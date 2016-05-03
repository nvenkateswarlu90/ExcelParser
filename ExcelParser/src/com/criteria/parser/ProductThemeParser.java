package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ProductThemeParser {
 
	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
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
			_LOGGER.error("Error while processing Product Theme :"+e.getMessage());            
		   	return null;
		 }
	}
}
