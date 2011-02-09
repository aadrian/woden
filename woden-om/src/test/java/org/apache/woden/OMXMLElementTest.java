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
package org.apache.woden;

import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.woden.internal.OMXMLElement;
import org.apache.woden.internal.ErrorReporterImpl;
import org.apache.woden.internal.wsdl20.Constants;
import org.apache.woden.internal.util.om.OMQNameUtils;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

public class OMXMLElementTest extends TestCase {

    private URL wsdlURL = getClass().getClassLoader().getResource("org/apache/woden/primer-hotelReservationService.wsdl");
    private ErrorReporter errorReporter;
    private OMElement elem = null;

  public static Test suite(){
    return new TestSuite(OMXMLElementTest.class);
  }

  protected void setUp() throws Exception{
      InputStream in = wsdlURL.openStream();
      StAXOMBuilder builder = new StAXOMBuilder(in);
      elem = builder.getDocumentElement();

      errorReporter = new ErrorReporterImpl();

  }

  protected void tearDown() throws Exception{
  }

  public void testGetFirstChildElement() throws WSDLException {
      OMXMLElement omXMLElement = new OMXMLElement(errorReporter);
      omXMLElement.setSource(elem);
      assertNotNull(omXMLElement.getFirstChildElement());
  }

  /* TODO implement this method only if getAttributes() is added to XMLElement.
   * 
    public void testGetAttributes() throws WSDLException {

        //The <binding> element in the hotelReservation WSDL has many attributes
        //So, let's test with that element
        OMXMLElement omXMLElement = new OMXMLElement(errorReporter);
        omXMLElement.setSource(elem);
        Object obj;
        OMElement tempEl;
        if ((obj = omXMLElement.getSource()) instanceof OMElement){
            tempEl = (OMElement)obj;
            Iterator childEls = tempEl.getChildElements();
            while (childEls.hasNext()){
                OMElement childEl = (OMElement) childEls.next();
                 if (OMQNameUtils.matches(Constants.Q_ELEM_BINDING, childEl)){
                    omXMLElement.setSource(childEl);
                    assertNotNull(omXMLElement.getAttributes());
                }
            }
        }
    }
    */

    public void testGetAttributeValue() throws WSDLException {
        OMXMLElement omXMLElement = new OMXMLElement(errorReporter);
        omXMLElement.setSource(elem);
        Object obj;
        OMElement tempEl;
        if ((obj = omXMLElement.getSource()) instanceof OMElement){
            tempEl = (OMElement)obj;
            Iterator childEls = tempEl.getChildElements();
            while (childEls.hasNext()){
                OMElement childEl = (OMElement) childEls.next();
                 if (OMQNameUtils.matches(Constants.Q_ELEM_BINDING, childEl)){
                    omXMLElement.setSource(childEl);
                    assertNotNull(omXMLElement.getAttributeValue("name"));
                     break;
                }

            }

        }
    }

    public void testGetQName() throws WSDLException {
        OMXMLElement omXMLElement = new OMXMLElement(errorReporter);
        omXMLElement.setSource(elem);
        Object obj;
        OMElement tempEl;
        if ((obj = omXMLElement.getSource()) instanceof OMElement){
            tempEl = (OMElement)obj;
            Iterator childEls = tempEl.getChildElements();
            while (childEls.hasNext()){
                OMElement childEl = (OMElement) childEls.next();
                 if (OMQNameUtils.matches(Constants.Q_ELEM_BINDING, childEl)){
                    omXMLElement.setSource(childEl);
                    assertNotNull(omXMLElement.getQName("wsoap:protocol"));
                }
            }
        }
    }
}
