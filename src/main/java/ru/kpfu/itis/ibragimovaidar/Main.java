package ru.kpfu.itis.ibragimovaidar;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		HttpClient httpClient = new HttpClientImpl();

		Map<String, String> getParams = new HashMap<>();
		getParams.put("firstName", "Aidar");
		getParams.put("lastName", "Ibragimov");

		Map<String, String> getHeaders = new HashMap<>();
		getHeaders.put("Accept", "application/json");

		System.out.println(httpClient.get("https://postman-echo.com/get", getHeaders, getParams));


		Map<String, String> postParams = new HashMap<>();
		postParams.put("firstName", "Aidar");
		postParams.put("lastName", "Ibragimov");

		Map<String, String> postHeaders = new HashMap<>();

		System.out.println(httpClient.post("https://postman-echo.com/post", postHeaders, postParams));

	}
}
