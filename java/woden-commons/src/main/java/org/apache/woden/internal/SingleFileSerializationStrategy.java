package org.apache.woden.internal;

import java.io.Writer;

import org.apache.woden.SerializationStrategy;
import org.apache.woden.WSDLException;
import org.apache.woden.WSDLFactory;
import org.apache.woden.WSDLWriter;
import org.apache.woden.wsdl20.Description;

/**
 * This class implement the
 * SearalizationStrategy.SingleFileSearalizationStrategy Also this is default
 * implementation when there is no SearalizationStrategy defined. *
 * 
 */
public class SingleFileSerializationStrategy implements SerializationStrategy {

	private WSDLContext fWsdlContext;
	private WSDLFactory factory;

	public SingleFileSerializationStrategy() {

		// TODO - decide what to do with WSDLContext.
		this.fWsdlContext = new WSDLContext(null, null, null, null);
	}

	public SingleFileSerializationStrategy(WSDLContext wsdlContext) {

		this.fWsdlContext = wsdlContext;

	}

	public void serializeInternal(Description description, Writer sink) {

		// set WSDLFactory
		if (factory == null) {
			if (fWsdlContext.wsdlFactory == null) {

				try {

					factory = WSDLFactory.newInstance();

				} catch (WSDLException e) {

					e.printStackTrace();
				}

			} else {

				factory = fWsdlContext.wsdlFactory;

			}
		}

		try {

			WSDLWriter writer = factory.newWSDLWriter();
			writer.writeWSDL(description.toElement(), sink);

		} catch (WSDLException e) {

			e.printStackTrace();
		}

	}

}
