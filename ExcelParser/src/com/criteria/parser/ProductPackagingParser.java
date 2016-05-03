package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ProductPackagingParser {

	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
	public List<String> getPackagingCriteria(String packaging){
		List<String> packagingList =new ArrayList<String>();
		try{
		String packagingValue = packaging;
		String packagingArr[] = packagingValue.split(",");
		
		for (String tempPackaging : packagingArr) {
 			packagingList.add(tempPackaging);
		}
		}catch(Exception e){
			_LOGGER.error("Error while processing Product Packaging :"+e.getMessage());             
		   	return null;
		   	
		   }
		 
		return packagingList;
		
	}
}
