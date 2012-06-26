package org.apache.woden.wsdl20.editable;

import javax.xml.namespace.QName;

import org.apache.woden.wsdl20.InterfaceFault;

/**
 * Represents the editable InterfaceFault component from the WSDL 2.0 Component
 * model.
 * 
 */
public interface EdInterfaceFault extends InterfaceFault {

    public void setName(QName name);

}
