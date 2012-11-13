package com.ippoippo.joplin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * HTTP Client Configuration for only gdata.
 */
@Configuration
public class GdataClientConfig {

	@Bean
	public HttpRequestInitializer gdataRequestInitializer() {
		return new HttpRequestInitializer() {

			@Override
			public void initialize(HttpRequest request) {
				// set up the Google headers
				GoogleHeaders headers = new GoogleHeaders();
				headers.setApplicationName("joplin/1.0");
				headers.setGDataVersion("2");// or add the parameter, "v=2" to the URL
				request.setHeaders(headers);
				// set timeout
				request.setConnectTimeout(3000);
				request.setReadTimeout(3000);
				// set the parser
				JsonCParser parser = new JsonCParser(new JacksonFactory());
				request.setParser(parser);
			}
		};
	}
	
	@Bean
	public HttpRequestFactory gdataRequestFactory() {
		HttpTransport transport = new NetHttpTransport();
		return transport.createRequestFactory(gdataRequestInitializer());
	}
}