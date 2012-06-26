package org.apache.woden.wsdl20.editable;

import org.apache.woden.wsdl20.BindingMessageReference;
import org.apache.woden.wsdl20.InterfaceMessageReference;

/**
 * Represents the editable BindingMessageReference component from the WSDL 2.0
 * Component model
 * 
 * 
 */
public interface EdBindingMessageReference extends BindingMessageReference {

    public void setInterfaceMessageReference(InterfaceMessageReference interfaceMessageReference);

}
