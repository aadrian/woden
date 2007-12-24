/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.woden.xpointer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class XPointerTest extends TestCase {

    
    public static Test suite()
    {
        return new TestSuite(XPointerTest.class);
    }
    
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testXPointerString() {
        XPointer xpointer;
        //Good Tests.
        String[] goodXPointers = new String[]{
                //Shorthand
                "justaShorthandPointer", 
                //element() scheme.
                "element(AnNCName)", "element(AnNCName/1)", "element(AnNCName/1/2/34)", "element(/1)", "element(/1/4/43)"
                };

        //Test XPointers
        for (int i=0; i< goodXPointers.length; i++) {
            try {
                xpointer = new XPointer(goodXPointers[i]);
                String result = xpointer.toString();
                assertEquals("The serialisation of XPointer: " + goodXPointers[i] + ", produced a different result: " + result, goodXPointers[i], result);
            } catch(InvalidXPointerException e) {
                fail("XPointer: " + goodXPointers[i] + ", is reported as being invalid when it actually is valid.");
            } catch(Exception e) {
                fail("Failed with unexpected exception: " + e);
            }
        }
        
        //Bad Tests.
        String[] badXPointers = new String[]{
                //Shorthand
                "justaShorthand##Pointer", 
                //element() scheme.
                "", "element(/)", "element(//)", "element(/1/2/3//)", "element(/1/b/3)", "element(Not!AnNCNa-me)",
                "element(/ncname)", "element(AnNCName/)", "element(AnNCName//)", "element(AnNCName/1/b)", "element(AnNCName/1/2//)"
                };

        //Test XPointers
        for (int i=0; i< badXPointers.length; i++) {
            try {
                xpointer = new XPointer(badXPointers[i]);
                fail("XPointer parser failed to thrown an exception for invalid XPointer: " + badXPointers[i]);
            } catch(Exception e) { //See if exception is anything other than InvalidXPointerException which we want.
                if (!(e instanceof InvalidXPointerException)) {
                    fail("Parsing the XPointer threw an unexpected exception: " + e + ", On XPointer: " + badXPointers[i]);
                }
            }
        }
    }

}
