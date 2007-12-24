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
package testcase.extensions.foo;

import org.apache.woden.internal.wsdl20.extensions.ComponentExtensionsImpl;
import org.apache.woden.wsdl20.xml.WSDLElement;
import org.apache.woden.xml.StringAttr;

public class FooBindingExtensionsImpl extends ComponentExtensionsImpl implements
		FooBindingExtensions {

	public Integer getFooBar() {
        FooOddAttr def = (FooOddAttr) ((WSDLElement)fParent).getExtensionAttribute(FooConstants.Q_ATTR_BAR);
        return def != null ? new Integer(def.getValue()) : null;
	}

	public String getFooBaz() {
		StringAttr def = (StringAttr) ((WSDLElement)fParent).getExtensionAttribute(FooConstants.Q_ATTR_BAZ);
		return def != null ? def.getString() : null;
	}

}
