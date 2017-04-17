package com.fusesource.demo.router;

import mTake.TibcomTalkAPI;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class TcpProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		
		
		String brokerID= (String)exchange.getProperty("BrokerID");
		String queryDate= (String)exchange.getProperty("QueryDate");
		String acNo= (String)exchange.getProperty("AcNo");
		
		
//    	10193000218000000000000000009819236 0000000000000000000000000	
//    	101
//    	93000 分公司    * BrokerID
//    	2180  WS-MMDD(7,11)   QueryDate
//    	0000000000000000   WS-TX-SEQ(11,28)
//    	9819236 客戶帳號 *  AcNo
//    	0000000000000000000000000	 WS-TX-SEQ-N(40,57)
//		System.out.println("10193000218000000000000000009819236 0000000000000000000000000");
		String body = "101"+brokerID.trim()+queryDate.substring(3,7)+"00000000000000000"+acNo.trim()+" 0000000000000000000000000";
		exchange.getOut().setBody(body);

//		 TibcomTalkAPI mt = new TibcomTalkAPI();
//		 String received = mt.wrtibco("172.22.168.170", 7777, 5, "K204_TIPW_ITS60", body);
//		 exchange.getOut().setBody(received);
		
		
//		
		

	}

}
