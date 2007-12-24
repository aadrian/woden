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
package org.apache.woden.internal.schema;

import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;

/**
 * Constants for XML Schema elements, attributes and URIs.
 * 
 * @author jkaputin@apache.org
 */
public class SchemaConstants {

    //Schema attribute names
    public static final String ATTR_ID = "id";
    public static final String ATTR_SCHEMA_LOCATION = "schemaLocation";
    
    //Schema element names
    public static final String ELEM_SCHEMA = "schema";
    public static final String ELEM_SCHEMA_IMPORT = "import";
    public static final String ELEM_SCHEMA_INCLUDE = "include";
    public static final String ELEM_SCHEMA_REDEFINE = "redefine";

    //Schema uri
    public static final String NS_URI_XSD_1999 =
        "http://www.w3.org/1999/XMLSchema";
    public static final String NS_URI_XSD_2000 =
        "http://www.w3.org/2000/10/XMLSchema";
    public static final String NS_URI_XSD_2001 =
        "http://www.w3.org/2001/XMLSchema";
    
    //Schema attribute qnames
    public static final QName Q_ATTR_ID = new QName(ATTR_ID);
    
    //<xs:schema> qnames
    public static final QName Q_ELEM_XSD_1999 =
        new QName(NS_URI_XSD_1999, ELEM_SCHEMA);
    public static final QName Q_ELEM_XSD_2000 =
        new QName(NS_URI_XSD_2000, ELEM_SCHEMA);
    public static final QName Q_ELEM_XSD_2001 =
        new QName(NS_URI_XSD_2001, ELEM_SCHEMA);
    public static final List XSD_SCHEMA_QNAME_LIST = Arrays.asList(new QName[]
        {Q_ELEM_XSD_1999, Q_ELEM_XSD_2000, Q_ELEM_XSD_2001});
    
    //<xs:import> qnames
    public static final QName Q_ELEM_IMPORT_XSD_1999 = new QName(
        NS_URI_XSD_1999, ELEM_SCHEMA_IMPORT);
    public static final QName Q_ELEM_IMPORT_XSD_2000 = new QName(
        NS_URI_XSD_2000, ELEM_SCHEMA_IMPORT);
    public static final QName Q_ELEM_IMPORT_XSD_2001 = new QName(
        NS_URI_XSD_2001, ELEM_SCHEMA_IMPORT);
    public static final List XSD_IMPORT_QNAME_LIST = Arrays.asList(new QName[] 
        { Q_ELEM_IMPORT_XSD_1999, Q_ELEM_IMPORT_XSD_2000, Q_ELEM_IMPORT_XSD_2001 });

    //TODO remove <include> if not used in Woden
    //<xs:include> qnames
    public static final QName Q_ELEM_INCLUDE_XSD_1999 = new QName(
        NS_URI_XSD_1999, ELEM_SCHEMA_INCLUDE);
    public static final QName Q_ELEM_INCLUDE_XSD_2000 = new QName(
        NS_URI_XSD_2000, ELEM_SCHEMA_INCLUDE);
    public static final QName Q_ELEM_INCLUDE_XSD_2001 = new QName(
        NS_URI_XSD_2001, ELEM_SCHEMA_INCLUDE);
    public static final List XSD_INCLUDE_QNAME_LIST = Arrays.asList(new QName[]
        { Q_ELEM_INCLUDE_XSD_1999, Q_ELEM_INCLUDE_XSD_2000, Q_ELEM_INCLUDE_XSD_2001 });

    //TODO remove <redefine> if not used in Woden
    //<xs:redefine> qnames
    public static final QName Q_ELEM_REDEFINE_XSD_1999 = new QName(
        NS_URI_XSD_1999, ELEM_SCHEMA_REDEFINE);
    public static final QName Q_ELEM_REDEFINE_XSD_2000 = new QName(
        NS_URI_XSD_2000, ELEM_SCHEMA_REDEFINE);
    public static final QName Q_ELEM_REDEFINE_XSD_2001 = new QName(
	    NS_URI_XSD_2001, ELEM_SCHEMA_REDEFINE);
    public static final List XSD_REDEFINE_QNAME_LIST = Arrays.asList(new QName[]
	    { Q_ELEM_REDEFINE_XSD_1999, Q_ELEM_REDEFINE_XSD_2000, Q_ELEM_REDEFINE_XSD_2001 });


    //Built-in XML Schema types. 19 primitive and 25 derived.
    public static final List LIST_Q_BUILT_IN_TYPES = Arrays.asList(new QName[]
        { new QName(NS_URI_XSD_2001, "string"),
          new QName(NS_URI_XSD_2001, "boolean"),
          new QName(NS_URI_XSD_2001, "decimal"),
          new QName(NS_URI_XSD_2001, "float"),
          new QName(NS_URI_XSD_2001, "double"),
          new QName(NS_URI_XSD_2001, "duration"),
          new QName(NS_URI_XSD_2001, "dateTime"),
          new QName(NS_URI_XSD_2001, "time"),
          new QName(NS_URI_XSD_2001, "date"),
          new QName(NS_URI_XSD_2001, "gYearMonth"),
          new QName(NS_URI_XSD_2001, "gYear"),
          new QName(NS_URI_XSD_2001, "gMonthDay"),
          new QName(NS_URI_XSD_2001, "gDay"),
          new QName(NS_URI_XSD_2001, "gMonth"),
          new QName(NS_URI_XSD_2001, "hexBinary"),
          new QName(NS_URI_XSD_2001, "base64Binary"),
          new QName(NS_URI_XSD_2001, "anyURI"),
          new QName(NS_URI_XSD_2001, "QName"),
          new QName(NS_URI_XSD_2001, "NOTATION"),
          new QName(NS_URI_XSD_2001, "normalizedString"),
          new QName(NS_URI_XSD_2001, "token"),
          new QName(NS_URI_XSD_2001, "language"),
          new QName(NS_URI_XSD_2001, "NMTOKEN"),
          new QName(NS_URI_XSD_2001, "NMTOKENS"),
          new QName(NS_URI_XSD_2001, "Name"),
          new QName(NS_URI_XSD_2001, "NCName"),
          new QName(NS_URI_XSD_2001, "ID"),
          new QName(NS_URI_XSD_2001, "IDREF"),
          new QName(NS_URI_XSD_2001, "IDREFS"),
          new QName(NS_URI_XSD_2001, "ENTITY"),
          new QName(NS_URI_XSD_2001, "ENTITIES"),
          new QName(NS_URI_XSD_2001, "integer"),
          new QName(NS_URI_XSD_2001, "nonPositiveInteger"),
          new QName(NS_URI_XSD_2001, "negativeInteger"),
          new QName(NS_URI_XSD_2001, "long"),
          new QName(NS_URI_XSD_2001, "int"),
          new QName(NS_URI_XSD_2001, "short"),
          new QName(NS_URI_XSD_2001, "byte"),
          new QName(NS_URI_XSD_2001, "nonNegativeInteger"),
          new QName(NS_URI_XSD_2001, "unsignedLong"),
          new QName(NS_URI_XSD_2001, "unsignedInt"),
          new QName(NS_URI_XSD_2001, "unsignedShort"),
          new QName(NS_URI_XSD_2001, "unsignedByte"),
          new QName(NS_URI_XSD_2001, "positiveInteger") });
}
