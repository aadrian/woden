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
package org.apache.woden.internal.wsdl20.extensions.soap;

import java.net.URI;

import org.apache.woden.internal.wsdl20.extensions.ComponentExtensionsImpl;
import org.apache.woden.internal.wsdl20.extensions.http.HTTPConstants;
import org.apache.woden.wsdl20.Binding;
import org.apache.woden.wsdl20.NestedComponent;
import org.apache.woden.wsdl20.extensions.ComponentExtensions;
import org.apache.woden.wsdl20.extensions.ExtensionElement;
import org.apache.woden.wsdl20.extensions.http.HTTPLocation;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingOperationExtensions;
import org.apache.woden.wsdl20.extensions.soap.SOAPModule;
import org.apache.woden.wsdl20.xml.WSDLElement;
import org.apache.woden.xml.StringAttr;
import org.apache.woden.xml.URIAttr;

/**
 * This class defines the properties from the SOAP namespace
 * added to the WSDL <code>BindingOperation</code> component as part 
 * of the SOAP binding extension defined by the WSDL 2.0 spec. 
 * 
 * @author jkaputin@apache.org
 */
public class SOAPBindingOperationExtensionsImpl extends ComponentExtensionsImpl
                                                implements SOAPBindingOperationExtensions 
{

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingOperationExtensions#getSoapMep()
     */
    public URI getSoapMep() 
    {
        URIAttr mep = 
            (URIAttr)fParentElement.getExtensionAttribute(SOAPConstants.Q_ATTR_SOAP_MEP);
        return mep != null ? mep.getURI() : null;
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingOperationExtensions#getSoapAction()
     */
    public URI getSoapAction() 
    {
        URIAttr action = 
            (URIAttr)fParentElement.getExtensionAttribute(SOAPConstants.Q_ATTR_SOAP_ACTION);
        return action != null ? action.getURI() : null;
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingOperationExtensions#getSoapModules()
     */
    public SOAPModule[] getSoapModules() 
    {
        ExtensionElement[] extEls = fParentElement.getExtensionElementsOfType(SOAPConstants.Q_ELEM_SOAP_MODULE);
        int len = extEls.length;
        SOAPModule[] soapMods = new SOAPModule[len];
        System.arraycopy(extEls, 0, soapMods, 0, len);
        return soapMods;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingOperationExtensions#getHttpLocation()
     */
    public HTTPLocation getHttpLocation() {
        
        Binding binding = (Binding) ((NestedComponent)fParent).getParent();
        SOAPBindingExtensions soapBindExt = (SOAPBindingExtensions)binding
           .getComponentExtensionsForNamespace(ComponentExtensions.NS_URI_SOAP);
        String version = soapBindExt.getSoapVersion();
        URI protocol = soapBindExt.getSoapUnderlyingProtocol();
        if(protocol == null) {
            return null;
        }
        
        if( ("1.2".equals(version) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP12_HTTP)) ||
            ("1.1".equals(version) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP11_HTTP)) )
        {
            StringAttr httpLoc = (StringAttr) ((WSDLElement)fParent)
                .getExtensionAttribute(HTTPConstants.Q_ATTR_LOCATION);
            return httpLoc != null ? new HTTPLocation(httpLoc.getString()) : null;
        } 
        else 
        {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingOperationExtensions#getHttpQueryParameterSeparator()
     */
    public String getHttpQueryParameterSeparator() {
        
        Binding binding = (Binding) ((NestedComponent)fParent).getParent();
        SOAPBindingExtensions soapBindExt = (SOAPBindingExtensions)binding
           .getComponentExtensionsForNamespace(ComponentExtensions.NS_URI_SOAP);
        String version = soapBindExt.getSoapVersion();
        URI protocol = soapBindExt.getSoapUnderlyingProtocol();
        if(protocol == null) {
            return null;
        }
        
        if( ("1.2".equals(version) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP12_HTTP)) ||
            ("1.1".equals(version) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP11_HTTP)) )
        {
            StringAttr separator = (StringAttr) ((WSDLElement) fParent)
                .getExtensionAttribute(HTTPConstants.Q_ATTR_QUERY_PARAMETER_SEPARATOR);
            return separator != null ? separator.getString() : null;
        } 
        else 
        {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingOperationExtensions#getHttpContentEncodingDefault()
     */
    public String getHttpContentEncodingDefault() {
        
        Binding binding = (Binding) ((NestedComponent)fParent).getParent();
        SOAPBindingExtensions soapBindExt = (SOAPBindingExtensions)binding
           .getComponentExtensionsForNamespace(ComponentExtensions.NS_URI_SOAP);
        String version = soapBindExt.getSoapVersion();
        URI protocol = soapBindExt.getSoapUnderlyingProtocol();
        if(protocol == null) {
            return null;
        }
        
        if( ("1.2".equals(version) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP12_HTTP)) ||
            ("1.1".equals(version) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP11_HTTP)) )
        {
            StringAttr ceDef = (StringAttr) ((WSDLElement)fParent)
               .getExtensionAttribute(HTTPConstants.Q_ATTR_CONTENT_ENCODING_DEFAULT);
            return ceDef != null ? ceDef.getString() : null;
        } 
        else 
        {
            return null;
        }
    }
    
}
