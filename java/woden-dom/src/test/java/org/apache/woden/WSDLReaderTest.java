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

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.woden.tests.TestErrorHandler;
import org.apache.woden.wsdl20.Description;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WSDLReaderTest extends TestCase 
{
  private WSDLFactory factory = null;
  private WSDLReader reader = null;
  
  public static Test suite()
  {
	return new TestSuite(WSDLReaderTest.class);
  }

  protected void setUp() throws Exception 
  {
	//Create wsdl20 factory and reader.
	try
	{
      factory = WSDLFactory.newInstance();
      reader = factory.newWSDLReader();  
    } 
	catch (Exception e) 
	{
    }
	//Set error handler.
	reader.getErrorReporter().setErrorHandler(new TestErrorHandler());
  }

  protected void tearDown() throws Exception 
  {
	factory = null;
	reader = null;
  }
  
  public void testReadValidWSDL20()
  {
	  Description desc = null;
	  try
	  {
        URL wsdlURL = getClass().getClassLoader().getResource("org/apache/woden/primer-hotelReservationService.wsdl");
	    desc = reader.readWSDL(wsdlURL.toString());
	  }
	  catch(WSDLException e)
	  {
          fail("Unexpected exception: " + e.getMessage());
	  }
      assertNotNull("The description returned is null.", desc);
  }
  
  public void testReadInvalidWSDL20()
  {
	  try
	  {
		URL wsdlURL = getClass().getClassLoader().getResource("org/apache/woden/badDescriptionTags.wsdl");
		reader.readWSDL(wsdlURL.toString());
        fail("Expected a WSDLException because the \"description\" tag was deliberately misspelt.");
	  }
	  catch(WSDLException e)
	  {
          assertTrue("Expected a WSDLException with message containing \"WSDL501\", but got: " + e.getMessage() ,
             e.getMessage().indexOf("WSDL501") > -1);
	  }
  }
  
  public void testReadWSDLSourceDoc()
  {
      Description desc = null;
      try
      {
        URL wsdlURL = getClass().getClassLoader().getResource("org/apache/woden/primer-hotelReservationService.wsdl");
        String wsdlURLStr = wsdlURL.toString();
        URI wsdlURI = URI.create(wsdlURLStr);
        
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            doc = builder.parse(new InputSource(wsdlURLStr));
        } catch (FactoryConfigurationError e1) {
            fail("Unexpected exception: " + e1.getMessage());
        } catch (ParserConfigurationException e1) {
            fail("Unexpected exception: " + e1.getMessage());
        } catch (SAXException e1) {
           fail("Unexpected exception: " + e1.getMessage());
        } catch (IOException e1) {
            fail("Unexpected exception: " + e1.getMessage());
        }
        
        WSDLSource wsdlSource = reader.createWSDLSource();
        wsdlSource.setBaseURI(wsdlURI);
        wsdlSource.setSource(doc);
        desc = reader.readWSDL(wsdlSource);
      }
      catch(WSDLException e)
      {
          fail("Unexpected exception: " + e.getMessage());
      }
      assertNotNull("The description returned is null.", desc);
  }

  public void testReadWSDLSourceIS()
  {
      Description desc = null;
      try
      {
        URL wsdlURL = getClass().getClassLoader().getResource("org/apache/woden/primer-hotelReservationService.wsdl");
        String wsdlURLStr = wsdlURL.toString();
        
        InputSource is = new InputSource(wsdlURLStr);
        URI wsdlURI = URI.create(wsdlURLStr);
        
        WSDLSource wsdlSource = reader.createWSDLSource();
        wsdlSource.setBaseURI(wsdlURI);
        wsdlSource.setSource(is);
        desc = reader.readWSDL(wsdlSource);
      }
      catch(WSDLException e)
      {
          fail("Unexpected exception: " + e.getMessage());
      }
      assertNotNull("The description returned is null.", desc);
  }
  
  public void testReadEmbeddedWSDLSource() {
      Description desc = null;
      //Load in a WSDL 2.0 file
      URL wsdlURL = getClass().getClassLoader().getResource("org/apache/woden/embeded.xml");

      //Good Tests.
      String[] goodFragids = new String[]{
              //Shorthand
              "#wsdlRoot", 
              //element() scheme.
              "#element(wsdlRoot)", "#element(first/1/2)", "#element(/1/1/2)", "#element(second/2)"
              };

      //Test fragids
      for (int i=0; i< goodFragids.length; i++) {
          try {
             desc = reader.readWSDL(wsdlURL.toString() + goodFragids[i]); 
          } catch(WSDLException e) {
             fail("Failed with unexpected exception: " + e);
          }
          assertNotNull("Failed to load the embedded wsdl20 description with fragid: " + goodFragids[i], desc);
      }
      
      //Bad Tests - Invalid XPointer. (Can't programmatically see between bad syntax and not pointing unless we modify WSDLException)
      String[] badFragids = new String[]{
              //Shorthand - bad syntax.
              "#wsdl#Root", "#wsd(Root",
              //Shorthand - don't point.
              "#wsdlRootElement", "#nonexistentFragment", 
              //element() scheme. - bad syntax.
              "#element(wsdlRoot//)", "#element(/wsdlRoot)", "#element(wsdlRoot/)", "#element(wsdl,Root/1/1/2)", "#element(second/a)", 
              //element() scheme - don't point.
              "#element(wsdlRoot/20)", "#element(/4/1/2)", "#element(second/3)"
              };

      //Test fragids
      for (int i=0; i< badFragids.length; i++) {
          try {
             desc = reader.readWSDL(wsdlURL.toString() + badFragids[i]); 
          } catch(WSDLException e) {
              assertEquals("Expected exception PARSE_ERROR for invalid XPoitner: " + badFragids[i] + ", but got the exception: " + e, "PARSER_ERROR", e.getFaultCode());
              continue;
          }
          fail("XPointer parse didn't not throw exception for invalid fragid: " + badFragids[i]);
      }
  }
}
