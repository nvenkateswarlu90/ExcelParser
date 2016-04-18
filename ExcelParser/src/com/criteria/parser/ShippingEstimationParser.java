package com.criteria.parser;

import java.util.ArrayList;
import java.util.List;
import com.a4.product.beans.Dimensions;
import com.a4.product.beans.NumberOfItems;
import com.a4.product.beans.ShippingEstimate;
import com.a4.product.beans.Weight;

public class ShippingEstimationParser {

	public ShippingEstimate getShippingEstimates(String shippingitemValue,
			String shippingdimensionValue, String shippingWeightValue) {

		ShippingEstimate ItemObject = new ShippingEstimate();
		List<Object> shipingEstObj1 = new ArrayList<Object>();
		List<Object> shipingEstObj2 = new ArrayList<Object>();

		NumberOfItems itemObj = new NumberOfItems();
		if (shippingitemValue != null && !shippingitemValue.isEmpty()) {
			String shipItemArr[] = shippingitemValue.split(":");
			itemObj.setUnit(shipItemArr[0]);
			itemObj.setValue(shipItemArr[1]);
			shipingEstObj1.add(itemObj);
			ItemObject.setNumberOfItems(shipingEstObj1);
		}

		if (shippingWeightValue != null && !shippingWeightValue.isEmpty()) {
			Weight weightObj = new Weight();
			String shipweightArr[] = shippingWeightValue.split(":");
			weightObj.setUnit(shipweightArr[0]);
			weightObj.setValue(shipweightArr[1]);
			shipingEstObj2.add(weightObj);
			ItemObject.setWeight(shipingEstObj2);
		}

		if (shippingdimensionValue != null && !shippingdimensionValue.isEmpty()) {
			String shipDimenArr[] = shippingdimensionValue.split(";");
			List<Dimensions> dimenlist = new ArrayList<Dimensions>();
			Dimensions dimensionObj = new Dimensions();
			for (int i = 0; i <= shipDimenArr.length - 1; i++) {

				String[] shipDimenArr1 = shipDimenArr[i].split(":");
				if (i == 0) {
					dimensionObj.setHeight(shipDimenArr1[0]);
					dimensionObj.setHeightUnit(shipDimenArr1[1]);
				} else if (i == 1) {
					dimensionObj.setLength(shipDimenArr1[0]);
					dimensionObj.setLengthUnit(shipDimenArr1[1]);
				} else if (i == 2) {

					dimensionObj.setWidth(shipDimenArr1[0]);
					dimensionObj.setWidthUnit(shipDimenArr1[1]);
				}

				dimenlist.add(dimensionObj);
			}
			ItemObject.setDimensions(dimensionObj);
		}
		return ItemObject;

	}
}
