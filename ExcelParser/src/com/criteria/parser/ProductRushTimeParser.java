package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.a4.product.beans.RushTime;
import com.a4.product.beans.RushTimeValue;

public class ProductRushTimeParser {

	private Logger              _LOGGER              = Logger.getLogger(getClass());
	 
	public RushTime getRushTimeCriteria(String rushTimeValue){
		RushTime rushObj=new RushTime();
		try{ 
		String tempValue = rushTimeValue;
		String rushTimetArr[] = tempValue.split(",");
		List<RushTimeValue> rushValueTimeList =new ArrayList<RushTimeValue>();
		
		RushTimeValue rushValueObj=null;
		for (String tempRushTime : rushTimetArr) {
			try{
 			rushValueObj=new RushTimeValue();
 			String value=tempRushTime;
 			String valueArr[]=value.split(":");
 			
 			if(valueArr.length==2){
 				rushValueObj.setBusinessDays(valueArr[0]);
 				rushValueObj.setDetails(valueArr[1]);
 			}else if(valueArr.length==1){
 				String regex = "\\d+";
 				
 				if(valueArr[0].matches(regex)){
 					rushValueObj.setBusinessDays(valueArr[0]);
 					rushValueObj.setDetails("");
 				}else{
 					rushValueObj.setBusinessDays("");
 					rushValueObj.setDetails(valueArr[1]);
 				}
 				
 			}
 			
 			
 			rushValueTimeList.add(rushValueObj);
			}catch(Exception e){
				
			}
		}
 		rushObj.setAvailable(true);
		rushObj.setRushTimeValues(rushValueTimeList);
		}catch(Exception e){
			_LOGGER.error("Error while processing Personalization :"+e.getMessage());             
		   	return null;
		   	
		   }
		return rushObj;
		
	}

}
