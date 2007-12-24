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
package org.apache.woden.wsdl20.extensions.soap;

import org.apache.woden.wsdl20.extensions.ComponentExtensions;
import org.apache.woden.wsdl20.extensions.http.HTTPHeader;

/**
 * This interface represents the properties from the SOAP namespace
 * added to the WSDL 2.0 <code>BindingFault</code> component as part 
 * of the SOAP binding extension.
 * <p>
 * These include:
 * <ul>
 * <li>{soap fault code}</li>
 * <li>{soap fault subcodes}</li>
 * <li>{soap modules}</li>
 * <li>{soap headers}</li>
 * </ul> 
 * It also defines the properties from the HTTP extensions that
 * are present in the SOAP BindingFault extensions when the underlying
 * protocol of the SOAP Binding is HTTP.
 * <p>
 * These include:
 * <ul>
 * <li>{http content encoding}</li>
 * <li>{http headers}</li>
 * </ul>
 * 
 * @author John Kaputin (jkaputin@apache.org)
 */
public interface SOAPBindingFaultExtensions extends ComponentExtensions 
{
    /**
     * Returns an object representing the {soap fault code} property, which may
     * contain either an xs:QName or the xs:token "#any".
     */
    public SOAPFaultCode getSoapFaultCode();
    
    /**
     * Returns an object representing the {soap fault subcodes} property, 
     * which contains a List of xs:QName or the xs:token "#any".
     */
    public SOAPFaultSubcodes getSoapFaultSubcodes();
    
    public SOAPModule[] getSoapModules();

    public SOAPHeaderBlock[] getSoapHeaders();
    
    public String getHttpContentEncoding();
    
    public HTTPHeader[] getHttpHeaders();
    
}
