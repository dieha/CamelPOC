package com.fusesource.demo.router;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class tcpRouter {


	static {


	}

	public static void main(String[] args) throws Exception {

		CamelContext context = getContext();

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() {
				

				
				tcpOutProcessor outProcessor = new tcpOutProcessor();

				 String varxslt="";
				
//				from("jetty:http://127.0.0.1:8088/MDMWSProvider/MDMService").process(outProcessor).to("xslt:http://acme.com/cheese/"+varxslt).removeHeaders("CamelHttp*").process(outProcessor).end();
				
				
//				from("netty4:tcp://0.0.0.0:9000?textline=true&sync=true").process(outProcessor).end();
				
			
			}
		});

		context.start();

		while (true) {
		}
	}

	public static CamelContext getContext() throws Exception {
		CamelContext camelContext = new DefaultCamelContext();


		return camelContext;
	}

}
