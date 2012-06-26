package org.apache.woden.wsdl20.editable;

import java.net.URI;

import javax.xml.namespace.QName;

import org.apache.woden.wsdl20.TypeDefinition;

/**
 * Represents the editableTypeDefinition component described in the WSDL 2.0
 * component model
 */
public interface EdTypeDefinition extends TypeDefinition {

    public void setContent(Object content);

    public void setContentModel(String contentModel);

    public void setName(QName name);

    public void setSystem(URI systemURI);

}
