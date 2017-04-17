package com.fusesource.demo.router;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class DbProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
//		String name= (String)exchange.getIn().getHeader("name");
		
//		String name= "Catch";
		
//		System.out.println("DB");
		
		
		
		String queryDate= (String)exchange.getProperty("QueryDate");
		String brokerID= (String)exchange.getProperty("BrokerID");
		String acNo= (String)exchange.getProperty("AcNo");
		String fewSK= (String)exchange.getProperty("FewSK");
		
		
		
		
//		System.out.println("---");
//		System.out.println(queryDate);
//		System.out.println(brokerID);
//		System.out.println(acNo);
//		System.out.println(fewSK);
//		
//		System.out.println("---");
		
		exchange.getOut().setHeader("QueryDate", Integer.parseInt(queryDate.trim()));
		exchange.getOut().setHeader("brokerID", Integer.parseInt(brokerID.trim()));
		exchange.getOut().setHeader("AcNo", Integer.parseInt(acNo.trim()));
		exchange.getOut().setHeader("FewSK", Integer.parseInt(fewSK.trim()));
//		System.out.println("select wor_broker_id, wor_system_id, wor_machine_no, wor_tx_seq, wor_buysell,wor_order_no, wor_company_no,wor_exchange_code, wor_stock_nos, wor_price, wor_price_type, wor_order_type, wor_ori_stock_nos, wor_create_date, wor_create_time, wor_process_mark, wor_day_trade_mark from itword where wor_order_date = :?QueryDate and wor_broker_id = :?BrokerID and wor_acno = :?AcNo and wor_exchange_code = :?FewSK and wor_process_mark in ('N','D')");
//		exchange.getOut().setBody("SELECT * FROM people WHERE name=:?name ");
		
		exchange.getOut().setBody("select wor_broker_id, wor_system_id, wor_machine_no, wor_tx_seq, wor_buysell,wor_order_no, wor_company_no,wor_exchange_code, wor_stock_nos, wor_price, wor_price_type, wor_order_type, wor_ori_stock_nos, wor_create_date, wor_create_time, wor_process_mark, wor_day_trade_mark from itword where wor_order_date = :?QueryDate and wor_broker_id = :?BrokerID and wor_acno = :?AcNo and wor_exchange_code = :?FewSK and wor_process_mark in ('N','D')");
		
//		


	}

}
