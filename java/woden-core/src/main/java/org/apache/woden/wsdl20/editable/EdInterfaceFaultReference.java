package org.apache.woden.wsdl20.editable;

import org.apache.woden.types.NCName;
import org.apache.woden.wsdl20.InterfaceFault;
import org.apache.woden.wsdl20.InterfaceFaultReference;
import org.apache.woden.wsdl20.enumeration.Direction;

/**
 * Represents the editable InterfaceFaultReference component from the WSDL 2.0
 * Component model.
 * 
 * 
 */
public interface EdInterfaceFaultReference extends InterfaceFaultReference {

    public void setDirection(Direction dir);

    public void setMessageLabel(NCName msgLabel);

    public void setInterfaceFault(InterfaceFault interfaceFault);

}
