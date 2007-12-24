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
package org.apache.woden.tests;

import javax.xml.namespace.QNameDeserializeTest;
import javax.xml.namespace.QNameTest;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.woden.ant.ObjectIdTableTest;
import org.apache.woden.schema.SchemaTest;
import org.apache.woden.types.NCNameTest;
import org.apache.woden.wsdl20.xml.ChildElementCreationTest;

public class AllWodenTests extends TestSuite 
{
  /**
   * Create this test suite.
   * 
   * @return This test suite.
   */
  public static Test suite()
  {
    return new AllWodenTests();
  }
  
  public static void main(String[] args) {
    junit.textui.TestRunner.run(AllWodenTests.suite());
  }
  
  /**
   * Constructor
   */
  public AllWodenTests()
  {
      super("AllWodenTests");
    
      String ver = System.getProperty("java.version");
      if(ver.startsWith("1.4"))
      {
          /*
           * From Java 5.0 the QName class is included in the jre and loaded
           * by the bootstrap classloader, so we don't want to run Woden's QName
           * tests against the Java 5 implementation. However, we are running in a 
           * 1.4 jvm now, so the Woden QName class will be loaded and we do want to
           * run the Woden junit tests for this class.
           */
          addTest(QNameTest.suite());
          addTest(QNameDeserializeTest.suite());
      }
    
      addTest(AllWodenTestsDOM.suite());
      addTest(AllWodenTestsOM.suite());
      addTest(ChildElementCreationTest.suite());
      addTest(NCNameTest.suite());
      addTest(ObjectIdTableTest.suite());
      addTest(SchemaTest.suite());
    //TODO in-progress 30May06 tests for BindingOpExt and BindingMsgRefExt
  }
	
}
