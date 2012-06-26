package org.apache.woden.wsdl20.editable;

import java.net.URI;

import javax.xml.namespace.QName;

import org.apache.woden.wsdl20.ElementDeclaration;

/**
 * Represents the editable ElementDeclaration component described in the WSDL
 * 2.0 component model
 * 
 */
public interface EdElementDeclaration extends ElementDeclaration {

    public void setContent(Object content);

    public void setName(QName name);

    public void setSystem(URI SyatemURI);

    public String setContentModel(String contentModel);

}
