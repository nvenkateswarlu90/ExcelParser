package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;


public class ProductOriginParser {

	public List<String> getOriginCriteria(String origin){
		List<String> originList =new ArrayList<String>();
		try{ 
		String originValue = origin;
		String originArr[] = originValue.split(",");
		
		for (String tempOrigin : originArr) {
 			originList.add(tempOrigin);
		}
		
		}catch(Exception e){
	         //need to log error over here             
		   	return null;
		   	
		   }
		return originList;
		
	}
}
