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

import org.apache.woden.ant.ObjectIdTable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests ObjectIdTable.
 * 
 * @author Arthur Ryman (ryman@ca.ibm.com, arthur.ryman@gmail.com)
 *
 */
public class ObjectIdTableTest extends TestCase {

    /**
     * Creates a test suite for this class.
     * 
     * @return the test suite
     */
    public static Test suite()
    {
        return new TestSuite(ObjectIdTableTest.class);
    }
    
    /**
     * Tests the id() method.
     * Same objects have the same id.
     * Different objects have different ids.
     */
    public void testId() {
        
        String s1 = "s1";
        String s2 = "s2";
        
        ObjectIdTable oit = new ObjectIdTable();
        
        int id1 = oit.id(s1);
        assertTrue("Same object, same id", id1 == oit.id(s1));
        
        int id2 = oit.id(s2);
        assertTrue("Different object, different id", id1 != id2);
        
        assertTrue("Same object, same id, again", id2 == oit.id(s2));
    }

}
