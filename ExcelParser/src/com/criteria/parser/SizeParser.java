package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;

import com.a4.product.beans.Apparel;
import com.a4.product.beans.Capacity;
import com.a4.product.beans.Dimension;
import com.a4.product.beans.OtherSize;
import com.a4.product.beans.Size;
import com.a4.product.beans.Value;
import com.a4.product.beans.Values;
import com.a4.product.beans.Volume;

public class SizeParser {

	public Size getSizes(String sizeGroup, String sizeValue) {
		Size sizeObj = new Size();
		try{
		
		//shippingitemValue != null && !shippingitemValue.isEmpty()
		if (sizeGroup.contains("Dimension")) {
			Dimension dimensionObj = new Dimension();
			String DimenArr[] = sizeValue.split(",");
			List<Values> valuesList = new ArrayList<Values>();
			List<Value> valuelist;
			Values valuesObj = null;
			Value valObj;
			for (int i = 0; i <= DimenArr.length - 1; i++) {
				String[] DimenArr1 = DimenArr[i].split(";");
				valuesObj = new Values();
				valuelist = new ArrayList<Value>();
				for (int j = 0; j <= DimenArr1.length - 1; j++) {
					valObj = new Value();
					String[] DimenArr2 = DimenArr1[j].split(":");
					valObj.setAttribute(DimenArr2[0]);
					valObj.setValue(DimenArr2[1]);
					valObj.setUnit(DimenArr2[2]);
					valuelist.add(valObj);
					valuesObj.setValue(valuelist);
				}
				valuesList.add(valuesObj);
			}

			dimensionObj.setValues(valuesList);
			sizeObj.setDimension(dimensionObj);
		}

		if (sizeGroup.contains("Capacity")) {

			Capacity capacityObj = new Capacity();
			String capacityArr[] = sizeValue.split(",");
			List<Value> capacityvalueList = new ArrayList<Value>();

			for (int i = 0; i <= capacityArr.length - 1; i++) {
				String capacityArr1[] = capacityArr[i].split(":");
				Value valObjc = new Value();
				valObjc.setValue(capacityArr1[0]);
				valObjc.setUnit(capacityArr1[1]);
				capacityvalueList.add(valObjc);
				capacityObj.setValues(capacityvalueList);
			}

			sizeObj.setCapacity(capacityObj);
		}

		if (sizeGroup.contains("Volume")) {

			Volume volumeObj = new Volume();
			String volumeArr[] = sizeValue.split(",");
			List<Values> volumeouterList = new ArrayList<Values>();
			List<Value> volumeinnerList; // = new ArrayList<Value>();
			Values valuesObj;// = new Values();
			Value valObjc;

			for (int i = 0; i <= volumeArr.length - 1; i++) {
				String volumeArr1[] = volumeArr[i].split(":");
				valObjc = new Value();
				valuesObj = new Values();
				volumeinnerList = new ArrayList<Value>();
				valObjc.setValue(volumeArr1[0]);
				valObjc.setUnit(volumeArr1[1]);
				volumeinnerList.add(valObjc);

				valuesObj.setValue(volumeinnerList);

				volumeouterList.add(valuesObj);
			}

			volumeObj.setValues(volumeouterList);

			sizeObj.setVolume(volumeObj);

		}

		if (sizeGroup.contains("Apparel")) {

			Apparel apparelObj = new Apparel();
			String apparelArr[] = sizeValue.split(",");
			List<Value> apparelList = new ArrayList<Value>();
			for (int i = 0; i <= apparelArr.length - 1; i++) {
				Value valObjc = new Value();
				valObjc.setValue(apparelArr[i]);
				apparelObj.setType(sizeGroup);

				apparelList.add(valObjc);
				apparelObj.setValues(apparelList);

			}
			sizeObj.setApparel(apparelObj);

		}

		if (sizeGroup.contains("Other")) {

			OtherSize otherObj = new OtherSize();
			String otherArr[] = sizeValue.split(",");
			List<Value> otherList = new ArrayList<Value>();
			for (int i = 0; i <= otherArr.length - 1; i++) {
				Value valObj = new Value();
				valObj.setValue(otherArr[i]);
				otherList.add(valObj);
			}
			otherObj.setValues(otherList);
			sizeObj.setOther(otherObj);

		}

		}
		
		catch(Exception e)
		{
			return null;
		}
		return sizeObj;
	}
}