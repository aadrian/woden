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

import org.apache.woden.DOMXMLElementTest;
import org.apache.woden.WSDLFactoryTest;
import org.apache.woden.WSDLReaderTest;
import org.apache.woden.internal.ReaderFeaturesTest;
import org.apache.woden.internal.wsdl20.validation.WSDLComponentValidatorTest;
import org.apache.woden.internal.wsdl20.validation.WSDLDocumentValidatorTest;
import org.apache.woden.resolver.SimpleURIResolverTest;
import org.apache.woden.wsdl20.BindingFaultReferenceTest;
import org.apache.woden.wsdl20.BindingFaultTest;
import org.apache.woden.wsdl20.BindingMessageReferenceTest;
import org.apache.woden.wsdl20.BindingOperationTest;
import org.apache.woden.wsdl20.BindingTest;
import org.apache.woden.wsdl20.DescriptionTest;
import org.apache.woden.wsdl20.ElementDeclarationTest;
import org.apache.woden.wsdl20.EndpointTest;
import org.apache.woden.wsdl20.InterfaceFaultReferenceTest;
import org.apache.woden.wsdl20.InterfaceFaultTest;
import org.apache.woden.wsdl20.InterfaceMessageReferenceTest;
import org.apache.woden.wsdl20.InterfaceOperationTest;
import org.apache.woden.wsdl20.InterfaceTest;
import org.apache.woden.wsdl20.ServiceTest;
import org.apache.woden.wsdl20.TypeDefinitionTest;
import org.apache.woden.wsdl20.extensions.ExtensionRegistryTest;
import org.apache.woden.wsdl20.extensions.http.HTTPBindingExtensionsTest;
import org.apache.woden.wsdl20.extensions.http.HTTPBindingFaultExtensionsTest;
import org.apache.woden.wsdl20.extensions.http.HTTPBindingMessageReferenceExtensionsTest;
import org.apache.woden.wsdl20.extensions.http.HTTPBindingOperationExtensionsTest;
import org.apache.woden.wsdl20.extensions.http.HTTPEndpointExtensionsTest;
import org.apache.woden.wsdl20.extensions.http.HTTPLocationTemplateTest;
import org.apache.woden.wsdl20.extensions.http.HTTPLocationTest;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingExtensionsTest;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingFaultExtensionsTest;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingFaultReferenceExtensionsTest;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingMessageReferenceExtensionsTest;
import org.apache.woden.wsdl20.extensions.soap.SOAPBindingOperationExtensionsTest;
import org.apache.woden.wsdl20.fragids.FragmentIdentificationTest;
import org.apache.woden.wsdl20.xml.BindingElementTest;
import org.apache.woden.wsdl20.xml.BindingFaultElementTest;
import org.apache.woden.wsdl20.xml.BindingFaultReferenceElementTest;
import org.apache.woden.wsdl20.xml.BindingMessageReferenceElementTest;
import org.apache.woden.wsdl20.xml.BindingOperationElementTest;
import org.apache.woden.wsdl20.xml.DescriptiontElementTest;
import org.apache.woden.wsdl20.xml.DocumentationElementTest;
import org.apache.woden.wsdl20.xml.EndpointElementTest;
import org.apache.woden.wsdl20.xml.ImportElementTest;
import org.apache.woden.wsdl20.xml.IncludeElementTest;
import org.apache.woden.wsdl20.xml.InterfaceElementTest;
import org.apache.woden.wsdl20.xml.InterfaceFaultElementTest;
import org.apache.woden.wsdl20.xml.InterfaceFaultReferenceElementTest;
import org.apache.woden.wsdl20.xml.InterfaceMessageReferenceElementTest;
import org.apache.woden.wsdl20.xml.InterfaceOperationElementTest;
import org.apache.woden.wsdl20.xml.NameAttributeTest;
import org.apache.woden.wsdl20.xml.ServiceElementTest;
import org.apache.woden.wsdl20.xml.TypesElementTest;
import org.apache.woden.xml.IntOrTokenAttrTest;
import org.apache.woden.xml.TokenAttrTest;
import org.apache.woden.xpointer.XPointerTest;

import testcase.extensions.foo.FooBindingExtensionsTest;
import testcase.resolver.schemaloc.SchemaLocationTest;

public class AllWodenTestsDOM extends TestSuite
{
  /**
   * Create this test suite.
   *
   * @return This test suite.
   */
  public static Test suite()
  {
    return new AllWodenTestsDOM();
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(AllWodenTestsDOM.suite());
  }

  /**
   * Constructor
   */
  public AllWodenTestsDOM()
  {
    super("AllWodenTestsDOM");

	addTest(WSDLFactoryTest.suite());
    addTest(WSDLReaderTest.suite());
    addTest(DOMXMLElementTest.suite());
   //addTest(W3CTestSuiteTest.suite()); W3C testsuite is run via /ant-test/build.xml and results are compared to W3C baseline
	addTestSuite(ReaderFeaturesTest.class);
	addTest(WSDLDocumentValidatorTest.suite());
	addTest(WSDLComponentValidatorTest.suite());
    addTest(ServiceElementTest.suite());
    addTest(EndpointElementTest.suite());
    addTest(NameAttributeTest.suite());
    addTest(IntOrTokenAttrTest.suite());
    addTest(TokenAttrTest.suite());
    addTest(SOAPBindingExtensionsTest.suite());
    addTest(SOAPBindingFaultExtensionsTest.suite());
    addTest(SOAPBindingOperationExtensionsTest.suite());
    addTest(SOAPBindingMessageReferenceExtensionsTest.suite());
    addTest(SOAPBindingFaultReferenceExtensionsTest.suite());
    addTest(HTTPBindingExtensionsTest.suite());
    addTest(HTTPBindingFaultExtensionsTest.suite());
    addTest(HTTPBindingOperationExtensionsTest.suite());
    addTest(HTTPBindingMessageReferenceExtensionsTest.suite());
    addTest(HTTPLocationTest.suite());
    addTest(HTTPLocationTemplateTest.suite());
    addTest(HTTPEndpointExtensionsTest.suite());
    addTest(SimpleURIResolverTest.suite());
    addTest(ImportElementTest.suite());
    addTest(IncludeElementTest.suite());
    addTest(EndpointTest.suite());
    addTest(ServiceTest.suite());
    addTest(DescriptionTest.suite());
    addTest(DescriptiontElementTest.suite());
    addTest(TypesElementTest.suite());
    addTest(ElementDeclarationTest.suite());
    addTest(TypeDefinitionTest.suite());
    addTest(InterfaceElementTest.suite());
    addTest(InterfaceFaultElementTest.suite());
    addTest(InterfaceOperationElementTest.suite());
    addTest(InterfaceFaultReferenceElementTest.suite());
    addTest(InterfaceMessageReferenceElementTest.suite());
    addTest(InterfaceTest.suite());
    addTest(InterfaceFaultTest.suite());
    addTest(InterfaceOperationTest.suite());
    addTest(InterfaceFaultReferenceTest.suite());
    addTest(InterfaceMessageReferenceTest.suite());
    addTest(BindingElementTest.suite());
    addTest(BindingFaultElementTest.suite());
    addTest(BindingOperationElementTest.suite());
    addTest(BindingFaultReferenceElementTest.suite());
    addTest(BindingMessageReferenceElementTest.suite());
    addTest(BindingTest.suite());
    addTest(BindingFaultTest.suite());
    addTest(BindingOperationTest.suite());
    addTest(BindingFaultReferenceTest.suite());
    addTest(BindingMessageReferenceTest.suite());
    addTest(DocumentationElementTest.suite());
    addTest(FragmentIdentificationTest.suite());    
    addTest(ExtensionRegistryTest.suite());
    addTest(FooBindingExtensionsTest.suite());
    addTest(XPointerTest.suite());
    addTest(SchemaLocationTest.suite());
    //TODO in-progress 30May06 tests for BindingOpExt and BindingMsgRefExt
  }

}
