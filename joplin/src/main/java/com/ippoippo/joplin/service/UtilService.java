package com.ippoippo.joplin.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ippoippo.joplin.jdbc.mapper.UtilMapper;
import com.ippoippo.joplin.mongo.operations.UtilOperations;

@Service
public class UtilService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${healthcheck.url}")
	private String healthcheckUrl;

	@Inject
	private UtilMapper utilMapper;

	@Inject
	private UtilOperations utilOperations;

	public static final String MSG_YES = "I'm alive.";
	public static final String MSG_NO = "I'm not alive.";

	public String amIAlive() {
		Integer result = utilMapper.areYouAlive();
		Set<String> collectionNames = utilOperations.getCollectionNames();
		return MSG_YES;
	}

	/**
	 * To keep the application active in Paas environment.
	 */
	@Scheduled(fixedDelay=900000)
	public void accessMe() {
		try {
			URL url = new URL(healthcheckUrl);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("GET");
			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);
			http.connect();
			InputStream is = http.getInputStream();
			if (http.getResponseCode() != HttpURLConnection.HTTP_OK) {
				logger.error("http response code: " + http.getResponseCode());
			}
			http.disconnect();
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
	}
}
