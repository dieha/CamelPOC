package com.fusesource.demo.router;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;


public class VarProcessor implements Processor {

	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Message inMsg=exchange.getIn();
		String s=inMsg.getBody().toString();

//    	String s = "026123456789300-------9819236---0000000000000000--------------------------------1201172.22.8.175---220000000000130012016021800";
    
			System.out.println(s);
		
    	String ApID = s.substring(0,3);
    	String ReqNo = s.substring(3,11);
    	String BrokerID = s.substring(11,22);
    	String AcNo = s.substring(22,32);	    	
    	String AgentID = s.substring(32,40);
    	String EmpID = s.substring(40,48);
    	String Password = s.substring(48,80);
    	String TxCode = s.substring(80,84);
    	String ClientIP = s.substring(84,99);
    	String GatewayID = s.substring(99,101);
    	String RespCode = s.substring(101,107);
    	String BodyLen = s.substring(107,113);
    	
    	Integer bodylen= Integer.parseInt(BodyLen);
    	String body = s.substring(113,113+bodylen);
    	
    	String Ver = body.substring(0,3);
    	String QueryDate = body.substring(3,11);
    	String QueryType = body.substring(11,12);
    	String FewSK = body.substring(12,13);
    	    	
//    	exchange.getOut().setHeader("ApID",ApID);//應用系統代碼
//    	exchange.getOut().setHeader("ReqNo",ReqNo);//Request Number
//    	exchange.getOut().setHeader("BrokerID",BrokerID);//分公司代碼
//    	exchange.getOut().setHeader("AcNo",AcNo);//客戶帳號
//    	exchange.getOut().setHeader("AgentID",AgentID);//營業員代碼
//    	exchange.getOut().setHeader("EmpID",EmpID);//員工編號
//    	exchange.getOut().setHeader("Password",Password);//客戶密碼或風控密碼
//    	exchange.getOut().setHeader("TxCode",TxCode);//交易別
//    	exchange.getOut().setHeader("ClientIP",ClientIP);//Client IP Address
//    	exchange.getOut().setHeader("GatewayID",GatewayID);//Gateway 序號
//    	exchange.getOut().setHeader("RespCode",RespCode);//回應結果代碼
//    	exchange.getOut().setHeader("BodyLen",BodyLen);//本文長度
//    	exchange.getOut().setHeader("Ver",Ver);//version
//    	exchange.getOut().setHeader("QueryDate",QueryDate);//查詢交易日
//    	exchange.getOut().setHeader("QueryType",QueryType);//查詢類別
//    	exchange.getOut().setHeader("FewSK",FewSK);//盤別
    	
//    	ApID, ReqNo, BrokerID, AcNo, AgentID, EmpID, Password, TxCode, ClientIP, GatewayID, RespCode, BodyLen
    	exchange.setProperty("ApID", ApID);
    	exchange.setProperty("ReqNo", ReqNo);
    	exchange.setProperty("BrokerID", BrokerID);
    	exchange.setProperty("AcNo", AcNo);
    	exchange.setProperty("AgentID", AgentID);
    	exchange.setProperty("EmpID", EmpID);
    	exchange.setProperty("Password", Password);
    	exchange.setProperty("TxCode", TxCode);
    	exchange.setProperty("ClientIP", ClientIP);
    	exchange.setProperty("GatewayID", GatewayID);
    	exchange.setProperty("RespCode", RespCode);
    	exchange.setProperty("BodyLen", BodyLen);
    	exchange.setProperty("Ver", Ver);
    	exchange.setProperty("QueryDate", QueryDate);
    	exchange.setProperty("FewSK", FewSK);
    	
    	
    	
    	
    	

    	
    	
    	
//    	select wor_broker_id, wor_system_id, wor_machine_no, wor_tx_seq, wor_buysell,wor_order_no, wor_company_no,wor_exchange_code, wor_stock_nos, wor_price, wor_price_type, wor_order_type, wor_ori_stock_nos, wor_create_date, wor_create_time, wor_process_mark, wor_day_trade_mark from itword 
//    	where wor_order_date = :QueryDate 
//    	and wor_broker_id = :BrokerID 
//    	and wor_acno = :AcNo 
//    	and wor_exchange_code = :FewSK 
//    	and wor_process_mark in ('N','D')
    	
//    	System.out.println("1234567890123456789012345678901234567890");
//    	[ApID=026][ReqNo=12345678][BrokerID=9300       ][AcNo=9819236   ][AgentID=00000000][EmpID=00000000][Password=                                ]
//    	[TxCode=1201][ClientIP=172.22.8.175   ][GatewayID=22][RespCode=000000][BodyLen=000013]
//    	[Ver=001][QueryDate=20160218][QueryType=0][FewSK=0]
    	
//		System.out.println("inMsg:"+inMsg.getBody());
//		String name = s.substring(4, 9) ;
//		exchange.getOut().setHeader("name",name);
//		System.out.println("VarProcessor:"+name);

	}

}


//[026123456789300       9819236   0000000000000000                                1201172.22.8.175   220000000000130012016021800]

//	    >[ApID=026][ReqNo=12345678][BrokerID=9300       ][AcNo=9819236   ][AgentID=00000000][EmpID=00000000][Password=                                ][TxCode=1201][ClientIP=172.22.8.175   ][GatewayID=22][RespCode=000000][BodyLen=000013]
//	    >[Ver=001][QueryDate=20160218][QueryType=0][FewSK=0]

