/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package org.apache.woden.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.woden.ErrorHandler;
import org.apache.woden.ErrorReporter;
import org.apache.woden.WSDLException;
import org.apache.woden.WSDLFactory;
import org.apache.woden.WSDLSource;
import org.apache.woden.XMLElement;
import org.apache.woden.internal.resolver.OMSchemaResolverAdapter;
import org.apache.woden.internal.schema.ImportedSchemaImpl;
import org.apache.woden.internal.schema.InlinedSchemaImpl;
import org.apache.woden.internal.schema.SchemaConstants;
import org.apache.woden.internal.util.StringUtils;
import org.apache.woden.internal.util.om.OMQNameUtils;
import org.apache.woden.internal.util.om.OMUtils;
import org.apache.woden.internal.wsdl20.Constants;
import org.apache.woden.internal.xpointer.OMXMLElementEvaluator;
import org.apache.woden.schema.Schema;
import org.apache.woden.types.NamespaceDeclaration;
import org.apache.woden.wsdl20.Description;
import org.apache.woden.wsdl20.xml.DescriptionElement;
import org.apache.woden.wsdl20.xml.WSDLElement;
import org.apache.woden.xpointer.InvalidXPointerException;
import org.apache.woden.xpointer.XPointer;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.utils.NamespaceMap;
import org.xml.sax.InputSource;

/**
 * Implements WSDL reader behaviour for OM based parsing
 */
public class OMWSDLReader extends BaseWSDLReader{

    //A map of imported schema definitions keyed by schema location URI
    private Map fImportedSchemas = new Hashtable();

    OMWSDLReader(WSDLContext wsdlContext) throws WSDLException {
        super(wsdlContext);
    }

    public Description readWSDL(String wsdlURI) throws WSDLException {
        //This conversion to a URL is necessary to import the schema
        URL url;
        try {
            url = StringUtils.getURL(null, wsdlURI);
        } catch (MalformedURLException e) {
            String msg = getErrorReporter().getFormattedMessage(
                            "WSDL502", new Object[] {null, wsdlURI});
            throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
        }
        String wsdlURL = url.toString();

        OMElement root = null;

        // perform URI Resolution here
        try {
            root = OMUtils.getElement(resolveURI(wsdlURL));
        } catch (IOException e){
            String msg = getErrorReporter().getFormattedMessage(
                    "WSDL503", new Object[] {wsdlURI});
            throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
        } catch (URISyntaxException e){
            String msg = getErrorReporter().getFormattedMessage(
                    "WSDL502", new Object[] {null, wsdlURI});
            throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
        } catch (XMLStreamException e){
            String msg = getErrorReporter().getFormattedMessage(
                    "WSDL500", new Object[] {"XML", wsdlURI});
            throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
        }
        //Try to find an element the XPointer points to if a Fragment Identifier exists.
        URI uri = null;
        try {
            uri = new URI(wsdlURI);
        } catch (URISyntaxException e) {
            String msg = getErrorReporter().getFormattedMessage(
                    "WSDL506", new Object[] {wsdlURI});
            throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
        }
        
        String fragment = uri.getFragment();

        if (fragment == null) { //No fragment identifier so just use the root element.
            XMLElement descEl = createXMLElement(root);
            DescriptionElement descElem = parseDescription(url.toString(), descEl, null);
            return descElem.toComponent();
        } else {
            XPointer xpointer;
            try {
                xpointer = new XPointer(fragment);
            } catch(InvalidXPointerException e) {
                String msg = getErrorReporter().getFormattedMessage(
                        "WSDL530", new Object[] {fragment, wsdlURI});
                throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
            }
            
            OMXMLElementEvaluator evaluator = new OMXMLElementEvaluator(xpointer, root, getErrorReporter());
            OMElement result = evaluator.evaluateElement();
            
            if (result != null) { //Element from XPointer evaluation.
                XMLElement descEl = createXMLElement(result);
                DescriptionElement descElem = parseDescription(url.toString(), descEl, null);
                return descElem.toComponent();
            } else {
                String msg = getErrorReporter().getFormattedMessage(
                        "WSDL531", new Object[] {fragment, wsdlURI});
                throw new WSDLException(WSDLException.PARSER_ERROR, msg);
            }
        }
    }

    /*
     * Parse the &lt;xs:import&gt; element and retrieve the imported
     * schema document if schemaLocation specified. Failure to retrieve
     * the schema will only matter if any WSDL components contain elements or
     * constraints that refer to the schema, and typically this will be
     * determined later by WSDL validation. So just report any such errors
     * and return the SchemaImport object (i.e. with a null schema property).
     *
     * WSDL 2.0 spec validation:
     * - namespace attribute is REQUIRED
     * - imported schema MUST have a targetNamespace
     * - namespace and targetNamespace MUST be the same
     */
    protected Schema parseSchemaImport(XMLElement importEl,
                                     DescriptionElement desc)
                                     throws WSDLException {

        ImportedSchemaImpl schema = new ImportedSchemaImpl();

        String ns = importEl.getAttributeValue(Constants.ATTR_NAMESPACE);

        if(ns != null) {
            schema.setNamespace(getURI(ns));
        }

        String sloc = importEl.getAttributeValue(SchemaConstants.ATTR_SCHEMA_LOCATION);
        if(sloc != null) {
            schema.setSchemaLocation(getURI(sloc));
        }

        if(schema.getNamespace() == null){
            //The namespace attribute is REQUIRED on xs:import, so don't continue.
            schema.setReferenceable(false);
            return schema;
        }

        if(schema.getSchemaLocation() == null){
            //This is a namespace-only import, no schema document to be retrieved so don't continue.

            /* TODO investigate whether/how to try to resolve the imported namespace to known schema
             * components from that namespace (e.g. via a URI catalog resolver). Currently, any attempt
             * to resolve a QName against schema components from this namespace will search ALL
             * schemas imported from this namespace (see methods in TypesImpl).
             */

            return schema;
        }

        //Now try to retrieve the schema import using schemaLocation

        OMElement importedSchemaDoc = null;
        URI contextURI = null;
        String schemaLoc = null;
        URL url = null;

        try{
        	/*
        	 * For simple resolvers, we resolve the parent (Description) URI
        	 * to be used as the context. This allows for relative locationURIs
        	 * to be resolved implicitly - they are considered to be located 
        	 * relative to the resolved parent. Therefore, relative URIs such as these
        	 * need not be listed in the catalog file.
        	 */
        	
        	/* TODO
        	 * OASIS-style catalogs have a convenience notation to define root URIs
        	 * thus grouping related URLs together. In this case the context URI here
        	 * should be left alone, but the resultant locationURL resolved instead.
        	 * 
        	 * Implement a boolean system property like org.apache.woden.resolver.useRelativeURLs
        	 * (set by the resolver ctor). SimpleURIResolver (et al) should set this to true,
        	 * OASISCatalogResolver should set to false. 
        	 */
        	// contextURI = desc.getDocumentBaseURI();
        	contextURI = resolveURI(desc.getDocumentBaseURI());
            URL contextURL = (contextURI != null) ? contextURI.toURL() : null;
            schemaLoc = schema.getSchemaLocation().toString();
            url = StringUtils.getURL(contextURL, schemaLoc);

        }
        catch (MalformedURLException e) {

            String baseLoc = contextURI != null ? contextURI.toString() : null;
            getErrorReporter().reportError(
                    new ErrorLocatorImpl(),  //TODO line&col nos.
                    "WSDL502",
                    new Object[] {baseLoc, schemaLoc},
                    ErrorReporter.SEVERITY_ERROR);

            //can't continue schema retrieval with a bad URL.
            schema.setReferenceable(false);
            return schema;
        }

        String schemaURL = url.toString();

        //If the schema has already been imported, reuse it.
        XmlSchema schemaDef = (XmlSchema)fImportedSchemas.get(schemaURL);

        if(schemaDef == null){
            //not previously imported, so retrieve it now.
            try {
                importedSchemaDoc = OMUtils.getElement(schemaURL);
            } catch (URISyntaxException e){
                String msg = getErrorReporter().getFormattedMessage(
                        "WSDL502", new Object[] {null, schemaURL});
                throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
            } catch (XMLStreamException e){
                String msg = getErrorReporter().getFormattedMessage(
                        "WSDL500", new Object[] {"XML", schemaURL});
                throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
            } catch (IOException e4) {
                
                //schema retrieval failed (e.g. 'not found')
                getErrorReporter().reportError(
                        new ErrorLocatorImpl(),  //TODO line&col nos.
                        "WSDL504", 
                        new Object[] {schemaURL}, 
                        ErrorReporter.SEVERITY_WARNING, 
                        e4);
                
                //cannot continue without an imported schema
                schema.setReferenceable(false);
                return schema;
            }

            /*
            * First get the schema element and serialize that into a byte array.
            * This is used in getting an InputSource which is later used as an argument
            * to the XMLSchemaCollection object.
            */
            String schemaElStr = null;
            try {
                schemaElStr = importedSchemaDoc.toStringWithConsume();
            }
            catch (XMLStreamException e) {
                e.printStackTrace();
            }
            byte[] schemaElbytes = schemaElStr.getBytes();
            InputSource schemaSource = new InputSource(new ByteArrayInputStream(schemaElbytes));
            schemaSource.setSystemId(schemaURL);

            try {
                XmlSchemaCollection xsc = new XmlSchemaCollection();
                
                // Plug in the selected woden URI Resolver
                xsc.setSchemaResolver(new OMSchemaResolverAdapter(getURIResolver(), importEl));
                
                schemaDef = xsc.read(schemaSource, null);
                fImportedSchemas.put(schemaURL, schemaDef);
            }
            catch (XmlSchemaException e){
                getErrorReporter().reportError(
                        new ErrorLocatorImpl(),  //TODO line&col nos.
                        "WSDL522",
                        new Object[] {schemaURL},
                        ErrorReporter.SEVERITY_WARNING,
                        e);
            }
        }
        if(schemaDef != null) {
            schema.setSchemaDefinition(schemaDef);
        }
        else {
            schema.setReferenceable(false);
        }
        return schema;
    }


    protected Schema parseSchemaInline(XMLElement schemaElement,
                                     DescriptionElement desc)
                                     throws WSDLException{

        InlinedSchemaImpl schema = new InlinedSchemaImpl();
        schema.setId(schemaElement.getAttributeValue(Constants.ATTR_ID));
        String tns = schemaElement.getAttributeValue(Constants.ATTR_TARGET_NAMESPACE);
        if(tns != null) {
            schema.setNamespace(getURI(tns));
        }

        String baseURI = desc.getDocumentBaseURI() != null ?
                         desc.getDocumentBaseURI().toString() : null;

        XmlSchema schemaDef = null;

        try {
            OMElement omSchemaElem = (OMElement)schemaElement.getSource();
            InputSource schemaSource = OMUtils.getInputSource(omSchemaElem);
            XmlSchemaCollection xsc = new XmlSchemaCollection();

            //Set the baseURI and the namespaces from the DescriptionElement in the XMLSchemaCollection
            xsc.setBaseUri(baseURI);
            
            NamespaceMap namespaces = new NamespaceMap();
            Iterator it = Arrays.asList(desc.getDeclaredNamespaces()).iterator();
            while(it.hasNext()) {
                NamespaceDeclaration d = (NamespaceDeclaration)it.next();
                namespaces.add(d.getPrefix(), d.getNamespaceURI().toString());
            }
            xsc.setNamespaceContext(namespaces);
            
            // Plug in the selected woden URI Resolver
            xsc.setSchemaResolver(new OMSchemaResolverAdapter(getURIResolver(), schemaElement));
            
            schemaDef = xsc.read(schemaSource, null);
        }
        catch (XmlSchemaException e){

            getErrorReporter().reportError(
                    new ErrorLocatorImpl(),  //TODO line&col nos.
                    "WSDL521",
                    new Object[] {baseURI},
                    ErrorReporter.SEVERITY_WARNING,
                    e);
        }
        catch (RuntimeException e)
        {
            getErrorReporter().reportError(
                    new ErrorLocatorImpl(),  //TODO line&col nos.
                    "WSDL521", 
                    new Object[] {baseURI}, 
                    ErrorReporter.SEVERITY_ERROR,
                    e);            
        }
        
        if(schemaDef != null) {
            schema.setSchemaDefinition(schemaDef);
        }
        else {
            schema.setReferenceable(false);
        }

        return schema;
    }
    

    //TODO
    protected void parseExtensionAttributes(XMLElement domEl,
                                          Class wsdlClass,
                                          WSDLElement wsdlObj,
                                          DescriptionElement desc)
                                          throws WSDLException {
    }

    ///////////////////////////////////////
    //  METHODS FOR READING FROM A SOURCE
    ///////////////////////////////////////

    //TODO
    public Description readWSDL(WSDLSource wsdlSource)
                                    throws WSDLException {
        return null;
    }

    //////////////////////////
    //  HELPER METHODS
    //////////////////////////


    /*
     * Retrieve a WSDL document by resolving the location URI specified
     * on a WSDL &lt;import&gt; or &lt;include&gt; element.
     *
     * TODO add support for a URL Catalog Resolver
     */
    protected DescriptionElement getWSDLFromLocation(String locationURI,
                                                   DescriptionElement desc,
                                                   Map wsdlModules)
                                               throws WSDLException{
        DescriptionElement referencedDesc = null;
        OMElement docEl;
        URL locationURL = null;
        URI contextURI = null;

        try{
        	/*
        	 * For simple resolvers, we resolve the parent (Description) URI
        	 * to be used as the context. This allows for relative locationURIs
        	 * to be resolved implicitly - they are considered to be located 
        	 * relative to the resolved parent. Therefore, relative URIs such as these
        	 * need not be listed in the catalog file.
        	 */
        	
        	/* TODO
        	 * OASIS-style catalogs have a convenience notation to define root URIs
        	 * thus grouping related URLs together. In this case the context URI here
        	 * should be left alone, but the resultant locationURL resolved instead.
        	 * 
        	 * Implement a boolean system property like org.apache.woden.resolver.useRelativeURLs
        	 * (set by the resolver ctor). SimpleURIResolver (et al) should set this to true,
        	 * OASISCatalogResolver should set to false. 
        	 */
        	// contextURI = desc.getDocumentBaseURI();
        	contextURI = resolveURI(desc.getDocumentBaseURI());
            URL contextURL = (contextURI != null) ? contextURI.toURL() : null;
            locationURL = StringUtils.getURL(contextURL, locationURI);
        }
        catch (MalformedURLException e){
            String baseURI = contextURI != null ? contextURI.toString() : null;

            getErrorReporter().reportError(
                    new ErrorLocatorImpl(),  //TODO line&col nos.
                    "WSDL502",
                    new Object[] {baseURI, locationURI},
                    ErrorReporter.SEVERITY_ERROR);

            //can't continue import with a bad URL.
            return null;
        }

        String locationStr = locationURL.toString();

        //Check if WSDL imported or included previously from this location.
        referencedDesc = (DescriptionElement)wsdlModules.get(locationStr);

        if(referencedDesc == null){
            // not previously imported or included, so retrieve the WSDL.
            try {
                docEl = OMUtils.getElement(locationStr);
            } catch (URISyntaxException e){
                String msg = getErrorReporter().getFormattedMessage(
                        "WSDL502", new Object[] {null, locationStr});
                throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
            } catch (XMLStreamException e){
                String msg = getErrorReporter().getFormattedMessage(
                        "WSDL500", new Object[] {"XML", locationStr});
                throw new WSDLException(WSDLException.PARSER_ERROR, msg, e);
            } catch (IOException e) {
                // document retrieval failed (e.g. 'not found')
                getErrorReporter().reportError(
                        new ErrorLocatorImpl(),  // TODO line&col nos.
                        "WSDL503", 
                        new Object[] {locationStr}, 
                        ErrorReporter.SEVERITY_WARNING, 
                        e);
                
                // cannot continue without the referenced document
                return null;
            }

            //The referenced document should contain a WSDL <description>
            if(!OMQNameUtils.matches(Constants.Q_ELEM_DESCRIPTION, docEl)){
                getErrorReporter().reportError(
                        new ErrorLocatorImpl(),  //TODO line&col nos.
                        "WSDL501",
                        new Object[] {Constants.Q_ELEM_DESCRIPTION,
                                      OMQNameUtils.newQName(docEl)},
                        ErrorReporter.SEVERITY_ERROR);

                //cannot continue without a <description> element
                return null;
            }

            XMLElement descEl = createXMLElement(docEl);
            
            referencedDesc = parseDescription(locationStr,
                                              descEl,
                                              wsdlModules);

            if(!wsdlModules.containsKey(locationStr)){
                wsdlModules.put(locationStr, referencedDesc);
            }
        }

        return referencedDesc;
    }

    //TODO
    public WSDLSource createWSDLSource() {
        return null;
    }

    protected XMLElement createXMLElement(Object elem) {
        OMXMLElement omXMLElement =  new OMXMLElement(getErrorReporter());
        omXMLElement.setSource(elem);
        return omXMLElement;

    }

    protected void parseNamespaceDeclarations(
            XMLElement xmlElem, 
            WSDLElement wsdlElem) 
            throws WSDLException {
        OMElement omDescription = (OMElement)xmlElem.getSource();
        
        Iterator namespaces = omDescription.getAllDeclaredNamespaces();
        while(namespaces.hasNext()){
            OMNamespace namespace = (OMNamespace)namespaces.next();
            String localPart = namespace.getPrefix();
            String value = namespace.getNamespaceURI();

          if (!(Constants.ATTR_XMLNS).equals(localPart)){
            wsdlElem.addNamespace(localPart, getURI(value));  //a prefixed namespace
          }
          else{
            wsdlElem.addNamespace(null, getURI(value));       //the default namespace
          }
        }
    }

}