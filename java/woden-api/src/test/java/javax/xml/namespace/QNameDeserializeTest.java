package javax.xml.namespace;

/**
 * Licensed to the apache Software Foundation (ASF) under one or more
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


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
// * junit test for deserializing with the QName class.
 * 
 * @author jkaputin@apache.org
 */
public class QNameDeserializeTest extends TestCase {

    private static final String emptyString = "";
    private QName qname;
    
    public static Test suite()
    {
        return new TestSuite(QNameDeserializeTest.class);
    }
    
    public void testGoodDeserializeQNameWithoutPrefix() throws IOException, 
                                                               ClassNotFoundException, Exception
    {
        /* 
         * Test that 'prefix' is initialized correctly when deserializing a v1.0 QName.
         * The v1.0 QName does not have a 'prefix' field, so when it's deserialized using
         * the v1.1 QName, its 'prefix' field should be set to the empty string "", not 
         * just initialized as null.
         * 
         * The input to this testcase is a file containing a v1.0 QName serialized 
         * from the WSDL4J version of QName.
         */
        
        URL url = getClass().getClassLoader().getResource("javax/xml/namespace/serialized_QName_no_prefix");

        ObjectInputStream ois =
            new ObjectInputStream(url.openStream());
        
        Object o = ois.readObject();
        qname = (QName)o;
        
        assertTrue("Expected a null 'prefix' to be initialized to the empty string \"\".",
                qname.getPrefix() != null && qname.getPrefix().equals(emptyString));
            
    }
}
