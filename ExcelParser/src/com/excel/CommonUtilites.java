package com.excel;

import java.util.HashMap;
import java.util.Map;

public class CommonUtilites {

	
	public static  Map<String,String> criteriaCodeMap =new HashMap<String, String>();
	public static Map<String,String> getMap()
	{	
		criteriaCodeMap.put("SABR", "Size-Apparel-Bra Sizes");
		criteriaCodeMap.put("SANS", "Apparel-Neck/Sleeve");
		criteriaCodeMap.put("SAHU","Apparel-Hosiery/Uniform (A,AB)" );
		criteriaCodeMap.put("SAIT","Apparel-Infant/Toddler (3 Months, 2T)" );
		criteriaCodeMap.put("SAWI",	"Apparel-Waist/Inseam" );
		criteriaCodeMap.put("CAPS","SIZE - Capacity" );
		criteriaCodeMap.put("DIMS","SIZE - Dimension" );
		criteriaCodeMap.put("SSNM","Standard & Numbered" );
		criteriaCodeMap.put("SVWT","Size-Volume/Weight" );
		criteriaCodeMap.put("SOTH","Size-Other" );
		criteriaCodeMap.put("SHWT","Shipping Weight" );
		criteriaCodeMap.put("SMPL","Samples" );
		criteriaCodeMap.put("TDNM","Trade Name");
		criteriaCodeMap.put("THEM","Theme" );
		criteriaCodeMap.put("SDRU","Same Day Service" );
		criteriaCodeMap.put("SHAP","Shape" );
		criteriaCodeMap.put( "PRTM","Production Time" );
		criteriaCodeMap.put("IMSZ","Imprint Size" );
		criteriaCodeMap.put("LMIN","Less than Minimum" );
		criteriaCodeMap.put("MTRL","Material" );
		criteriaCodeMap.put("PCKG","Packaging" );
		criteriaCodeMap.put("ADCL", "Additional Colors" );
		criteriaCodeMap.put("ADLN","Additional Location" );
		criteriaCodeMap.put("ARTW","Artwork & Proofs" );
		criteriaCodeMap.put("BTRY","Battery Information" );
		criteriaCodeMap.put("CARR","Carrier" );
		criteriaCodeMap.put("PRCL","Product Color" );
		criteriaCodeMap.put("ORGN","Origin" );
		criteriaCodeMap.put("FOBP", "FOB Point" );
		criteriaCodeMap.put("IMCL","Imprint Color" );
		criteriaCodeMap.put("IMLO","Imprint Location" );
		criteriaCodeMap.put("IMMD","Imprint Method" );
		criteriaCodeMap.put("IMOP","Imprint Option");
		criteriaCodeMap.put("ITWT","Item Weight");
		criteriaCodeMap.put("PERS","Personalization");
		criteriaCodeMap.put("PROP","Product Option");
		criteriaCodeMap.put( "RUSH","Rush Service ");
		criteriaCodeMap.put("SDIM","Shipping Dimension");
		criteriaCodeMap.put("SHES","Shipping Estimate");
		criteriaCodeMap.put("SHOP","Shipping Option");
		criteriaCodeMap.put("WARR","Warranty Information");
		return criteriaCodeMap;
	}

	public static Map<String, String> getCriteriaCodeMap() {
		return criteriaCodeMap;
	}

	public static void setCriteriaCodeMap(Map<String, String> criteriaCodeMap) {
		CommonUtilites.criteriaCodeMap = criteriaCodeMap;
	}
	
	public static  void main(String args[]) {
		// TODO Auto-generated method stub
		//System.out.println(CommonUtilites.criteriaCodeMap.get("SHOP"));
	Map<String, String> refMap=	CommonUtilites.getMap();
	
	System.out.println(refMap.get("SHOP"));
	}
}
