package org.apache.woden.wsdl20.editable;

import org.apache.woden.types.NCName;
import org.apache.woden.wsdl20.InterfaceMessageReference;
import org.apache.woden.wsdl20.enumeration.Direction;

/**
 * Represents the editable InterfaceMessageReference component from the WSDL 2.0
 * Component model.
 * 
 * 
 */
public interface EdInterfaceMessageReference extends InterfaceMessageReference {

    public void setDirection(Direction dir);

    public void setMessageLabel(NCName msgLabel);

}
