package ru.kpfu.itis.ibragimovaidar;

import java.util.Map;

public final class ParamsUtil {

	public static String paramsMapToJsonString(Map<String, String> params){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		params.forEach((key, value) -> stringBuilder.append("\"").append(key)
				.append("\"").append(":").append("\"").append(value).append("\""));
		stringBuilder.append("}");
		return stringBuilder.toString();
	}

	public static String getUrlWithParams(String url, Map<String, String> params){
		StringBuilder urlBuilder = new StringBuilder(url + "?");
		if (!params.isEmpty()){
			params.forEach((key, value) ->
					urlBuilder.append(key)
							.append("=")
							.append(value)
							.append("&"));
			// delete last '&' symbol
			urlBuilder.deleteCharAt(urlBuilder.length()-1);
		}
		return urlBuilder.toString();
	}

	private ParamsUtil(){
		throw new AssertionError();
	}
}
