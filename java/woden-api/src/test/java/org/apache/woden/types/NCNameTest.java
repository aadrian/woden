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
package org.apache.woden.types;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * junit tests for the NCName class.
 */
public class NCNameTest extends TestCase {

    public static Test suite()
    {
        return new TestSuite(NCNameTest.class);
    }
    
    private NCName n1, n1_dup, n1_dup2, n2, n3, n3_dup, n3_dup2;
    
    public void setUp() {
        n1 = new NCName("someNCName");
        n1_dup = new NCName("someNCName");
        n1_dup2 = new NCName("someNCName");
        n2 = new NCName("someOtheNCName");
        n3= new NCName("");
        n3_dup = new NCName("");
        n3_dup2 = new NCName("");
    }

    public void testEqualsWithNullValueNCNames() {
        equalsIsReflexive(n3);
        equalsIsSymmetric(n3, n3_dup, n2);
        equalsIsTransitive(n3, n3_dup, n3_dup2);
        equalsIsConsistent(n3, n3_dup);
    }
    
    public void testEqualsWithNonNullValueNCNames() {
        equalsIsReflexive(n1);
        equalsIsSymmetric(n1, n1_dup, n2);
        equalsIsTransitive(n1, n1_dup, n1_dup2);
        equalsIsConsistent(n1, n1_dup);        
    }
    
    private void equalsIsReflexive(NCName n1) {
        assertTrue("reflexive test fails", n1.equals(n1));
    }
    
    private void equalsIsSymmetric(NCName n1, NCName n1_dup, NCName n2) {
        assertTrue("symmetric test fails for equal NCNames", n1.equals(n1_dup));
        assertTrue("symmetric test fails for equal NCNames", n1_dup.equals(n1));
        assertTrue("symmetric test fails for unequal NCNames", !n1.equals(n2));
        assertTrue("symmetric test fails for unequal NCNames", !n2.equals(n1));
    }
    
    private void equalsIsTransitive(NCName n1, NCName n1_dup, NCName n1_dup2) {
        assertTrue("transitive test fails", n1.equals(n1_dup) && n1_dup.equals(n1_dup2) && n1.equals(n1_dup2));
    }
    
    private void equalsIsConsistent(NCName n1, NCName n1_dup) {
        assertTrue("consistent test fails", n1.equals(n1_dup) && n1.equals(n1_dup));
    }
    
    public void testEqualsNullParamFalse() {
        assertTrue("Null param doesn't return false", n1.equals(null) == false);
    }
    
    public void testThrowsIllegalArgExceptionWithNullArgument() {
        try {
            NCName nc = new NCName(null);
            fail("NCName(null) should throw IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // do nothing as this is the expected result
        }
    }

    public void testMapWithStringKeyAndNCNameValue() {
        String keyStr = new String("bar");
        NCName nOrig = new NCName(keyStr);
        NCName n;
        Map<String, NCName> ncnameMap = new HashMap<String, NCName>();
        ncnameMap.put(keyStr, nOrig);
        n = (NCName)ncnameMap.get(keyStr);
        assertNotNull("Couldn't find NCName in Map keyed off String key object used to put it there", n);
        assertTrue("Didn't find the right NCName in the map", n==nOrig);
        n = (NCName)ncnameMap.get(new String("bar"));
        assertNotNull("Couldn't find NCName in Map keyed off new equivalent String", n);
        assertTrue("Didn't find the right NCName in the map", n==nOrig);
    }

    public void testMapWithNCNameKeyAndStringValue() {
        NCName keyNCName = new NCName("bar");
        String sOrig = keyNCName.toString();
        String s;
        Map<NCName, String> stringMap = new HashMap<NCName, String>();
        stringMap.put(keyNCName, sOrig);
        s = (String)stringMap.get(keyNCName);
        assertNotNull("Couldn't find String in Map keyed off NCName key object used to put it there", s);
        assertTrue("Didn't find the right NCName in the map", s==sOrig);
        s = (String)stringMap.get(new NCName("bar"));
        assertNotNull("Couldn't find NCName in Map keyed off new equivalent NCName", s);
        assertTrue("Didn't find the right NCName in the map", s==sOrig);
    }
}
