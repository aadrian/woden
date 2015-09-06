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

import java.net.URI;

import javax.xml.namespace.QName;

import org.apache.woden.wsdl20.Binding;
import org.apache.woden.wsdl20.Interface;

/**
 * Represents the editable Binding component from the WSDL 2.0 Component model
 * 
 */
public interface EdBinding extends Binding {
    public EdBindingFault addBindingFault();

    public EdBindingOperation addBindingOperation();

    public void setInterface(Interface interfaceComp);

    public void setName(QName name);

    public void setType(URI uri);

}