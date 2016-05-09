package com.criteria.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.a4.product.beans.Configurations;
import com.a4.product.beans.ProductNumber;
import com.excel.ApplicationConstants;
import com.excel.CommonUtilites;

public class ProductNumberParser {
	
	private Logger              _LOGGER              = Logger.getLogger(getClass());

	public ProductNumber getProductNumer(String productNumberCriteria1,String productNumberCriteria2,String productNumber ){
		List<ProductNumber> pnumberList=new ArrayList<ProductNumber>();
		ProductNumber pnumberObj=new ProductNumber();
		try{
		List<Configurations> configList=new ArrayList<Configurations>();
		List<Object> Value;
		Configurations configObj;
		Map<String, String> criCodeMap=new HashMap<String, String>();
		criCodeMap=CommonUtilites.getMap();
		
		
		pnumberObj.setProductNumber(productNumber);
		if(productNumberCriteria1!=null && !productNumberCriteria1.isEmpty()){
			String pnumberArr[]=productNumberCriteria1.split(ApplicationConstants.CONST_DELIMITER_COLON);
			configObj=new Configurations();
			Value= new ArrayList<Object>();
			configObj.setCriteria(criCodeMap.get(pnumberArr[0]));
			Value.add(pnumberArr[1]);
			
			configObj.setValue(Value);
			configList.add(configObj);
			
		}
		
		if(productNumberCriteria2!=null && !productNumberCriteria2.isEmpty()){
			String pnumberArr[]=productNumberCriteria2.split(ApplicationConstants.CONST_DELIMITER_COLON);
			configObj=new Configurations();
			Value= new ArrayList<Object>();
			configObj.setCriteria(criCodeMap.get(pnumberArr[0]));
			Value.add(pnumberArr[1]);
			
			configObj.setValue(Value);
			configList.add(configObj);
			
		}
		pnumberObj.setConfigurations(configList);
		}catch(Exception e){
			_LOGGER.error("Error while processing Product Number :"+e.getMessage());             
		   	return null;
		   	
		   }
		return pnumberObj;		
	}
}
