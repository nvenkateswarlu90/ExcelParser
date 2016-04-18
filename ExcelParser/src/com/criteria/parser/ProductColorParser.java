package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import com.a4.product.beans.Color;
import com.a4.product.beans.Combo;

public class ProductColorParser {
	 private final static String COMBO_VALUES_SEPARATOR = ":";
	 private final static Integer COMBO_TEXT_VALUE_INDEX = 1;
	 public static final String   CONST_STRING_COMBO_TEXT = "Combo";
	   
	public List<Color> getColorCriteria(String color){
		List<Color> colorList=new ArrayList<Color>();
		
		try{
		Color colorObj;
		String colorArr[]=color.split(",");
		 boolean isCombo=false;
		 List<Combo> comboList=null;
		 Combo comboObj=new Combo();
		for (String value : colorArr) {
			colorObj=new Color();
			//String tempColor=value;
			comboList	=new ArrayList<Combo>();
			
            String originalValue = value;
            String teampValue=value;
            int index = value.indexOf("=");

            if (index != -1) {
                value = value.substring(0, index);
                originalValue = originalValue.substring(index + 1);
            }
            
            // 
            isCombo = isComboColors(value);
            
            if(!isCombo){
            	colorObj.setName(value);
            	colorObj.setAlias(originalValue);
            	colorList.add(colorObj);
            }else{ 
        		String colorArray[]=value.split(":");
        		
        		String name=colorArray[0];
        		
        		String alias=null;
                 int indexAlias = teampValue.indexOf("=");
                 
                 if (indexAlias != -1) {
                	 alias = teampValue.substring(indexAlias + 1);
                 }
                 int comboIndex=teampValue.indexOf("Combo");
                 String comboStr=teampValue.substring(comboIndex+6);//(comboIndex + 6);
                 String FinalComboStr=comboStr.substring(0, comboStr.indexOf("="));
                 //System.out.println("FinalComboStr "+FinalComboStr);
                 	 
                 
                 String finalarray[]=FinalComboStr.split(":");
                 if(finalarray.length==2){
                	 Color colorObj1=new Color();
                	 colorObj1.setName(name);
                	 colorObj1.setAlias(alias);
                	 
                	 Combo combotemp=new Combo();
                	 combotemp.setName(finalarray[0]);
                	 combotemp.setType(finalarray[1]);
                	 
                	 comboList.add(combotemp);
                	 
                	 colorObj1.setCombos(comboList);
                	 colorList.add(colorObj1);
                 }else if(finalarray.length==4){
                	 Color colorObj2=new Color();
                	 colorObj2.setName(name);
                     colorObj2.setAlias(alias);
                	
                	 Combo combo1=new Combo();
                	 Combo combo2=new Combo();
                	 combo1.setName(finalarray[0]);
                	 combo1.setType(finalarray[1]);
                	 combo2.setName(finalarray[2]);
                	 combo2.setType(finalarray[3]);
                	 comboList.add(combo1);
                	 comboList.add(combo2);
                	 colorObj2.setCombos(comboList);
                	 colorList.add(colorObj2);
                 }
            }  
		}

		}catch(Exception e){
		//need to log error over here
         return null;	
         }
		
		return colorList;
		
	}
	
	
	private boolean isComboColors(String value) {
    	boolean result = false;
    	if(value.contains(COMBO_VALUES_SEPARATOR)) {
    		String comboValues[] = value.split(COMBO_VALUES_SEPARATOR);
    		result = (comboValues.length % 2 == 0) && comboValues[COMBO_TEXT_VALUE_INDEX].equalsIgnoreCase( CONST_STRING_COMBO_TEXT);
    	}
    	return result;
    }
			
}
