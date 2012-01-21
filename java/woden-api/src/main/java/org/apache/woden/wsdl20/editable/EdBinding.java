package org.apache.woden.wsdl20.editable;

import java.net.URI;

import javax.xml.namespace.QName;

import org.apache.woden.wsdl20.Binding;
import org.apache.woden.wsdl20.Interface;

/**
 * Represents the editable Binding component from the WSDL 2.0 Component model
 * 
 */
public interface EdBinding extends Binding {
    public EdBindingFault addBindingFault();

    public EdBindingOperation addBindingOperation();

    public void setInterface(Interface interfaceComp);

    public void setName(QName name);

    public void setType(URI uri);

}