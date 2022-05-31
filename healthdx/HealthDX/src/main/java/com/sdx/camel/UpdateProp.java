package com.sdx.camel;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateProp {



	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		// TODO Auto-generated method stub
		final ObjectMapper mapper = new ObjectMapper();
		String jsonPropertyString ="Testing";
		LinkedHashMap outValue = mapper.readValue(jsonPropertyString, LinkedHashMap.class);
		System.out.println("outValue"+outValue);
	}

}
