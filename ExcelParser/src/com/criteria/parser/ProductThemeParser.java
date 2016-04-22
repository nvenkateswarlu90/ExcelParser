package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

public class ProductThemeParser {

	
	public List<String> getThemeCriteria(String theme){
		
		List<String> themeList =new ArrayList<String>();
		try{
		String themeValue = theme;
		String themeArr[] = themeValue.split(",");
		
		for (String tempTheme : themeArr) {
 			themeList.add(tempTheme);
		}
		return themeList;
		}catch(Exception e){
	         //need to log error over here             
		   	return null;
		 }
	}
}
