package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ProductTradeNameParser {
           
	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
	public List<String> getTradeNameCriteria(String tradename){
		List<String> tradenameList =new ArrayList<String>();
		try{
		String tradeNameValue = tradename;
		String tradeArr[] = tradeNameValue.split(",");
		
		for (String tempTrade : tradeArr) {
 			tradenameList.add(tempTrade);
		}
		}catch(Exception e){
			_LOGGER.error("Error while processing Product TradeName :"+e.getMessage());       
		   	return null;
		   	
		   }
		return tradenameList;
		
	}
}
