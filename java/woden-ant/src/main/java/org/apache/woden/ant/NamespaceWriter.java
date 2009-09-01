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
package org.apache.woden.ant;

import java.net.URI;

/**
 * This is the abstract base class for classes that write elements and types from an XML namespace;
 * 
 * @author Arthur Ryman (ryman@ca.ibm.com, arthur.ryman@gmail.com)
 *
 */
public abstract class NamespaceWriter {
    
    protected XMLWriter out;
    
    private String namespace;
    
    private String prefix;
    
    public NamespaceWriter(XMLWriter out, String namespace, String prefix) {
        
        this.out = out;
        this.namespace = namespace;
        this.prefix = prefix;
        
        out.register(this);
    }
    
    public String getNamespace() {
        
        return namespace;
    }
    
    public String getPrefix() {
        
        return prefix;
    }
    
    public void write(String tag, URI uri) {

        if (uri == null)
            return;

        out.element(tag, uri.toString());
    }

    public void writeAny(String tag, Object o) {

        if (o == null)
            return;

        // TODO: write element content correctly
        out.element(tag, o.toString());
    }
}