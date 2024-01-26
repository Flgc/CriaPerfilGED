package br.com.aspprev.uteis;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StringToJSON {

	public static String isTheStringToJSON(Object myObject) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(myObject);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
