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
import java.util.Arrays;

import javax.xml.namespace.QName;

public class CmBaseWriter extends NamespaceWriter {
    
    public final static String NS = "http://www.w3.org/2002/ws/desc/wsdl/component-base";
    
    public final static String PREFIX = "cmbase";
    
    // element names
    public final static String LOCAL_NAME = PREFIX + ":localName";
    public final static String NAMESPACE_NAME = PREFIX + ":namespaceName";
    public final static String PARENT = PREFIX + ":parent";
    public final static String REF = PREFIX + ":ref";
    public final static String REQUIRED = PREFIX + ":required";
    public final static String URI = PREFIX + ":uri";
    public final static String VALUE = PREFIX + ":value";
    public final static String VALUE_CONSTRAINT = PREFIX + ":valueConstraint";
    
    private ObjectIdTable oit = new ObjectIdTable();

    public CmBaseWriter(XMLWriter out) {
        
        super(out, NS, PREFIX);
    }
    
    public void parent(Object parent) {
        
        writeRef(PARENT, parent);
    }

    public void write(String tag, QName qname) {

        if (qname == null)
            return;

        out.beginElement(tag);

        out.element(NAMESPACE_NAME, qname.getNamespaceURI());
        out.element(LOCAL_NAME, qname.getLocalPart());

        out.endElement();
    }

    public void writeUris(String tag, URI[] uris) {

        if (uris.length == 0)
            return;

        Arrays.sort(uris);

        out.beginElement(tag);

        for (int i = 0; i < uris.length; i++)
            write(URI, uris[i]);

        out.endElement();
    }

    public void writeOptionalRef(String tag, Object o) {

        if (o == null)
            return;

        writeRef(tag, o);
    }

    public void writeRef(String tag, Object o) {

        out.emptyElement(tag, refAttribute(o));
    }

    private String id(Object o) {

        if (o == null) {
            return "id-null";
        }

        return "id-" + oit.id(o);
    }

    public String idAttribute(Object o) {

        return "xml:id='" + id(o) + "'";
    }

    public String refAttribute(Object o) {

        return "ref='" + id(o) + "'";
    }
    
    public static int compareQName(QName q1, QName q2) {

        if (q1.equals(q2))
            return 0;

        String n1 = q1.getNamespaceURI();
        String n2 = q2.getNamespaceURI();
        if (n1.equals(n2)) {
            String l1 = q1.getLocalPart();
            String l2 = q2.getLocalPart();

            return l1.compareTo(l2);
        } else {
            return n1.compareTo(n2);
        }
    }
}
