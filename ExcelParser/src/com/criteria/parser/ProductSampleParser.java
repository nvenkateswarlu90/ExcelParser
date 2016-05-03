package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.a4.product.beans.RushTime;
import com.a4.product.beans.RushTimeValue;
import com.a4.product.beans.Samples;

public class ProductSampleParser {

	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
	public Samples getSampleCriteria(String prodsample, String specSample,
			boolean flag) {
		Samples samplesObj = new Samples();
		try{
		String prodSampleValue = prodsample;
		String specSampleValue = prodsample;

		if (flag) {
			String specSampleArr[] = specSampleValue.split(":");
			if(specSampleArr.length==2){
				String value=specSampleArr[0];
				if(value.equalsIgnoreCase("Y")){
					samplesObj.setSpecSampleAvailable(true);
					samplesObj.setSpecInfo(specSampleArr[1]);
				}
				else{
					samplesObj.setSpecSampleAvailable(false);
					samplesObj.setSpecInfo("");
				}
			}
			}else{
				samplesObj.setSpecSampleAvailable(false);
				samplesObj.setSpecInfo("");
			}
		

	

		if (prodSampleValue!=null && !prodSampleValue.isEmpty()) {
			String prodSampleArr[] = prodSampleValue.split(":");
			if(prodSampleArr.length==2){
				String value=prodSampleArr[0];
				if(value.equalsIgnoreCase("Y")){
					samplesObj.setProductSampleAvailable(true);
					samplesObj.setProductSampleInfo(prodSampleArr[1]);
				}
				else{
					samplesObj.setProductSampleAvailable(false);
					samplesObj.setProductSampleInfo("");
				}
			}
			}else{
				samplesObj.setProductSampleAvailable(false);
				samplesObj.setProductSampleInfo("");
			}
		
		specSampleValue=null;
		prodSampleValue=null;
		}catch(Exception e){
			_LOGGER.error("Error while processing Sample Parser :"+e.getMessage());          
		   	return null;
		   	
		   }
		return samplesObj;

	}
}
