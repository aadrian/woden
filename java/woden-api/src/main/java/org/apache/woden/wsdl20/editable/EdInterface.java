package org.apache.woden.wsdl20.editable;

import org.apache.woden.types.NCName;
import org.apache.woden.wsdl20.Interface;

/**
 * Represents the editable Interface component from the WSDL 2.0 Component
 * model.
 * 
 * 
 */
public interface EdInterface extends Interface {

    public void addExtendedInterface(Interface interfaceComp);

    public EdInterfaceFault addInterfaceFault();

    public EdInterfaceOperation addInterfaceOperation();

    public void setName(NCName name);

}
