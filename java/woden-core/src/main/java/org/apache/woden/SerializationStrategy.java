package org.apache.woden;

import java.io.Writer;

import org.apache.woden.wsdl20.Description;

/**
 * This interface define the possible serialization
 * Strategies and their names.  
*/

public interface SerializationStrategy {
    
	/**
	 * Define the SingleFileSearalizationStrategy document content 
	 * serialize into a single file. 
	 */
	public final String SingleFileSerializationStrategy = "SingleFileSearalizationStrategy";
    
	/**
	 * A SearalizationStrategy implementation must provide this method.
	 * @param description
	 * @param sink
	 */
	public void serializeInternal(Description description, Writer sink);

}
