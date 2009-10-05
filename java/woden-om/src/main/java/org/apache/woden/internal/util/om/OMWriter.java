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

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.om.OMNode;
import org.apache.woden.internal.util.ObjectRegistry;
import org.apache.woden.internal.wsdl20.Constants;


/**
 * @author Sagara Gunathunga (sagara.gunathunga@gmail.com)
 *
 */
public class OMWriter {
    /**
     * The namespaceURI represented by the prefix <code>xmlns</code>.
     */
    private static String NS_URI_XMLNS = "http://www.w3.org/2000/xmlns/";

    /**
     * The namespaceURI represented by the prefix <code>xml</code>.
     */
    private static String NS_URI_XML = "http://www.w3.org/XML/1998/namespace";

    private static Map xmlEncodingMap = new HashMap();

    static
    {
        xmlEncodingMap.put(null, Constants.XML_DECL_DEFAULT);
        xmlEncodingMap.put(System.getProperty("file.encoding"),
                Constants.XML_DECL_DEFAULT);
        xmlEncodingMap.put("UTF8", "UTF-8");
        xmlEncodingMap.put("UTF-16", "UTF-16");
        xmlEncodingMap.put("UnicodeBig", "UTF-16");
        xmlEncodingMap.put("UnicodeLittle", "UTF-16");
        xmlEncodingMap.put("ASCII", "US-ASCII");
        xmlEncodingMap.put("ISO8859_1", "ISO-8859-1");
        xmlEncodingMap.put("ISO8859_2", "ISO-8859-2");
        xmlEncodingMap.put("ISO8859_3", "ISO-8859-3");
        xmlEncodingMap.put("ISO8859_4", "ISO-8859-4");
        xmlEncodingMap.put("ISO8859_5", "ISO-8859-5");
        xmlEncodingMap.put("ISO8859_6", "ISO-8859-6");
        xmlEncodingMap.put("ISO8859_7", "ISO-8859-7");
        xmlEncodingMap.put("ISO8859_8", "ISO-8859-8");
        xmlEncodingMap.put("ISO8859_9", "ISO-8859-9");
        xmlEncodingMap.put("ISO8859_13", "ISO-8859-13");
        xmlEncodingMap.put("ISO8859_15_FDIS", "ISO-8859-15");
        xmlEncodingMap.put("GBK", "GBK");
        xmlEncodingMap.put("Big5", "Big5");
    }



    public static String java2XMLEncoding(String javaEnc)
    {
        return (String)xmlEncodingMap.get(javaEnc);
    }

    public static void serializeAsXML(OMNode node, Writer writer)
    {

        ObjectRegistry namespaceStack = new ObjectRegistry();

        namespaceStack.register("xml", NS_URI_XML);

        PrintWriter pw = new PrintWriter(writer);
        String javaEncoding = (writer instanceof OutputStreamWriter)
        ? ((OutputStreamWriter) writer).getEncoding()
                : null;

        print(node, namespaceStack, pw, java2XMLEncoding(javaEncoding));
    }


    private static void print(OMNode node, ObjectRegistry namespaceStack,
            PrintWriter out, String xmlEncoding) {
        /**
         * check this  correct with DOM2Writer method 
         */
        if (node == null) {
            return;
        }
        try{
           
            //TODO - The method serialize(Writer) from the
            //       type OMNode is deprecated on AXIOM

            node.serialize(out);
        }catch(Exception e){
            e.printStackTrace();
        }  
    }



}
