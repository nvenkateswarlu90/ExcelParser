package com.criteria.parser;

import com.a4.product.beans.SameDayRush;

public class ProductSameDayParser {
	public SameDayRush getSameDayRush(String value){
		SameDayRush sdayObj=new SameDayRush();
		try{
		if(value.contains(":")){
		String samedyArr[]=value.split(":");
		
		if(samedyArr.length==2 && samedyArr[0].equalsIgnoreCase("Y")){
			sdayObj.setAvailable(true);
			sdayObj.setDetails(samedyArr[1]);
		}else if(samedyArr.length==1 && samedyArr[0].equalsIgnoreCase("Y")){
			sdayObj.setAvailable(true);
			sdayObj.setDetails("");
		}
		}else if(value.equalsIgnoreCase("Y")){
			sdayObj.setAvailable(true);
			sdayObj.setDetails("");
		}else{
			sdayObj.setAvailable(false);
			sdayObj.setDetails("");
			
		}
		
		}catch(Exception e){
	         //need to log error over here             
		   	return null;
		   	
		   }
		return sdayObj;
	}

}
