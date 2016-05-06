package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import com.a4.product.beans.Option;

import com.excel.ApplicationConstants;


public class ProductOptionParser
{
	public Option getOptions(String optiontype,String optionname,String optionvalues,Boolean canorder,Boolean Reqfororder,String optionadditionalinfo) 
	{
		
		
		Option optobj=new Option();

		optobj.setOptionType(optiontype);
		optobj.setName(optionname);
		optobj.setAdditionalInformation(optionadditionalinfo);
		String optionArr[]=optionvalues.split(ApplicationConstants.CONST_STRING_COMMA_SEP);
		List<String> values=new ArrayList<String>();
		
		for (String string : optionArr) {
			values.add(string);
		}
		optobj.setValues(values);
		optobj.setCanOnlyOrderOne(canorder);
		optobj.setRequiredForOrder(Reqfororder);
		
		return optobj;	
	
}
}