package com.a4.product.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Volume {
    
	@JsonProperty("Values")
	private List<Values> values;
	
	public List<Values> getValues() {
		return values;
	}

	public void setValues(List<Values> values) {
		this.values = values;
	}

}
