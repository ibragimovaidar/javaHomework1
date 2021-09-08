package ru.kpfu.itis.ibragimovaidar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static ru.kpfu.itis.ibragimovaidar.ParamsUtil.getUrlWithParams;
import static ru.kpfu.itis.ibragimovaidar.ParamsUtil.paramsMapToJsonString;

public class HttpClientImpl implements HttpClient {

	private static final String HTTP_METHOD_GET = "GET";
	private static final String HTTP_METHOD_POST = "POST";

	private static final String POST_REQUEST_MIME_TYPE = "application/json";

	@Override
	public String get(String url, Map<String, String> headers, Map<String, String> params) {
		return makeHttpRequest(HTTP_METHOD_GET, url, headers, params);
	}

	@Override
	public String post(String url, Map<String, String> headers, Map<String, String> params) {
		return makeHttpRequest(HTTP_METHOD_POST, url, headers, params);
	}

	private String makeHttpRequest(String method, String url, Map<String, String> headers, Map<String, String> params){
		// adding params to GET request
		if (method.equals(HTTP_METHOD_GET)){
			url = getUrlWithParams(url, params);
		}

		HttpURLConnection connection = createHttpURLConnection(method, url);
		headers.forEach(connection::setRequestProperty);
		// adding params to POST request
		if (method.equals(HTTP_METHOD_POST)){
			try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
				outputStream.writeBytes(paramsMapToJsonString(params));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		// body of response extract
		String responseBody = null;
		try (DataInputStream inputStream = new DataInputStream(connection.getInputStream())) {
			byte[] bytes = new byte[inputStream.available() + 1];
			inputStream.readFully(bytes);
			responseBody = new String(bytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			connection.disconnect();
		}
		return responseBody;
	}

	private HttpURLConnection createHttpURLConnection(String method, String url){
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod(method);
			if (method.equals(HTTP_METHOD_POST)){
				connection.setRequestProperty("Content-type", POST_REQUEST_MIME_TYPE);
				connection.setDoOutput(true);
			}
			connection.setDoInput(true);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Incorrect url", e);
		} catch (IOException e) {
			throw new RuntimeException("Error establishing connection", e);
		}
		return connection;
	}
}
