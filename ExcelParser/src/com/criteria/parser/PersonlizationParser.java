package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.a4.product.beans.Personalization;

public class PersonlizationParser {
	
	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
	public  List<Personalization> getPersonalization(
			String personalizevalue) {

		List<Personalization> personaliseList = new ArrayList<Personalization>();
		try{
		String PersonalizationArr[] = personalizevalue.split(",");
		for (int i = 0; i <= PersonalizationArr.length - 1; i++) {
			Personalization perObj = new Personalization();
			String pers = PersonalizationArr[i];
			String[] temp = null;
			if (pers.contains("=")) {
				temp = pers.split("=");
				perObj.setType(temp[0]);
				perObj.setAlias(temp[1]);
			} else {
				perObj.setType(temp[1]);
			}

			personaliseList.add(perObj);
		}
		}catch(Exception e){
			_LOGGER.error("Error while processing Personalization :"+e.getMessage());
		}
		return personaliseList;

	}
}