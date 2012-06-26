package org.apache.woden.wsdl20.editable;

import org.apache.woden.wsdl20.BindingFaultReference;
import org.apache.woden.wsdl20.InterfaceFaultReference;

/**
 * Represents the editable BindingFaultReference component from the WSDL 2.0
 * Component model.
 * 
 * 
 */
public interface EdBindingFaultReference extends BindingFaultReference {

    public void setInterfaceFaultReference(InterfaceFaultReference interfaceFaultReference);

}
