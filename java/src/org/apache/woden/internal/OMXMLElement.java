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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.woden.ErrorReporter;
import org.apache.woden.WSDLException;
import org.apache.woden.XMLElement;

public class OMXMLElement extends BaseXMLElement{

    public OMXMLElement(ErrorReporter errorReporter) {
        super(errorReporter);
    }

    /*
     * @see org.apache.woden.XMLElement#setSource(java.lang.Object)
     */
    public void setSource(Object elem) {

        if(elem instanceof OMElement) {
            fSource = elem;
        }
        else {
            String elemClass = (elem != null
                                     ? elem.getClass().getName()
                                     : null);
            String xmlElementClass = this.getClass().getName();
            String msg = fErrorReporter.getFormattedMessage(
                    "WSDL019", new Object[] {elemClass, xmlElementClass});
            throw new IllegalArgumentException(msg);
        }

    }

    /*TODO complete this method if it's added to XMLElement.
     *
    public XMLAttribute[] getAttributes() {

        if (fSource instanceof OMElement){
            OMElement elem = (OMElement)fSource;
            List attrs = new Vector();
            Iterator iter =  elem.getAllAttributes();
            while (iter.hasNext()){
                //No need to check for xmlns, since AXIOM knows that if it's prefixed
                // with xmlns, that it's not an attribute
                //TODO create an XMLAttribute from iter.next()
                //attrs.add(xmlAttribute);
            }
        }

        XMLElement[] array = new XMLElement[attrs.size()];
        attrs.toArray(array);
        return array;
    }
     */

    protected String doGetAttributeValue(String attrName) {
        OMElement elem = (OMElement)fSource;
        return elem.getAttributeValue(new QName(attrName));
    }

    protected URI doGetNamespaceURI() throws WSDLException {
        OMElement elem = (OMElement)fSource;
        String nsStr =  elem.getNamespace().getNamespaceURI();
        URI uri = null;
        try {
            uri = new URI(nsStr);
        } catch (URISyntaxException e) {
            String msg = fErrorReporter.getFormattedMessage(
                                            "WSDL506", 
                                            new Object[] {nsStr});
            throw new WSDLException(WSDLException.INVALID_WSDL, msg, e);
        }
        return uri;
    }

    protected String doGetLocalName() {
        OMElement elem = (OMElement)fSource;
        return elem.getLocalName();
    }
    
    protected QName doGetQName() {
        OMElement elem = (OMElement)fSource;
        return elem.getQName();
    }

    protected QName doGetQName(String prefixedValue) throws WSDLException {
        OMElement elem = (OMElement)fSource;
        int index = prefixedValue.indexOf(':');
        String prefix = (index != -1)
                        ? prefixedValue.substring(0, index)
                        : null;
        String localPart    = prefixedValue.substring(index + 1);
        OMNamespace OMns    = elem.findNamespaceURI(prefix);
        String namespaceURI = OMns != null ? OMns.getNamespaceURI() : null;

        if(prefix != null && namespaceURI == null) {
            String faultCode = WSDLException.UNBOUND_PREFIX;
            String msg = fErrorReporter.getFormattedMessage(
                    "WSDL513", 
                    new Object[] {prefixedValue, elem.getQName()});
            WSDLException wsdlExc = new WSDLException(
                    faultCode,
                    msg);
            //TODO wsdlExc.setLocation(XPathUtils.getXPathExprFromNode(el));
            throw wsdlExc;
        }
        
        return new QName(namespaceURI, localPart, (prefix != null ? prefix : ""));
    }

    protected XMLElement doGetFirstChildElement() {
        OMElement elem = (OMElement)fSource;
        OMXMLElement omXMLElement = new OMXMLElement(fErrorReporter);
        omXMLElement.setSource(elem.getFirstElement());
        return omXMLElement;
    }

    protected XMLElement doGetNextSiblingElement() {
        OMElement elem = (OMElement)fSource;
        OMXMLElement omXMLElement = new OMXMLElement(fErrorReporter);
        omXMLElement.setSource(elem.getNextOMSibling());
        return omXMLElement;
    }
        
    protected XMLElement[] doGetChildElements() {
        
        OMElement elem = (OMElement)fSource;
        Iterator elems = elem.getChildElements();
        List children = new Vector();
        Object next = elems.next();
        while(next != null)
        {
            OMXMLElement omXMLElement = new OMXMLElement(fErrorReporter);
            omXMLElement.setSource(next);
            children.add(omXMLElement);
            next = elems.next();
        }
        XMLElement[] array = new XMLElement[children.size()];
        children.toArray(array);
        return array;
    }
    
}
