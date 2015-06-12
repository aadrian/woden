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
package org.apache.woden.wsdl20.editable;

import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;

import org.apache.woden.wsdl20.Description;

/**
 * Represents the editable Description component from the WSDL 2.0 Component
 * model
 * 
 * 
 */
public interface EdDescription extends Description {
    public EdBinding addBinding();

    public EdInterface addInterface();

    public EdService addService();

    public EdTypeDefinition addTypeDefinition();

    public void setDocumentBaseURI(URI documentBaseURI);

    public void setTargetNamespace(URI namespaceURI);

    /**
     * serialize WSDL content according to the provided SearalizationStrategy.
     * 
     * @param sink
     */
    public void serialize(OutputStream sink);

    public void serialize(Writer sink);

    public String getserializationStrategy();

    public void setserializationStrategy(String searalizationStrategy);
}
