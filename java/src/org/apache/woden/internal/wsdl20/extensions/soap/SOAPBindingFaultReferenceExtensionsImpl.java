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

import org.apache.woden.internal.wsdl20.extensions.ComponentExtensionsImpl;
import org.apache.woden.wsdl20.extensions.ExtensionElement;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingFaultReferenceExtensions;
import org.apache.woden.wsdl20.extensions.soap.SOAPModule;

/**
 * This class defines the properties from the SOAP namespace
 * added to the WSDL <code>BindingFaultReference</code> component as part 
 * of the SOAP binding extension defined by the WSDL 2.0 spec. 
 * 
 * @author jkaputin@apache.org
 */
public class SOAPBindingFaultReferenceExtensionsImpl extends ComponentExtensionsImpl 
                                                     implements SOAPBindingFaultReferenceExtensions 
{

    /* (non-Javadoc)
     * @see org.apache.woden.wsdl20.extensions.soap.SOAPBindingFaultReferenceExtensions#getSoapModules()
     */
    public SOAPModule[] getSoapModules() 
    {
        ExtensionElement[] extEls = fParentElement.getExtensionElementsOfType(SOAPConstants.Q_ELEM_SOAP_MODULE);
        int len = extEls.length;
        SOAPModule[] soapMods = new SOAPModule[len];
        System.arraycopy(extEls, 0, soapMods, 0, len);
        return soapMods;
    }

}
