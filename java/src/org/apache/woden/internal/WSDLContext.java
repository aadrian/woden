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

import org.apache.woden.ErrorReporter;
import org.apache.woden.WSDLFactory;
import org.apache.woden.wsdl20.extensions.ExtensionRegistry;

/**
 * A container of objects shared across Woden within the context of a WSDLFactory.
 * 
 * TODO may need to be API, if so interface is WSDLContext, class name is WSDLContextImpl, add getters and make variables private
 * TODO check for use cases that break the WSDLFactory context concept (ie WSDLReader.setFactoryImplName).
 * TODO decide if anything else should be kept here (e.g. woden feats & props, Description factory)
 *  
 * @author John Kaputin (jkaputin@apache.org)
 */
public class WSDLContext {
    final public WSDLFactory wsdlFactory;
    final public ErrorReporter errorReporter;
    final public ExtensionRegistry extensionRegistry;
    
    //package private ctor
    WSDLContext(WSDLFactory wsdlFactory,
            ErrorReporter errorReporter,
            ExtensionRegistry extensionRegistry) {
        this.wsdlFactory = wsdlFactory;
        this.errorReporter = errorReporter;
        this.extensionRegistry = extensionRegistry;
    }
}
