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
package javax.xml.namespace;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * junit tests for the QName class.
 * 
 * @author jkaputin@apache.org
 */
public class QNameTest extends TestCase {

    private static final String emptyString = "";
    private QName qname;
    
    
    public static Test suite()
    {
        return new TestSuite(QNameTest.class);
    }

    /* ************************************************
     * Tests for ctor QName(locPart)
     * ************************************************/
    
    public void testOneArgCtorGoodLocalPart()
    {
        qname = new QName("myLocalPart");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals(emptyString) &&
                        qname.getLocalPart().equals("myLocalPart") &&
                        qname.getPrefix().equals(emptyString))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }

    public void testOneArgCtorGoodLocalPartEmptyString()
    {
        qname = new QName("");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals(emptyString) &&
                        qname.getLocalPart().equals(emptyString) &&
                        qname.getPrefix().equals(emptyString))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }

    public void testOneArgCtorBadLocalPartNull()
    {
        boolean b = false;
        
        try {
            qname = new QName(null);
        } catch (IllegalArgumentException e) {
            b = true;
        }
        
        assertTrue("Expected an IllegalArgumentException because of null localPart.", b);
    }

    /* ************************************************
     * Tests for ctor QName(NS, locPart)
     * ************************************************/
    
    public void testTwoArgCtorGoodNonEmptyStrings()
    {
        qname = new QName("myNamespace", "myLocalPart");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals("myNamespace") &&
                        qname.getLocalPart().equals("myLocalPart") &&
                        qname.getPrefix().equals(emptyString))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }

    public void testTwoArgCtorGoodEmptyStrings()
    {
        qname = new QName("", "");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals(emptyString) &&
                        qname.getLocalPart().equals(emptyString) &&
                        qname.getPrefix().equals(emptyString))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }

    public void testTwoArgCtorGoodNullNamespaceURI()
    {
        qname = new QName(null, "myLocalPart");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals(emptyString) &&
                        qname.getLocalPart().equals("myLocalPart") &&
                        qname.getPrefix().equals(emptyString))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }
    
    public void testTwoArgCtorBadNullLocalPart()
    {
        boolean b = false;
        
        try {
            qname = new QName("myNamespace", null);
        } catch (IllegalArgumentException e) {
            b = true;
        }
        
        assertTrue("Expected an IllegalArgumentException because of null localPart.", b);
    }
    
    /* ************************************************
     * Tests for ctor QName(NS, locPart, prefix)
     * ************************************************/
    
    public void testThreeArgCtorGoodNonEmptyStrings()
    {
        qname = new QName("myNamespace", "myLocalPart", "myPrefix");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals("myNamespace") &&
                        qname.getLocalPart().equals("myLocalPart") &&
                        qname.getPrefix().equals("myPrefix"))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }

    public void testThreeArgCtorGoodEmptyStrings()
    {
        qname = new QName("", "", "");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals(emptyString) &&
                        qname.getLocalPart().equals(emptyString) &&
                        qname.getPrefix().equals(emptyString))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }

    public void testThreeArgCtorGoodNullNamespaceURI()
    {
        qname = new QName(null, "myLocalPart", "myPrefix");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals(emptyString) &&
                        qname.getLocalPart().equals("myLocalPart") &&
                        qname.getPrefix().equals("myPrefix"))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }
    
    public void testThreeArgCtorBadNullLocalPart()
    {
        boolean b = false;
        
        try {
            qname = new QName("myNamespace", null, "myPrefix");
        } catch (IllegalArgumentException e) {
            b = true;
        }
        
        assertTrue("Expected an IllegalArgumentException because of null localPart.", b);
    }
    
    public void testThreeArgCtorBadNullPrefix()
    {
        boolean b = false;
        
        try {
            qname = new QName("myNamespace", "myLocalPart", null);
        } catch (IllegalArgumentException e) {
            b = true;
        }
        
        assertTrue("Expected an IllegalArgumentException because of null prefix.", b);
    }

    /* ************************************************
     * Tests for valueOf(String) method
     * ************************************************/
    
    public void testValueOfGoodNamespaceAndLocalPart()
    {
        qname = QName.valueOf("{myNamespace}myLocalPart");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals("myNamespace") &&
                        qname.getLocalPart().equals("myLocalPart") &&
                        qname.getPrefix().equals(emptyString))
                      );
        }
        else
        {
            fail("QName was not instantiated.");
        }
        
        assertEquals("Input string literal must match the output of toString()",
                "{myNamespace}myLocalPart",
                qname.toString() );
    }
    
    public void testValueOfGoodLocalPartOnly()
    {
        qname = QName.valueOf("myLocalPart");
        
        if(qname != null)
        {
            assertTrue("QName was not initialized correctly",
                       (qname.getNamespaceURI().equals(emptyString) &&
                        qname.getLocalPart().equals("myLocalPart") &&
                        qname.getPrefix().equals(emptyString))
                      );
            
            assertEquals("Input string literal must match the output of toString()",
                    "myLocalPart",
                    qname.toString() );
        }
        else
        {
            fail("QName was not instantiated.");
        }
    }
    
    public void testValueOfBadNullString()
    {
        boolean b = false;
        
        try {
            qname = QName.valueOf(null);
        } catch (IllegalArgumentException e) {
            b = true;
        }
        
        assertTrue("Expected an IllegalArgumentException because the string literal cannot be null.",
                   b);
    }
    
    public void testValueOfBadUnmatchedLeftBrace()
    {
        try {
            qname = QName.valueOf("{myNamespacemyLocalPart");
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("Expected an IllegalArgumentException because the starting left brace is not matched by a right brace: " + "\"{myNamespacemyLocalPart\"");
    }
    
    /* TODO these tests commented out for now (M7), because different QName impls get loaded depending on the
     * environment (e.g. Ant 1.7.0, Java 5.0) and as they all differ slightly, some of these testcases which
     * pass for the Woden QName class will fail for other QName implementations. Will need to either
     * use a classloader to ensure that the Woden QName class is always used for these testcases or maybe
     * just delete these testcases and keep the minimal testcases above for the valueOf() method. 
     * Note, it only seems to be the testcases for the valueOf() method that are affected.
     * 
    public void testValueOfBadNoLocalpart()
    {
        try {
            qname = QName.valueOf("{myNamespace}");
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("Expected an IllegalArgumentException because there is no local part: " + "\"{myNamespace}\"");
    }
    
    public void testValueOfGoodLeftBraceNotLeftmost()
    {
        //this is junk, but it is permitted according to the QName Javadoc
        try {
            qname = QName.valueOf("x{myNamespace}myLocalPart");
        } catch (IllegalArgumentException e) {
            fail("The QName literal is not illegal: " + "\"x{myNamespace}myLocalPart\"");
        }
        
        assertTrue("QName created from string literal is incorrect.",
                 (qname.getNamespaceURI().equals(emptyString) && 
                  qname.getLocalPart().equals("x{myNamespace}myLocalPart") &&
                  qname.getPrefix().equals(emptyString)) );
        
        assertEquals("Input string literal must match the output of toString()",
                "x{myNamespace}myLocalPart",
                qname.toString() );
    }
    
    public void testValueOfGoodMultipleLeftBraces()
    {
        //this is junk, but it is permitted according to the QName Javadoc
        try {
            qname = QName.valueOf("{myNam{espace}myLocalPart");
        } catch (IllegalArgumentException e) {
            fail("The QName literal is not illegal: " + "\"{myNam{espace}myLocalPart\"");
        }
        
        assertTrue("QName created from string literal is incorrect.",
                (qname.getNamespaceURI().equals("myNam{espace") && 
                 qname.getLocalPart().equals("myLocalPart") && 
                 qname.getPrefix().equals(emptyString)) );
        
        assertEquals("Input string literal must match the output of toString()",
                "{myNam{espace}myLocalPart",
                qname.toString() );
    }
    
    public void testValueOfGoodUnmatchedRightBrace()
    {
        //this is junk, but it is permitted according to the QName Javadoc
        try {
            qname = QName.valueOf("myNamespace}myLocalPart");
        } catch (IllegalArgumentException e) {
            fail("The QName literal is not illegal: " + "\"myNamespace}myLocalPart\"");
        }
        
        assertTrue("QName created from string literal is incorrect.",
                (qname.getNamespaceURI().equals(emptyString) && 
                 qname.getLocalPart().equals("myNamespace}myLocalPart") &&
                 qname.getPrefix().equals(emptyString)) );
        
        assertEquals("Input string literal must match the output of toString()",
                "myNamespace}myLocalPart",
                qname.toString() );
    }

    public void testValueOfGoodMultipleRightBraces()
    {
        //this is junk, but it is permitted according to the QName Javadoc
        try {
            qname = QName.valueOf("{myNam}espace}myLocalPart");
        } catch (IllegalArgumentException e) {
            fail("The QName literal is not illegal: " + "\"{myNam}espace}myLocalPart\"");
        }
        
        assertTrue("QName created from string literal is incorrect.",
                (qname.getNamespaceURI().equals("myNam") && 
                 qname.getLocalPart().equals("espace}myLocalPart") &&
                 qname.getPrefix().equals(emptyString)) );
        
        assertEquals("Input string literal must match the output of toString()",
                "{myNam}espace}myLocalPart",
                qname.toString() );
    }
    */

    /* ************************************************
     * Tests for deserializing.
     * ************************************************/
    
    public void testGoodSerializeThenDeserialize() throws IOException,
                                                          ClassNotFoundException
    {
        //Serialize a QName then deserialize it to test it has been initialized 
        //correctly by the readObject() method.
        
        qname = new QName("myNamespace", "myLocalPart", "myPrefix");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(qname);
        oos.close();

        QName qnameCopy = null;
        
        ObjectInputStream ois =
            new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Object o = ois.readObject();
        qnameCopy = (QName)o;
        ois.close();

        assertTrue("QName was not initialized correctly after deserialization.",
                (qnameCopy.getNamespaceURI().equals("myNamespace") &&
                 qnameCopy.getLocalPart().equals("myLocalPart") &&
                 qnameCopy.getPrefix().equals("myPrefix")));
        
    }  

    /* ************************************************
     * Tests for equals(obj) method
     * ************************************************/
    
    public void testEqualsGoodSameValues()
    {
        QName qn1, qn2;
        qn1 = new QName("myNamespace", "myLocalPart");
        qn2 = QName.valueOf("{myNamespace}myLocalPart");
        
        assertTrue("Expected two QNames objects with the same values to be equal.",
                   qn1.equals(qn2));
    }

    public void testEqualsGoodDifferentPrefix()
    {
        /* the prefix is not used to determine equality, so even though the prefixes
         * are different, the namespacesURIs and localParts are equivalent so the
         * objects are equal.
         */
        
        QName qn1, qn2;
        qn1 = new QName("myNamespace", "myLocalPart", "myPrefix");
        qn2 = QName.valueOf("{myNamespace}myLocalPart");   //prefix defaults to ""
        
        assertTrue("Expected two QNames objects with the same values to be equal.",
                   qn1.equals(qn2));
    }

    public void testEqualsGoodDifferentNamespaceAndLocalPart()
    {
        QName qn1, qn2;
        qn1 = new QName("myNamespace", "myLocalPart", "");
        qn2 = QName.valueOf("{yourNamespace}yourLocalPart"); //prefix defaults to ""
        
        assertFalse("Expected two QNames objects with different values to be not equal.",
                   qn1.equals(qn2));
    }

    public void testEqualsGoodSameObjectRef()
    {
        QName qn1, qn2;
        qn1 = new QName("myNamespace", "myLocalPart", "");
        qn2 = qn1;
        
        //qn1 and qn2 now refer to the same object.
        
        assertTrue("Expected two QNames with the same object reference to be equal.",
                    qn1.equals(qn2));
    }

    public void testEqualsGoodNotAQName()
    {
        QName qn1;
        qn1 = new QName("myNamespace", "myLocalPart", "");
        Object o = new Object();
        
        assertFalse("Expected a QName and a different type of object to be not equal.",
                   qn1.equals(o));
    }


    
}
