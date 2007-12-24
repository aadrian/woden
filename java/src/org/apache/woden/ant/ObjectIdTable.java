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

import java.util.HashMap;

/**
 * Provides a completely safe way to generate unique ids for equivalence classes of objects.
 * Equivalent objects are assigned the same ids.
 * Inequivalent objects are assigned different ids.
 * Here equivalance is defined by the equals() method which is used by HashMap.
 * Experience has shown that hashCode() occasionally produces the same hash for different objects.
 * Note that the objects are permanently stored, so only use this class when serializing
 * objects in an XML file.
 * 
 * A count is kept and assigned to each object equivalence class as it is added.
 * 
 * @author Arthur Ryman (ryman@ca.ibm.com, arthur.ryman@gmail.com)
 *
 */

public class ObjectIdTable {

    private HashMap components = new HashMap();

    private int nextId = 1;

    /**
     * Returns a unique integer for the equivalence class of the object.
     * 
     * @param o An object.
     * @return A unique id for the object.
     */
    public int id(Object o) {

        Integer id = (Integer) components.get(o);
        if (id == null) {

            id = new Integer(nextId++);
            components.put(o, id);
        }

        return id.intValue();
    }

}
