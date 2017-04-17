package com.fusesource.demo.router;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class BodyInAggregatingStrateg implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		Object newBody = newExchange.getIn().getBody();
        HashMap<String,Object> map = null;
        if (oldExchange == null) {
            map = new HashMap<String,Object>();
            map.put("first",newBody);
            newExchange.getIn().setBody(map);
//            System.out.println("newExchange");
            return newExchange;
        } else {
        	map = oldExchange.getIn().getBody(HashMap.class);
        	map.put("second",newBody);
//            System.out.println("oldExchange");
            return oldExchange;
        }
		

		
	}

}
