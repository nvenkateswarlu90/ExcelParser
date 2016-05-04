package com.criteria.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.a4.product.beans.Inventory;
import com.a4.product.beans.ProductSKUConfiguration;
import com.a4.product.beans.ProductSkus;
import com.excel.ApplicationConstants;
import com.excel.CommonUtilites;

public class ProductSkuParser {

	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
	public ProductSkus getProductRelationSkus(String skuCriteria1, String skuCriteria2,
			   String sku, String skuinventoryLink, String skuinventoryStatus,
			   String skuinventoryQty) {
			  ProductSkus pskuObj=new ProductSkus();
			  try{
			  //List<ProductNumber> pnumberList=new ArrayList<ProductNumber>();
			  
			  //ProductNumber pnumberObj2=new ProductNumber();
			  List<ProductSKUConfiguration> configList=new ArrayList<ProductSKUConfiguration>();
			  List<Object> Value;
			  ProductSKUConfiguration configObj;
			  Map<String, String> criCodeMap=new HashMap<String, String>();
			  criCodeMap=CommonUtilites.getMap();
			  
			  pskuObj.setSKU(sku);
			  if(skuCriteria1!=null && !skuCriteria1.isEmpty()){
			   String pskuArr[]=skuCriteria1.split(ApplicationConstants.CONST_DELIMITER_COLON);
			   configObj=new ProductSKUConfiguration();
			   Value= new ArrayList<Object>();
			   configObj.setCriteria(criCodeMap.get(pskuArr[0]));
			   Value.add(pskuArr[1]);
			   
			   configObj.setValue(Value);
			   configList.add(configObj);
			   
			  }
			  
			  if(skuCriteria2!=null && !skuCriteria2.isEmpty()){
			   String pskuArr[]=skuCriteria2.split(ApplicationConstants.CONST_DELIMITER_COLON);
			   configObj=new ProductSKUConfiguration();
			   Value= new ArrayList<Object>();
			   configObj.setCriteria(criCodeMap.get(pskuArr[0]));
			   Value.add(pskuArr[1]);
			   
			   configObj.setValue(Value);
			   configList.add(configObj);
			   
			  }
			  
			  pskuObj.setConfigurations(configList);
			  
			  //pnumberList.add(pnumberObj);
			  Inventory inventory =new Inventory();
			  if(skuinventoryLink!=null && !skuinventoryLink.isEmpty()){
			   inventory.setInventoryLink(skuinventoryLink);
			  }else{
			   inventory.setInventoryLink(ApplicationConstants.CONST_STRING_EMPTY);
			  }
			  if(skuinventoryStatus!=null && !skuinventoryStatus.isEmpty()){
			   inventory.setInventoryStatus(skuinventoryStatus);
			  }else{
			   inventory.setInventoryStatus(ApplicationConstants.CONST_STRING_EMPTY);
			  }
			  if(skuinventoryQty!=null && !skuinventoryQty.isEmpty()){
			   inventory.setInventoryQuantity(skuinventoryQty);
			  }else{
			   inventory.setInventoryQuantity(ApplicationConstants.CONST_STRING_EMPTY);
			  }
			  pskuObj.setInventory(inventory);
			  }catch(Exception e){
				_LOGGER.error("Error while processing Product SKUs :"+e.getMessage());            
			    return null;
			     }
			  return pskuObj;
	}

}
