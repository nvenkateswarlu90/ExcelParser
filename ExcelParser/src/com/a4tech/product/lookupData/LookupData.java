package com.a4tech.product.lookupData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public  class LookupData {
	
	public static HashMap<String, String> criteriaInfo = new HashMap<String, String>();
	public static Set<String> repeateIndex = new HashSet<String>();
	static{
		criteriaInfo.put("MTRL", "Material");
		criteriaInfo.put("PRCL", "Product Color");
		repeateIndex.add("1");
		repeateIndex.add("22");
		repeateIndex.add("23");
		repeateIndex.add("24");
		repeateIndex.add("25");
		repeateIndex.add("26");
		repeateIndex.add("27");
		repeateIndex.add("57");
		repeateIndex.add("58");
		repeateIndex.add("59");
		repeateIndex.add("61");
		repeateIndex.add("62");
		repeateIndex.add("63");
		repeateIndex.add("64");
		repeateIndex.add("65");
		repeateIndex.add("66");
		repeateIndex.add("67");
		repeateIndex.add("68");
		repeateIndex.add("69");
		repeateIndex.add("70");
		repeateIndex.add("71");
		repeateIndex.add("72");
		
		repeateIndex.add("73");
		repeateIndex.add("74");
		repeateIndex.add("75");
		repeateIndex.add("76");
		repeateIndex.add("77");
		repeateIndex.add("78");
		repeateIndex.add("79");
		repeateIndex.add("80");
		repeateIndex.add("81");
		repeateIndex.add("82");
		repeateIndex.add("83");
		repeateIndex.add("84");
		repeateIndex.add("85");
		repeateIndex.add("86");
		repeateIndex.add("87");
		repeateIndex.add("88");
		repeateIndex.add("89");
		repeateIndex.add("90");
		repeateIndex.add("91");
		
		repeateIndex.add("92");
		repeateIndex.add("97");
		repeateIndex.add("98");
		repeateIndex.add("99");
		repeateIndex.add("100");
		repeateIndex.add("101");
		repeateIndex.add("102");
		repeateIndex.add("103");
		repeateIndex.add("104");
		repeateIndex.add("105");
		repeateIndex.add("106");
		repeateIndex.add("107");
		repeateIndex.add("108");
		repeateIndex.add("109");
		repeateIndex.add("110");
		repeateIndex.add("111");
		repeateIndex.add("112");
		repeateIndex.add("113");
		repeateIndex.add("114");
		repeateIndex.add("115");
		repeateIndex.add("116");
		repeateIndex.add("117");
		repeateIndex.add("118");
		repeateIndex.add("119");
		repeateIndex.add("120");
		repeateIndex.add("121");
		repeateIndex.add("122");
		repeateIndex.add("123");
		repeateIndex.add("124");
		repeateIndex.add("125");
		repeateIndex.add("126");
		repeateIndex.add("127");
		repeateIndex.add("128");
		repeateIndex.add("129");
		repeateIndex.add("130");
		repeateIndex.add("131");
		repeateIndex.add("132");
		repeateIndex.add("133");
		// product number
		repeateIndex.add("136");
		repeateIndex.add("137");
		repeateIndex.add("138");
		// sku index
		repeateIndex.add("140");
		repeateIndex.add("141");
		repeateIndex.add("144");
		repeateIndex.add("145");
		repeateIndex.add("146");
		repeateIndex.add("147");
		
		
		
	}
	
	public static String getCriteriaValue(String criteriaCode){
		if(criteriaCode != null){
			return criteriaInfo.get(criteriaCode);
		}
		return "";
	}
	
	public static boolean isRepeateIndex(String indexNo){
		if(repeateIndex.contains(indexNo)){
			return true;
		}
		return false;
	}

}
