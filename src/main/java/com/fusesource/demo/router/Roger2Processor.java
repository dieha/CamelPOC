package com.fusesource.demo.router;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class Roger2Processor implements Processor {
	
	static Logger log = Logger.getLogger(Roger2Processor.class);
	
	static SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
			
	@Override
	public void process(Exchange exchange) throws Exception {
			
		
			exchange.getOut().setHeader("goahead", "yes");
		
			String tdata = (String)exchange.getIn().getBody();
			exchange.getOut().setHeader("lastNo", tdata.substring(0, 12));
			
			
			String HEADING = tdata.substring(0,4);
			String SEQNO = tdata.substring(4,12);
			String BROKER_NO = tdata.substring(12,15);
			String BRANCH_NO = tdata.substring(15,16);
			String ACNO = tdata.substring(16,23);
			String FILLER1 = tdata.substring(23,24);
			String ORDER_NOF = tdata.substring(24,25);
			String ORDER_NOL = tdata.substring(25,29);
			String COMPANY_NO = tdata.substring(29,35);
			String PRICE = tdata.substring(35,41);
			String FUNCTION_CODE = tdata.substring(41,43);
			String EXCHANGE_CODE = tdata.substring(43,44);
			String ORDER_TYPE = tdata.substring(44,45);
			String STOCK_NOS = tdata.substring(45,48);
			String BEFORE_NOS = tdata.substring(48,51);
			String AFTER_NOS = tdata.substring(51,54);
			String T030_STATUS = tdata.substring(54,56);
			String SELLER = tdata.substring(56,60);
			String TIME_OUT = tdata.substring(60,61);
			String PVC_ID = tdata.substring(61,63);
			String SYSTEM_ID = tdata.substring(63,66);
			String MACHINE_NO = tdata.substring(66,68);
			String TX_SEQ = tdata.substring(68,80);
			String ORDER_TIME = tdata.substring(80,86);
			String ACNO_FLAG = tdata.substring(86,87);
			String OTC_MARK = tdata.substring(87,88);
			String KEYIN_UNIT = tdata.substring(88,92);
			String OFFESET_MARK = tdata.substring(92,93);
			String DAY_TRADE_MARK = tdata.substring(93,94);
			 
			
			String resBody ="";
			String strDate = sdFormat.format(new Date());	

			resBody += String.format("%1$-15s",BROKER_NO + BRANCH_NO); //<BrokerID> concat(BROKER-NO,BRANCH-NO)"/>
			
			resBody += String.format("%1$-10s",ACNO);//<AcNo><xsl:value-of select="ACNO"/>
			
			resBody += String.format("%1$-10s",SELLER);//<AgentID> <xsl:value-of select="SELLER"/>
			resBody += strDate; //<TxDate><xsl:value-of select="tib:format-date(‘yyyyMMdd’,current-date())"/>
			resBody += strDate;//<ReqOrderDate><xsl:value-of select="tib:format-date(‘yyyyMMdd’,current-date())"/>		
			resBody += ORDER_TIME;//<ReqOrderTime><xsl:value-of select="ORDER-TIME"/>

			resBody +=String.format("%1$-20s","");
			resBody += "2";//<OrderStatus><xsl:value-of select="‘2’"/>
			resBody += String.format("%1$-10s",ORDER_NOF + ORDER_NOL); //<ReqNo><xsl:value-of select="concat(ORDER-NOF,ORDER-NOL)"/>
			resBody += String.format("%1$-10s",  " ");//PreReqNo(088,098)
			
			resBody += String.format("%1$-6s",  COMPANY_NO); //<StockNo><xsl:value-of select="COMPANY-NO"/>

			resBody +="0";//<FewSK><xsl:value-of select="if (ORDER-TIME>=$Start/root/pf_endtime_2) then ‘3’ else &#xA;if (EXCHANGE-CODE=‘0’ or EXCHANGE-CODE=‘1’) then ‘0’ else EXCHANGE-CODE"/>
			resBody += "B";//<BuySell><xsl:value-of select="if (current()/DAY-TRADE-MARK=‘X’) then current()/DAY-TRADE-MARK&#xA;else&#xA;if (FUNCTION-CODE=‘01’) then ‘B’&#xA;else&#xA;if (FUNCTION-CODE=‘02’) then ‘S’&#xA;else&#xA;’’"/>
			resBody += "9";//<OrderType><xsl:value-of select="if (OFFESET-MARK='9') then&#xA;   OFFESET-MARK&#xA;else&#xA;    ORDER-TYPE"/>
			resBody += "2";//<PriceFlag><xsl:value-of select="if (ORDER-TIME>=$Start/root/pf_endtime_2 or EXCHANGE-CODE=‘2’) then ‘‘ else  ‘2’"/>
			
			
			resBody += String.format("%07d", Integer.parseInt(""+PRICE.substring(0,6)));//<OrderPrice><xsl:value-of select="tib:pad-front(PRICE,7,’0’)"/>
			
			resBody += String.format("%04d", Integer.parseInt(""+STOCK_NOS.substring(0,3)));//<ReqOrderAmt><xsl:value-of select="tib:pad-front(STOCK-NOS,4,’0’)"/>
			
			resBody += "0000";//<MatchAmt><xsl:value-of select="‘0000’"/>
			resBody += "0000";//<CancelAmt><xsl:value-of select="‘0000’"/>
			resBody += " ";
			//<CancelStatus>	<xsl:value-of select=" "/>
			resBody +=  String.format("%05d", Integer.parseInt(""+T030_STATUS.substring(0,2))); //<T030Status>tib:pad-front(T030-STATUS,5,’0’)"/>
			resBody += FUNCTION_CODE.substring(FUNCTION_CODE.length()-1);//<FunctionKind>	<xsl:value-of select="tib:right(FUNCTION-CODE,1)"/>
			
			resBody +=  String.format("%04d", Integer.parseInt(""+BEFORE_NOS.substring(0,3)));//<BeforeQty><xsl:value-of select="tib:pad-front(BEFORE-NOS,4,’0’)"/>
			resBody += String.format("%04d", Integer.parseInt(""+AFTER_NOS.substring(0,3)));//<AfterQty><xsl:value-of select="tib:pad-front(AFTER-NOS,4,’0’)"/>
			resBody += SYSTEM_ID+MACHINE_NO+TX_SEQ;//<TxSeqno><xsl:value-of select="concat(SYSTEM-ID,MACHINE-NO,TX-SEQ)"/>
			
			
//			System.out.println("9300-----------1367676---0129------2016070720160707094426--------------------2N0001---------------2303--0B020001115000200000000-0000010000000200790000000042831");
			
			
			
			exchange.getOut().setBody(resBody);
//			
	}


}
