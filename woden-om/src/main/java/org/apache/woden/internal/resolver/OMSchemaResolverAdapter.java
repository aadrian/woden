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
package org.apache.woden.internal.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.woden.XMLElement;
import org.apache.woden.internal.schema.SchemaConstants;
import org.apache.woden.resolver.URIResolver;

/**
 * Class that adds OM specific behaviour to the SchemaResolverAdapter class.
 * 
 * TODO consider some approach other than inheritance as part of a broader consideration
 * of resolving imports and fragids.
 * 
 * @author John Kaputin (jkaputin@apache.org)
 *
 */

public class OMSchemaResolverAdapter extends SchemaResolverAdapter {

    public OMSchemaResolverAdapter(URIResolver actualResolver, XMLElement schemaElement) {
        super(actualResolver, schemaElement);
    }
    
    protected InputStream resolveFragId(String fragId) {
        
        String id = fragId.substring(1);
        
        OMElement contextEl = (OMElement)fContextElement.getSource();
        OMElement typesEl = (OMElement)contextEl.getParent();
        Iterator inlineSchemas = typesEl.
            getChildrenWithName(SchemaConstants.Q_ELEM_SCHEMA);
        OMElement identifiedSchema = null;
        String schemaId;
        while(inlineSchemas.hasNext()) {
            OMElement schema = (OMElement)inlineSchemas.next();
            schemaId = schema.getAttributeValue(SchemaConstants.Q_ATTR_ID);
            if(schemaId != null && schemaId.equals(id)) {
                identifiedSchema = schema;
                break;
            }
        }
        
        if(identifiedSchema == null) {
            //the fragid does not identify any element
            //TODO suitable error message
            return null;
        }
        
        String schemaString = null;
        try {
            schemaString = identifiedSchema.toStringWithConsume();
        } catch (XMLStreamException e) {
            // TODO this conforms to parent, but needs an error message via ErrorReporter and maybe it should be handled differently?
            throw new RuntimeException(e);
        }
        byte[] schemaBytes = schemaString.getBytes();
        InputStream iStream = new ByteArrayInputStream(schemaBytes);
        return iStream;
    }
}
