package com.fusesource.demo.router;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class EndProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		
		Message inMsg=exchange.getIn();
		
		
	
		exchange.getOut().setBody(""+inMsg.getBody());
		
		HashMap<String,Object> map = exchange.getIn().getBody(HashMap.class);
		ArrayList list = null;
		String mtalk = "";
		if (map.get("second") instanceof ArrayList) {
			 list= (ArrayList)map.get("second");
			
        }
		if (map.get("first") instanceof ArrayList) {
			 list= (ArrayList)map.get("first");
       }
		if (map.get("second") instanceof String) {
			 mtalk = (String) map.get("second");
       }
		if (map.get("first") instanceof String) {
			
			 mtalk = (String) map.get("first");
       }
		
		
		HashMap<String,Object> dbHash = null;
		

		  
		  
		
		
//		System.out.println("----------first");
//		System.out.println(map.get("first"));
//		System.out.println("----------second");
//		System.out.println(map.get("second"));

		
		
		
		
//		[ApID=026][ReqNo=12345678][BrokerID=9300       ][AcNo=9819236   ]
//		[AgentID=00000000][EmpID=00000000][Password=                                ]
//		[TxCode=1201][ClientIP=172.22.8.175   ][GatewayID=22][RespCode=000000][BodyLen=000692]
//			    >[RecCount=0008]
		String apID = (String)exchange.getProperty("ApID", "000");
		String reqNo = (String)exchange.getProperty("ReqNo", "00000000");
		String brokerID = (String)exchange.getProperty("BrokerID", "0000       ");
		String acNo = (String)exchange.getProperty("AcNo", "0000000   ");
		String agentID = (String)exchange.getProperty("AgentID", "00000000");
		String empID = (String)exchange.getProperty("EmpID", "00000000");
		String password = (String)exchange.getProperty("Password", "00000000");
		String txCode = (String)exchange.getProperty("TxCode", "00000000");
		String clientIP = (String)exchange.getProperty("ClientIP", "000.00.0.000   ");
		String gatewayID = (String)exchange.getProperty("GatewayID", "22");
		String respCode = (String)exchange.getProperty("RespCode", "000000");
		String queryDate = (String)exchange.getProperty("QueryDate", "000000");
		String bodyLen = (String)exchange.getProperty("BodyLen", "000000");
		
		Integer count = Integer.parseInt(mtalk.substring(14,16));
		if(list!=null){
			count = list.size()+count;
		}
		String recCount = String.format("%04d", count);
		
		String resBody =apID+reqNo+brokerID+acNo+agentID+empID+password+txCode+clientIP+gatewayID+respCode+bodyLen+recCount;
		 for(Object obj : list) {
			 
			 //>[TxDate=20160218][ReqOrderDate=20160218][ReqOrderTime=084944][SystemId=022][MachineNo=22]
			 //[ReqOrderSrc=000001087474][OrderStatus=2][ReqNo=N0001     ][StockNo=2880  ]
			 //[FewSK=0][BuySell=S][OrderType=0][PriceFlag=9][OrderPrice=0001640][ReqOrderAmt=0001]
			 //[MatchAmt=0001][CancelAmt=0000][ModifyStatus=2][R030Status=     ][Split_field=|]
			  dbHash = (HashMap) obj;
			  
			  resBody+=queryDate;	//<TxDate>		$Dispatch-Format/Rows/root[1]/QueryDate"/>
			  resBody+=dbHash.get("wor_create_date");	//<ReqOrderDate>		wor_create_date
			  
			  resBody+=(""+dbHash.get("wor_create_time")).substring(0,6);	//<ReqOrderTime>		floor(wor_create_time div 100) , 6, '0')
			
			
			  resBody+=dbHash.get("wor_system_id");		//<SystemId>		wor_system_id"/>
			  resBody+=dbHash.get("wor_machine_no");	//<MachineNo>		<xsl:value-of select="wor_machine_no"/>
			  resBody+=String.format("%012d", Integer.parseInt(""+dbHash.get("wor_tx_seq"))); 		//<ReqOrderSrc>		tib:pad-front(wor_tx_seq, '12', '0')"/>
			  resBody+="1";								//		<OrderStatus>		
			  resBody+="N0001     ";								//<ReqNo>			""
			  resBody+=dbHash.get("wor_company_no");
			  
			  resBody+=dbHash.get("wor_exchange_code");	//<FewSK>			wor_exchange_code
			  resBody+=dbHash.get("wor_buysell"); // <BuySell>		if ($Dispatch-Format/Rows/root/Version="001" and column[name='wor_day_trade_mark']/value="X") then column[name='wor_day_trade_mark']/value else    wor_buysell"/>
			  
			  resBody+=dbHash.get("wor_order_type"); //		<OrderType>		wor_order_type
			  resBody+=dbHash.get("wor_price_type");//		<PriceFlag>		if (wor_price_type = 2 ) then  0 else  wor_price_type"
			  
			  resBody+=String.format("%07d", (Integer.parseInt(""+((BigDecimal)dbHash.get("wor_price")).intValue() * 100)));   //		<OrderPrice>		round(wor_price  * 100),"7","0")
			  resBody+=String.format("%04d", Integer.parseInt(""+dbHash.get("wor_ori_stock_nos"))); //<ReqOrderAmt>		tib:pad-front(wor_ori_stock_nos, '4', '0')
			  resBody+="0000"; //		<MatchAmt>		0000
			  resBody+="0000"; //		<CancelAmt>		if (wor_process_mark = "D" or wor_process_mark = "E") then  tib:pad-front(wor_ori_stock_nos, 4, "0") else   tib:pad-front(wor_ori_stock_nos - wor_stock_nos, 4, '0')"
			  resBody+="2";				//<ModifyStatus>		if (wor_process_mark = "N" ) then    "1"else    "2""/>
			  resBody+="     "; //<R030Status>		“”
			  resBody+="|";  //<Split_field>		"|"
		  }
		 
//		 String mtalk = "         10715100217700001796376302497714304  001001240B021010503040300000105030413451167 N90620010019 0217700001796376502497714306  001001365B020010503040300000105030413451174 N90630010019 0217700001796376802497714533  001001395B021010503040300000105030413451179 N90640010019 0217700001796377302497714714  001000942B021010503040300000105030413451186 N90650010019 0217700001796377502497714908  001002125B021010503040300000105030413451190 N90660010019 0217700001796385102497714979  001008520B021010503040300000105030413451195 N90670010019 0217700001796385202497714999  001005200B020010503040300000105030413451200 N90680010019 0217700001796385302497715203  001008090B020010503040300000105030413451204 N90690010019 0217700001796388402497715398  001002130B021010503040300000105030413451210 N90700010019 0217700001796388502497715460  001001285B021010503040300000105030413451214 N90710010019 ";
			String errorCode = mtalk.substring(0,10).trim();
			String returnCode = mtalk.substring(10,14);
			String rows = mtalk.substring(16);
			
//			System.out.println(errorCode);
//			System.out.println(returnCode);
//			System.out.println(count);
//			System.out.println(rows);
			if(rows !=null && rows.length() > 87){
				
				String[] pairs=rows.split("(?<=\\G.{87})");
			
				for(int i=0;i <1;i++){
					
					if(pairs[i].length()>86){
					String TX_SEQ= pairs[i].substring(0,17);
//					String ACNO= pairs[i].substring(17,24);
					String COMPANY_NO= pairs[i].substring(24,30);
					String STOCK_NOS= pairs[i].substring(30,33);
					String PRICE= pairs[i].substring(33,39);
					String BUYSELL= pairs[i].substring(39,40);
					String ORDER_TYPE= pairs[i].substring(40,41);
					String EXCHANGE_CODE= pairs[i].substring(41,42);
//					String MARKET_TYPE= pairs[i].substring(42,43);
//					String ORDER_DATE= pairs[i].substring(43,51);
//					String STATUS_CODE= pairs[i].substring(51,53);
//					String ERROR_CODE= pairs[i].substring(53,57);
//					String CREATE_DATE= pairs[i].substring(57,65);
					String CREATE_TIME= pairs[i].substring(65,73);
//					String CANCEL= pairs[i].substring(73,74);
//					String ORDER_NO= pairs[i].substring(74,79);
					String AFTER_NOS= pairs[i].substring(79,82);
					String OK_STOCK_NOS= pairs[i].substring(82,85);
					String PRICE_KIND= pairs[i].substring(85,86);
//					String DAY_TRADE_MAR= pairs[i].substring(86,87);
					//2016021820160311134926026220000010889011N0001     00633L0S0200027000010000000002     |
					
					  resBody+=queryDate;	
					  resBody+="00000000";  //*if (number(concat(0,WR-CREATE-DATE)) = 0) then&#xA;    &quot;00000000&quot;&#xA;else&#xA;    WR-CREATE-DATE + 19110000"/>
					  resBody+=(""+CREATE_TIME).substring(0,6);	//<xsl:value-of select="WR-CREATE-TIME"/>
					  resBody+=TX_SEQ.substring(0,3);//substring(WR-TX-SEQ, 1, 3)"
					  resBody+=TX_SEQ.substring(3,5);	//substring(WR-TX-SEQ, 4, 5)"/>
					 
					 
					  resBody+=String.format("%012d", Integer.parseInt(""+TX_SEQ.substring(6,17))); 		//<ReqOrderSrc substring(WR-TX-SEQ, 6, 17)"/>

					  resBody+="2";//<OrderStatus> <xsl:value-of select="&quot;2&quot;"/>		
					  resBody+="N0001     ";//*  <ReqNo>"if (tib:trim(WR-ORDER-NO)=&quot;&quot;) then&#xA;    &quot;&quot;&#xA;else&#xA;    WR-ORDER-NO"/>
					  resBody+=COMPANY_NO;  //<StockNo> <xsl:value-of select="WR-COMPANY-NO"/>
					  
					  resBody+=EXCHANGE_CODE;//<FewSK><xsl:value-of select="WR-EXCHANGE-CODE"/>
					  resBody+=BUYSELL; //<BuySell><xsl:value-of select="if ($Start/root/Version=&quot;001&quot; and WR-DAY-TRADE-MAR=&quot;X&quot;) then&#xA;    WR-DAY-TRADE-MAR&#xA;else&#xA;    WR-BUYSELL"/>
					  
					  resBody+=ORDER_TYPE; //	   <OrderType><xsl:value-of select="WR-ORDER-TYPE"/>
					  resBody+=PRICE_KIND;//*	<PriceFlag><xsl:value-of select="if (WR-PRICE-KIND = &quot; &quot;) then&#xA;&#x9;&quot;0&quot;&#xA;else if (WR-PRICE-KIND = &quot;2&quot;) then&#xA;&#x9;&quot;0&quot;&#xA;else if (WR-PRICE-KIND = &quot;_&quot;) then&#xA;&#x9;&quot;0&quot;&#xA;else&#xA;&#x9;WR-PRICE-KIND"/>
					  
					  resBody+=String.format("%07d", (Integer.parseInt(PRICE.trim())));   //<OrderPrice>   <xsl:value-of select="tib:pad-front(tib:trim(WR-PRICE), '7','0')"/>
					  
					  resBody+=String.format("%04d", Integer.parseInt(""+STOCK_NOS)); //<ReqOrderAmt>   <xsl:value-of select="tib:pad-front(number(concat(0,WR-STOCK-NOS)), '4', '0')"/>
					  resBody+=String.format("%04d", Integer.parseInt(""+OK_STOCK_NOS)); ; //	<MatchAmt> <xsl:value-of select="tib:pad-front(number(concat(0,WR-OK-STOCK-NOS)), '4', '0')"/>
					  resBody+=String.format("%04d",Integer.parseInt(STOCK_NOS) - Integer.parseInt(AFTER_NOS)); //CancelAmt> <xsl:value-of select="tib:pad-front(number(concat(0,WR-STOCK-NOS)) - number(concat(0,WR-AFTER-NOS)), '4', '0')"/>
					  
					  resBody+="1";// <ModifyStatus> <xsl:value-of select="if ((WR-CANCEL = &quot;D&quot; ) &#xA;or (WR-CANCEL  !=&quot;D&quot; and (WR-AFTER-NOS -  WR-OK-STOCK-NOS) = 0 )&#xA;or (WR-ERROR-CODE > 0)) then&#xA;    &quot;2&quot;&#xA;else &#xA;    &quot;1&quot;"/>
					  resBody+="     "; //<R030Status>		“”
					  resBody+="|";  //<Split_field>		"|"
					}
					
				}
			}

//		
//		[{wor_broker_id=9300,   	BrokerID
//		wor_system_id=026, 	ApID
//		wor_machine_no=22, 
//		wor_tx_seq=1088901, 
//		wor_buysell=S, 	BuySell
//		wor_order_no=Q0117, 	ReqNo
//		wor_company_no=00633L, 
//		wor_exchange_code=0, 
//		wor_stock_nos=10, 
//		wor_price=27.41, 	OrderPrice
//		wor_price_type=2, 	
//		wor_order_type=0, 	OrderType
//		wor_ori_stock_nos=10, 
//		wor_create_date=20160311, 
		exchange.getOut().setBody(resBody);
		
//		System.out.println("026123456789300       9819236   0000000000000000                                1201172.22.8.175   2200000000069200082016021820160218084944022220000010874742N0001     2880  0S0900016400001000100002     |");
		
		
		
		
		
		

	}

}

