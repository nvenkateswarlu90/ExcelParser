package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;
import com.a4.product.beans.Inventory;
import com.a4.product.beans.ProductSKUConfiguration;
import com.a4.product.beans.ProductSkus;

public class ProductSkuParser {

	public ProductSkus getProductRelationSkus(String sKUCriteria1,
			String sKUCriteria2, String skuvalue, String inlink,
			String instatus, int inQuantity) {


		ProductSkus prodSkuObj = new ProductSkus();
		try {
			if (sKUCriteria1 != null && sKUCriteria2 == null) {
				prodSkuObj.setSKU(skuvalue);
				Inventory Inventory = new Inventory();
				Inventory.setInventoryLink(inlink);
				Inventory.setInventoryStatus(instatus);
				Inventory.setInventoryQuantity(Integer.toString(inQuantity));
				prodSkuObj.setInventory(Inventory);
				ProductSKUConfiguration skuconfObj = new ProductSKUConfiguration();
				String skuArr[] = sKUCriteria1.split(":");
				skuconfObj.setCriteria(skuArr[0]);
				List<Object> val = new ArrayList<Object>();
				val.add(skuArr[1]);
				skuconfObj.setValue(val);
				List<ProductSKUConfiguration> skulist = new ArrayList<ProductSKUConfiguration>();
				skulist.add(skuconfObj);
				prodSkuObj.setConfigurations(skulist);
			}
			if (sKUCriteria2 != null && sKUCriteria1 == null) {
				prodSkuObj.setSKU(skuvalue);
				Inventory Inventory = new Inventory();
				Inventory.setInventoryLink(inlink);
				Inventory.setInventoryStatus(instatus);
				Inventory.setInventoryQuantity(Integer.toString(inQuantity));
				prodSkuObj.setInventory(Inventory);
				ProductSKUConfiguration skuconfObj = new ProductSKUConfiguration();
				String skuArr[] = sKUCriteria2.split(":");
				skuconfObj.setCriteria(skuArr[0]);
				List<Object> val = new ArrayList<Object>();
				val.add(skuArr[1]);
				skuconfObj.setValue(val);
				List<ProductSKUConfiguration> skulist = new ArrayList<ProductSKUConfiguration>();
				skulist.add(skuconfObj);
				prodSkuObj.setConfigurations(skulist);

			}
			if (sKUCriteria1 != null && sKUCriteria2 != null) {
				prodSkuObj.setSKU(skuvalue);
				Inventory Inventory = new Inventory();
				Inventory.setInventoryLink(inlink);
				Inventory.setInventoryStatus(instatus);
				Inventory.setInventoryQuantity(Integer.toString(inQuantity));
				prodSkuObj.setInventory(Inventory);

				String skuArr1[] = sKUCriteria1.split(":");
				String skuArr2[] = sKUCriteria2.split(":");
				ProductSKUConfiguration skuconfObj1 = new ProductSKUConfiguration();
				ProductSKUConfiguration skuconfObj2 = new ProductSKUConfiguration();

				List<ProductSKUConfiguration> skulist = new ArrayList<ProductSKUConfiguration>();

				skuconfObj1.setCriteria(skuArr1[0]);
				List<Object> val1 = new ArrayList<Object>();
				val1.add(skuArr1[1]);
				skuconfObj1.setValue(val1);
				skulist.add(skuconfObj1);

				skuconfObj2.setCriteria(skuArr2[0]);
				List<Object> val2 = new ArrayList<Object>();
				val2.add(skuArr2[1]);
				skuconfObj2.setValue(val2);
				skulist.add(skuconfObj2);

				prodSkuObj.setConfigurations(skulist);
			}
		} catch (Exception e) {
			return null;
		}

		return prodSkuObj;
	}

}
