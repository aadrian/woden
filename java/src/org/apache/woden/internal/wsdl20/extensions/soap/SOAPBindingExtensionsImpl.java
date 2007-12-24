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
import org.apache.woden.wsdl20.extensions.ExtensionElement;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions;
import org.apache.woden.wsdl20.extensions.soap.SOAPModule;
import org.apache.woden.wsdl20.xml.WSDLElement;
import org.apache.woden.xml.BooleanAttr;
import org.apache.woden.xml.StringAttr;
import org.apache.woden.xml.URIAttr;

/**
 * This class defines the properties from the SOAP namespace
 * added to the WSDL <code>Binding</code> component as part 
 * of the SOAP binding extension defined by the WSDL 2.0 spec. 
 * 
 * @author jkaputin@apache.org
 */
public class SOAPBindingExtensionsImpl extends ComponentExtensionsImpl
                                       implements SOAPBindingExtensions 
{

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions#getSoapVersion()
     */
    public String getSoapVersion() 
    {
        StringAttr version = 
            (StringAttr)fParentElement.getExtensionAttribute(SOAPConstants.Q_ATTR_SOAP_VERSION);
        return version != null ? version.getString() : "1.2";
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions#getSoapUnderlyingProtocol()
     */
    public URI getSoapUnderlyingProtocol() 
    {
        URIAttr protocol = 
            (URIAttr)fParentElement.getExtensionAttribute(SOAPConstants.Q_ATTR_SOAP_PROTOCOL);
        return protocol != null ? protocol.getURI() : null;
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions#getSoapMepDefault()
     */
    public URI getSoapMepDefault() 
    {
        URIAttr mepDefault = 
            (URIAttr)fParentElement.getExtensionAttribute(SOAPConstants.Q_ATTR_SOAP_MEPDEFAULT);
        return mepDefault != null ? mepDefault.getURI() : null;
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions#getSoapModules()
     */
    public SOAPModule[] getSoapModules() 
    {
        ExtensionElement[] extEls = fParentElement.getExtensionElementsOfType(SOAPConstants.Q_ELEM_SOAP_MODULE);
        int len = extEls.length;
        SOAPModule[] soapMods = new SOAPModule[len];
        System.arraycopy(extEls, 0, soapMods, 0, len);
        return soapMods;
    }
    
    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions#getHttpQueryParameterSeparatorDefault()
     */
    public String getHttpQueryParameterSeparatorDefault() 
    {
        URI protocol = getSoapUnderlyingProtocol();
        if(protocol == null) {
            return null;
        }
        
        if( ("1.2".equals(getSoapVersion()) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP12_HTTP)) ||
            ("1.1".equals(getSoapVersion()) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP11_HTTP)) )
        {
            StringAttr qpsDef = (StringAttr) ((WSDLElement)fParent)
                .getExtensionAttribute(HTTPConstants.Q_ATTR_QUERY_PARAMETER_SEPARATOR_DEFAULT);
            return qpsDef != null ? qpsDef.getString() : HTTPConstants.QUERY_SEP_AMPERSAND;
        } 
        else
        {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions#isHttpCookies()
     */
    public Boolean isHttpCookies() 
    {
        URI protocol = getSoapUnderlyingProtocol();
        if(protocol == null) {
            return null;
        }
        
        if( ("1.2".equals(getSoapVersion()) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP12_HTTP)) ||
            ("1.1".equals(getSoapVersion()) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP11_HTTP)) )
        {
            BooleanAttr cookiesUsed = (BooleanAttr) ((WSDLElement)fParent)
                .getExtensionAttribute(HTTPConstants.Q_ATTR_COOKIES);
            return cookiesUsed != null ? cookiesUsed.getBoolean() : new Boolean(false); //defaults to false if omitted
        } 
        else 
        {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensions#getHttpContentEncodingDefault()
     */
    public String getHttpContentEncodingDefault() 
    {
        URI protocol = getSoapUnderlyingProtocol();
        if(protocol == null) {
            return null;
        }
        
        if( ("1.2".equals(getSoapVersion()) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP12_HTTP)) ||
            ("1.1".equals(getSoapVersion()) && protocol.toString().equals(SOAPConstants.PROTOCOL_STRING_SOAP11_HTTP)) )
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
