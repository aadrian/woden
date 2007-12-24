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
 * added to the WSDL 2.0 <code>BindingMessageReference</code> component as part 
 * of the SOAP binding extension.
 * <p>
 * These include:
 * <ul>
 * <li>{soap modules}</li>
 * <li>{soap headers}</li>
 * </ul> 
 * It also defines the properties from the HTTP extensions that
 * are present in the SOAP BindingMessageReference extensions when the underlying
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
public interface SOAPBindingMessageReferenceExtensions extends ComponentExtensions 
{
    public SOAPModule[] getSoapModules();

    public SOAPHeaderBlock[] getSoapHeaders();

    /**
     * @return String the {http content encoding} property, represented by the whttp:contentEncoding extension attribute
     */
    public String getHttpContentEncoding();
    
    /**
     * @return HTTPHeader[] the {http headers} property, represented by an array of 
     * HTTPHeader extension components, which map to whttp:header elements.
     */
    public HTTPHeader[] getHttpHeaders();
    
}
