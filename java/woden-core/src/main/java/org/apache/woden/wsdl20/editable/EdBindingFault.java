package org.apache.woden.wsdl20.editable;

import org.apache.woden.wsdl20.BindingFault;
import org.apache.woden.wsdl20.InterfaceFault;

/**
 * Represents the editable BindingFault component from the WSDL 2.0 Component
 * model.
 * 
 * 
 */
public interface EdBindingFault extends BindingFault {
    public void setInterfaceFault(InterfaceFault interfaceFault);

}