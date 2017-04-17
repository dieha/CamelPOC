package com.fusesource.demo.router;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class MockGatewayProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
//		System.out.println("End:"+exchange);
		exchange.getOut().setBody("OK");
	}

}
