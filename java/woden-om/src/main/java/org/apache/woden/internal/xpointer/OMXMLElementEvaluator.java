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
package org.apache.woden.internal.xpointer;

//Java
import javax.xml.namespace.QName;

//Woden
import org.apache.woden.XMLElement;
import org.apache.woden.internal.OMXMLElement;
import org.apache.woden.ErrorReporter;

//XPointer
import org.apache.woden.xpointer.XPointer;

//OM
import org.apache.axiom.om.OMElement;

/**
 * This class extends XMLElementEvaluator to support the OM implementation in XMLElement
 * 
 * @author Dan Harvey <danharvey42@gmail.com>
 *
 */
public class OMXMLElementEvaluator extends XMLElementEvaluator {

    /**
     * Constructs a new OMXMLElementEvaluator to evaluate a XPointer on a OM Element.
     * 
     * @param xpointer an XPointer to evaluate.
     * @param element an OMElement to be evaluated. 
     * @param errorReporter the Woden Error Reporter context object.
     */
    public OMXMLElementEvaluator(XPointer xpointer, OMElement element, ErrorReporter errorReporter) {
        super(xpointer, createXMLElement(element, errorReporter));
    }
    
    /*
     * (non-Javadoc)
     * @see org.apache.woden.internal.xpointer.XMLElementEvaluator#testElementShorthand(org.apache.woden.XMLElement, java.lang.String)
     */
    public boolean testElementShorthand(XMLElement element, String shorthand) {
        // Simple http://www.w3.org/TR/xml-id/ support for now until we support full scheme based ID's.
        OMElement omElement = (OMElement)element.getSource();
        String attr = omElement.getAttributeValue(new QName("http://www.w3.org/XML/1998/namespace", "id"));
        return attr != null && attr.equals(shorthand);
    }

    /**
     * Evaluates the XPointer on the root Element and returns the resulting
     * Element or null.
     * 
     * @return an Element from the resultant evaluation of the root Element or
     *         null if evaluation fails.
     */
    public OMElement evaluateElement() {
        XMLElement element = evaluate();
        if (element != null) return (OMElement) element.getSource();
        return null;
    }

    // Private methods
    private static XMLElement createXMLElement(Object elem, ErrorReporter errorReporter) {
        OMXMLElement omXMLElement = new OMXMLElement(errorReporter);
        omXMLElement.setSource(elem);
        return omXMLElement;

    }
}
