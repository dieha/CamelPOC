package com.fusesource.demo.router;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class RogerEndProcessor implements Processor {
	
	
	@Override
	public void process(Exchange exchange) throws Exception {


		

		
		String goahead = (String) exchange.getIn().getHeader("goahead");
	

		
		if(goahead.equals("yes")){
			String body = exchange.getIn().getBody().toString();
			String[] arr= body.split(",");
			this.keepNo(arr[arr.length-1].substring(0,12));
			exchange.getOut().setBody("OK");
			
		}else{
			String keepNo = (String) exchange.getProperty("keepNo");
			exchange.getOut().setBody(keepNo);
		}
		

	}
	
	
	private void keepNo(String no){
		String queryType =no.substring(0,4);
		List<String> dan = Arrays.asList("NAOX","NBOX","N1OX","N1OZ");
		boolean contains = dan.contains(queryType);
		if(no !=null && contains){
			PrintWriter writer = null;
			try {
				writer = new PrintWriter("/tmp/"+queryType+".dat");
				writer.println(no);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				writer.close();
			}
		}
		
		
		
		
		
	
	}

}
