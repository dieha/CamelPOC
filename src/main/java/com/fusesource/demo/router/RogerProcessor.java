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

public class RogerProcessor implements Processor {
//	static Logger log = Logger.getLogger(RogerProcessor.class);
	
	static SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
			
	@Override
	public void process(Exchange exchange) throws Exception {
		
//		/		N1OZ00000000
		String tdata = exchange.getIn().getBody().toString();
		
	
		Boolean isQuery = (tdata.substring(4, 12).equals("00000000"))? true : false;
		
//		System.out.println(isQuery);
		if(isQuery){
			exchange.getOut().setHeader("goahead", "no");
			exchange.setProperty("goahead", "no");
			String keepNo= this.readNo(tdata.substring(0, 4));
			exchange.setProperty("keepNo", keepNo);
			exchange.getOut().setBody(keepNo);
			
		}else{// not a query
			
		
			exchange.setProperty("no", tdata.substring(0, 12));
			exchange.getOut().setHeader("goahead", "yes");

			exchange.getOut().setBody(tdata);
//			log.info(tdata);
		}
//		System.out.println(tdata);
//		exchange.getOut().setBody("         10715100217700001796376302497714304  001001240B021010503040300000105030413451167 N90620010019 0217700001796376502497714306  001001365B020010503040300000105030413451174 N90630010019 0217700001796376802497714533  001001395B021010503040300000105030413451179 N90640010019 0217700001796377302497714714  001000942B021010503040300000105030413451186 N90650010019 0217700001796377502497714908  001002125B021010503040300000105030413451190 N90660010019 0217700001796385102497714979  001008520B021010503040300000105030413451195 N90670010019 0217700001796385202497714999  001005200B020010503040300000105030413451200 N90680010019 0217700001796385302497715203  001008090B020010503040300000105030413451204 N90690010019 0217700001796388402497715398  001002130B021010503040300000105030413451210 N90700010019 0217700001796388502497715460  001001285B021010503040300000105030413451214 N90710010019 ");

	}
	
	private String readNo(String queryType){
		try {
//			log.info("readNo:"+queryType);
			List<String> dan = Arrays.asList("NAOX","NBOX","N1OX","N1OZ");
			boolean contains = dan.contains(queryType);
			if(contains){
				File file = new File("/tmp/"+queryType+".dat");
				
				
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line);
				}
				fileReader.close();
				
//				log.info("/root/"+queryType+".dat"+stringBuffer.toString());
				return stringBuffer.toString();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
