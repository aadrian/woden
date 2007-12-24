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
import org.apache.woden.wsdl20.extensions.http.HTTPAuthenticationScheme;

/**
 * This interface represents the properties from the HTTP namespace added to the
 * WSDL 2.0 <code>Endpoint</code> component when the binding type is SOAP and the
 * underlying protocol is HTTP.
 * <p>
 * These include:
 * <ul>
 * <li>{http authentication scheme}</li>
 * <li>{http authentication realm}</li>
 * </ul>
 * 
 * @author Arthur Ryman (ryman@ca.ibm.com, arthur.ryman@gmail.com)
 * 
 */
public interface SOAPEndpointExtensions extends ComponentExtensions {

	public HTTPAuthenticationScheme getHttpAuthenticationScheme();

	public String getHttpAuthenticationRealm();
}
