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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import javax.xml.namespace.QName;

/**
 * This class tests the three possible ways to construct a QNameTokenUnion and the functions for each.
 *
 * @author Dan Harvey, danharvey42@gmail.com
 *
 */
public class QNameTokenUnionTest extends TestCase {

    
    public static Test suite()
    {
        return new TestSuite(QNameTokenUnionTest.class);
    }
    
    public void testQNameTokenUnionString() {
        //Test three possible token types.
        //#any token
        QNameTokenUnion union = QNameTokenUnion.ANY;
        assertFalse("A QNameTokenUnion with token #any should return false for isQName()", union.isQName());
        assertTrue("A QNameTokenUnion with token #any should return true for isToken()", union.isToken());
        assertNull("A QNameTokenUnion with token #any should return null for getQName()", union.getQName());
        assertTrue("A QNameTokenUnion with token #any should return #any for getToken()", union.getToken().equals("#any"));

        //#none token
        union = QNameTokenUnion.NONE;
        assertFalse("A QNameTokenUnion with token #none should return false for isQName()", union.isQName());
        assertTrue("A QNameTokenUnion with token #none should return true for isToken()", union.isToken());
        assertNull("A QNameTokenUnion with token #none should return null for getQName()", union.getQName());
        assertTrue("A QNameTokenUnion with token #none should return #none for getToken()", union.getToken().equals("#none"));

        //#other token
        union = QNameTokenUnion.OTHER;
        assertFalse("A QNameTokenUnion with token #other should return false for isQName()", union.isQName());
        assertTrue("A QNameTokenUnion with token #other should return true for isToken()", union.isToken());
        assertNull("A QNameTokenUnion with token #other should return null for getQName()", union.getQName());
        assertTrue("A QNameTokenUnion woth token #other should return #other for getToken()", union.getToken().equals("#other"));

    }

    public void testQNameTokenUnionQName() {
        QNameTokenUnion union = null; 
        
        //Construct with a QName.
        boolean notThrownException = true;
        try {
            union = new QNameTokenUnion(new QName("org.apache.woden"));
        } catch (NullPointerException e) {
            notThrownException = false;
        }
        assertTrue("A QNameTokenUnion with a valid QName should not throw a NullPointerException when constructed", notThrownException);
        assertTrue("The QName returned a different QName value to that which was set.", union.getQName().equals(new QName("org.apache.woden")));
        assertNull("A QNameTokenUnion with a valid QName should return null for getToken()", union.getToken());
        assertTrue("A QNameTokenUnion with a valid QName should return true for isQName()", union.isQName());
        assertFalse("A QNameTokenUnion with a valid QName should return false for isToken()", union.isToken());
        
        //Construct with a null QName.
        boolean thrownException = false;
        try {
            QName qname = null;
            union = new QNameTokenUnion(qname);
        } catch (NullPointerException e) {
            thrownException = true;
        }
        assertTrue("A QNameTokenUnion with a null QName argument should throw an NullPointerException when constructed", thrownException);
    }
    
}
