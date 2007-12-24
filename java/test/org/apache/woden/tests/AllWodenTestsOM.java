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

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.woden.OMWSDLFactoryTest;
import org.apache.woden.OMWSDLReaderTest;
import org.apache.woden.OMXMLElementTest;
import org.apache.woden.resolver.OMSimpleURIResolverTest;
import org.apache.woden.wsdl20.xml.OMEndpointElementTest;
import org.apache.woden.wsdl20.xml.OMServiceElementTest;

public class AllWodenTestsOM extends TestSuite{

  /**
   * Create this test suite.
   *
   * @return This test suite.
   */
  public static Test suite(){
    return new AllWodenTestsOM();
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(AllWodenTestsOM.suite());
  }

  /**
   * Constructor
   */
  public AllWodenTestsOM(){
    super("AllWodenTestsOM");

    addTest(OMWSDLFactoryTest.suite());
	addTest(OMWSDLReaderTest.suite());
   //addTest(OMW3CTestSuiteTest.suite());  W3C testsuite is run via /ant-test/build.xml and results are compared to W3C baseline.
    addTest(OMServiceElementTest.suite());
    addTest(OMEndpointElementTest.suite());
    addTest(OMSimpleURIResolverTest.suite());
    addTest(OMXMLElementTest.suite());
  }

}
