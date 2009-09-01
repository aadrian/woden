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
package org.apache.woden.internal.util.om;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.xml.sax.InputSource;

/**
 * This class contains utility methods required for parsing elements
 * in a WSDL using AXIOM.
 */
public class OMUtils {

    /**
     * @param strUri The URI where the WSDL can be found
     * @return A StAXOMBuilder which could be used in obtaining the document object
     * @throws IOException 
     */
    public static StAXOMBuilder getOMBuilder(String strUri) throws IOException, URISyntaxException, XMLStreamException  {
        StAXOMBuilder builder = null;
        
        URI uri = new URI(strUri);
        URL url = uri.toURL();
        
        InputStream in = url.openStream();
        builder = new StAXOMBuilder(in);
        
        return builder;
    }


    /**
     * todo add validation
     * @param uri of the OMDocument
     * @return an OMElement representing the document just read
     * @throws IOException 
     */
    public static OMElement getElement(String uri) throws IOException, URISyntaxException, XMLStreamException {
        StAXOMBuilder builder = OMUtils.getOMBuilder(uri);
        return builder == null ? null : builder.getDocumentElement();
    }

    /**
     * Returns the value of an attribute of an element. Returns null
     * if the attribute is not found
     * @param omElement Element whose attrib is looked for
     * @param attrName  name of attribute to look for
     * @return the attribute value
     */
    public static String getAttribute(OMElement omElement, String attrName) {
        String val = null;
        Iterator allAttr = omElement.getAllAttributes();
        while(allAttr.hasNext()){
            OMAttribute attr = (OMAttribute)allAttr.next();
            if (attr.getLocalName().equals(attrName)){
                val = attr.getAttributeValue();
            }
        }
        return val;
    }

    /**
     * @param element OMElement which would most probably contain <xs:schema>
     * @return a SAX inputsource from the OMElement
     */
    public static InputSource getInputSource(OMElement element){

        String elementString;
        byte[] bytes = new byte[0];

        //Obtain the String value of the OMElement after building the OM tree
        try {
            elementString = element.toStringWithConsume();
            bytes= elementString.getBytes();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        //Deserialize from a byte array
        InputStream inputStream = new ByteArrayInputStream(bytes);

        return new InputSource(inputStream);
    }
    
}