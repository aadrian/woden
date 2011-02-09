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

import org.apache.woden.wsdl20.extensions.AttributeExtensible;
import org.apache.woden.wsdl20.extensions.ElementExtensible;
import org.apache.woden.wsdl20.extensions.ExtensionElement;
import org.apache.woden.wsdl20.xml.DocumentationElement;
import org.apache.woden.wsdl20.xml.WSDLElement;

/**
 * This interface represents the &lt;wsoap:module&gt; extension element that 
 * can appear within a Binding, Binding Fault, Binding Operation, Binding
 * Fault Reference or Binding Message Reference.
 * 
 * @author jkaputin@apache.org
 */
public interface SOAPModuleElement extends ExtensionElement, 
                                           AttributeExtensible, 
                                           ElementExtensible
{
    public void setRef(URI uri);
    
    public URI getRef();
    
    public void setParentElement(WSDLElement wsdlEl);
    
    public WSDLElement getParentElement();

    public void addDocumentationElement(DocumentationElement docEl);
    
    public DocumentationElement[] getDocumentationElements();
}
