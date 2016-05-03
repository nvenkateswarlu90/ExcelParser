package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.a4.product.beans.Artwork;

public class ProductArtworkProcessor {

	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
	public List<Artwork> getArtworkCriteria(String artwork){
		List<Artwork> artworkList=new  ArrayList<Artwork>();
		try{
		
		
		Artwork artObj=null;
		String artArr[]= artwork.split(",");
		
	     for (String tempArt : artArr) {
			artObj=new Artwork();
			String tempValues[]=tempArt.split(":");
			
			if(tempValues.length==2){
				artObj.setValue(tempValues[0]);
				artObj.setComments(tempValues[1]);
			}else if(tempValues.length==1){
				artObj.setValue(tempValues[0]);
				artObj.setComments("");
			}
			
			artworkList.add(artObj);
			
		}

           }catch(Exception e){
        	   _LOGGER.error("Error while processing Artwork :"+e.getMessage());
        	   return null;
	   	
	   }
		return artworkList;
		
	}
}
