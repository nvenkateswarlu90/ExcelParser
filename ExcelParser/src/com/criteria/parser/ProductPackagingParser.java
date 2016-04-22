package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

public class ProductPackagingParser {

	public List<String> getPackagingCriteria(String packaging){
		List<String> packagingList =new ArrayList<String>();
		try{
		String packagingValue = packaging;
		String packagingArr[] = packagingValue.split(",");
		
		for (String tempPackaging : packagingArr) {
 			packagingList.add(tempPackaging);
		}
		}catch(Exception e){
	         //need to log error over here             
		   	return null;
		   	
		   }
		 
		return packagingList;
		
	}
}
