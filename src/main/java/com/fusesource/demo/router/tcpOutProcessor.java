package com.fusesource.demo.router;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.w3c.dom.NodeList;

/**
 * Processor class that takes a line of CSV as an input on the Exchange:
 * <ol>
 * <li>gets the first field,</li>
 * <li>parses it according to a country-specific date format</li>
 * <li>sets the CamelFileName header to the date the file should be written to</li>
 * <li>changes the first field to the universal one.</li>
 * </ol>
 */
public class tcpOutProcessor implements Processor {
	String version;
	
	static int re = 0;
	static Map<String, String> responseXML = new HashMap<String, String>();
	/**
	 * See http://xkcd.com/1179/
	 */
	public final static String UNIVERSAL_DATE_FORMAT = "yyyy-MM-dd";

	public String countryDateFormat;

	public void setCountryDateFormat(String countryDateFormat) {
		this.countryDateFormat = countryDateFormat;

	}

	public tcpOutProcessor() {
	}

	public tcpOutProcessor(String version) {
		this.version = version;
	}

	
	
	Random rnd = new Random();
	String xml=null;
	int a=0;
	

	@Override
	public void process(Exchange exchange) throws Exception {
//System.out.println("tcpOutProcessor out");
		Message in = exchange.getIn();
		Message out = exchange.getIn();
	
		out.setBody(in.getBody() + ":out");
		

	}

	public String getFileName(String inMsg) throws Exception {
		InputStream stream = new ByteArrayInputStream(inMsg.getBytes());
		MessageFactory mf = MessageFactory.newInstance();

		SOAPMessage message;

		message = mf.createMessage(new MimeHeaders(), stream);
		NodeList nodeList = message.getSOAPBody().getChildNodes();
		int num = 0;
		if (nodeList.getLength() == 3)
			num = 1;

		String fileName = (nodeList.item(num).getNodeName().split(":").length > 1 ? nodeList
				.item(num).getNodeName().split(":")[1]
				: nodeList.item(num).getNodeName().split(":")[0].split("Rq")[0])
						.split("Request")[0] + "Response";

//		System.out.println("psname:" + fileName);

		return fileName;

	}

	public String getURI(String uri) {

		String[] array1 = uri.split(":");
		String[] array2 = array1[array1.length - 1].split("/");
		String checkuri = array2[array2.length - 1];
		if ("Mock".equals(checkuri))
			checkuri = array2[array2.length - 2];
		String[] array = checkuri.split("\\.");
		if (array.length > 4) {
			checkuri = array[0] + "." + array[1] + "." + array[2] + "."
					+ array[3] + "." + array[4];
		}

		uri = checkuri;

		return uri;

	}

	static public String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}