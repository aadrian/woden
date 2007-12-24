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
package org.apache.woden.internal.wsdl20.extensions.http;

import org.apache.woden.internal.wsdl20.extensions.ComponentExtensionsImpl;
import org.apache.woden.internal.xml.IntOrTokenAnyAttrImpl;
import org.apache.woden.wsdl20.extensions.ExtensionElement;
import org.apache.woden.wsdl20.extensions.http.HTTPBindingFaultExtensions;
import org.apache.woden.wsdl20.extensions.http.HTTPErrorStatusCode;
import org.apache.woden.wsdl20.extensions.http.HTTPHeader;
import org.apache.woden.wsdl20.xml.WSDLElement;
import org.apache.woden.xml.StringAttr;

/**
 * This class defines the properties from the HTTP namespace
 * added to the WSDL <code>BindingFault</code> component as part 
 * of the HTTP binding extension defined by the WSDL 2.0 spec. 
 * 
 * @author John Kaputin (jkaputin@apache.org)
 */
public class HTTPBindingFaultExtensionsImpl extends ComponentExtensionsImpl
                                            implements HTTPBindingFaultExtensions 
{

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.http.HTTPBindingFaultExtensions#getHttpErrorStatusCode()
     */
    public HTTPErrorStatusCode getHttpErrorStatusCode() 
    {
        /* IntOrTokenAnyAttrImpl is the class registered for this extension attribute. Use this type 
         * here, rather than the IntOrTokenAttr interface, to guarantee that if the code contains an 
         * xs:token it is of type #any.
         */
        IntOrTokenAnyAttrImpl code = (IntOrTokenAnyAttrImpl)
            ((WSDLElement)fParent).getExtensionAttribute(HTTPConstants.Q_ATTR_CODE);
        
        if(code == null)
        {
            //defaults to xs:token #any if the attribute is omitted from the WSDL.
            return HTTPErrorStatusCode.ANY;
        }
        else if(code.isToken()) 
        {
            //if IntOrTokenAnyAttrImpl contains a token then is must be '#any'
            return HTTPErrorStatusCode.ANY;
        }
        else if(code.isInt())
        {
            return new HTTPErrorStatusCode(code.getInt());
        }
        else
        {
            //the whttp:code attribute contains an invalid value (i.e. not an xs:QName or the xs:token #any)
            //TODO confirm if this should be represented in the Component model as a null
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.http.HTTPBindingFaultExtensions#getHttpContentEncoding()
     * 
     */
    public String getHttpContentEncoding() 
    {
        String ce = null;
        StringAttr contEncoding = (StringAttr) ((WSDLElement)fParent)
            .getExtensionAttribute(HTTPConstants.Q_ATTR_CONTENT_ENCODING);
        if(contEncoding != null) {
            ce = contEncoding.getString();
        }
        return ce; 
    }

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.http.HTTPBindingFaultExtensions#getHttpHeaders()
     */
    public HTTPHeader[] getHttpHeaders() 
    {
        ExtensionElement[] extEls =  ((WSDLElement)fParent)
            .getExtensionElementsOfType(HTTPConstants.Q_ELEM_HTTP_HEADER);
        int len = extEls.length;
        HTTPHeader[] httpHeaders = new HTTPHeader[len];
        System.arraycopy(extEls, 0, httpHeaders, 0, len);
        return httpHeaders;
    }

}
