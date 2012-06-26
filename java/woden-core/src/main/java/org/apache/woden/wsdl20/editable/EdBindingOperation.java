package org.apache.woden.wsdl20.editable;

import org.apache.woden.wsdl20.BindingOperation;
import org.apache.woden.wsdl20.InterfaceOperation;

/**
 * Represents the editable BindingOperation component from the WSDL 2.0
 * Component
 * 
 * 
 */
public interface EdBindingOperation extends BindingOperation {
    public EdBindingFaultReference addBindingFaultReference();

    public EdBindingMessageReference addBindingMessageReference();

    public void setInterfaceOperation(InterfaceOperation interfaceOperationComp);

}
