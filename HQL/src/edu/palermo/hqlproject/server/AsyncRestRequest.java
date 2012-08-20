package edu.palermo.hqlproject.server;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.util.Log;
import edu.palermo.hqlproject.server.listeners.AsyncRestListener;

public class AsyncRestRequest<T> extends AsyncTask<Map<String,String>, Integer, T> {
	
	private Class<T> mClass;
	private AsyncRestListener<T> listener;
	private String url;
	
	public AsyncRestRequest(String url, Class<T> classType, AsyncRestListener<T> listener){
		this.url = url;
		this.listener = listener;
		this.mClass = classType;
	}

	@Override
	protected T doInBackground(Map<String,String>... params) {
		Map<String,String> arguments = params[0];
		arguments.put("userAgent","Android");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		// Make the HTTP GET request, marshaling the response from JSON to an array of Events
		T result = null;
		try{
			ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, mClass, arguments);
			result = responseEntity.getBody();
		}catch (Exception e) {
			Log.e("AsyncRestRequest", "Request failed.",e);
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(T result) {
		if (result == null){
			listener.restCallDidFinishWithErrors();
		}else{
			listener.restCallDidFinishSucessfuly(result);
		}
	}

}
