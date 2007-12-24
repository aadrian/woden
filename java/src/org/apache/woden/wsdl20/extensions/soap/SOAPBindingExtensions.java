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

import java.net.URI;

import org.apache.woden.wsdl20.extensions.ComponentExtensions;

/**
 * This interface represents the properties from the SOAP namespace
 * added to the WSDL 2.0 <code>Binding</code> component as part 
 * of the SOAP binding extension. 
 * <p>
 * These include:
 * <ul>
 * <li>{soap version}</li>
 * <li>{soap underlying protocol}</li>
 * <li>{soap mep default}</li>
 * <li>{soap modules}</li>
 * </ul> 
 * It also defines the properties from the HTTP extensions that
 * are present in the SOAP Binding extensions when the underlying
 * protocol is HTTP.
 * <p>
 * These include:
 * <ul>
 * <li>{http query parameter separator default}</li>
 * <li>{http cookies}</li>
 * <li>{http content encoding default}</li>
 * </ul>
 * 
 * @author John Kaputin (jkaputin@apache.org)
 */
public interface SOAPBindingExtensions extends ComponentExtensions 
{
    public String getSoapVersion();
    
    public URI getSoapUnderlyingProtocol();
    
    public URI getSoapMepDefault();
    
    public SOAPModule[] getSoapModules();
    
    /**
     * If the SOAP version is "1.1" or "1.2" and the underlying protocol is HTTP, returns the
     * {http query parameter separator default} extension property represented by the 
     * whttp:queryParameterSeparatorDefault extension attribute , otherwise null. 
     * 
     * @return String the {http query parameter separator default} extension property
     */
    public String getHttpQueryParameterSeparatorDefault();
    
    /**
     * If the SOAP version is "1.1" or "1.2" and the underlying protocol is HTTP, returns the
     * {http cookies} extension property represented by the 
     * whttp:cookies extension attribute , otherwise null. 
     * 
     * @return Boolean the {http cookies} extension property if present, otherwise null
     */
    public Boolean isHttpCookies();
    
    /**
     * If the SOAP version is "1.1" or "1.2" and the underlying protocol is HTTP, returns the
     * {http content encoding default} extension property represented by the 
     * whttp:contentEncodingDefault extension attribute , otherwise null. 
     * 
     * @return String the {http content encoding default} extension property
     */
    public String getHttpContentEncodingDefault();
    
}
