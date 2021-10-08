package net.shtab.hid;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadTask extends TimerTask {
	private final Logger logger = Logger.getLogger(LoadTask.class.getSimpleName());
	private final HttpClient client = HttpClient.newBuilder().build();
	private final ObjectMapper mapper = new ObjectMapper();
	private final String API_KEY = System.getenv("RADA_API_KEY");
	private final String URL = "https://interes.shtab.net/api/document/detect/";

	private String[] loaders;

	public LoadTask(String[] loaders) {
		this.loaders = loaders;
		if (loaders.length <= 0) {
			logger.warning("Loaders list is empty");
		}
		if (client == null) {
			logger.warning("Error creating HTTP client");
		}

		if (null == API_KEY || API_KEY.isBlank()) {
			logger.warning("API key is not maintained");
		}

	}
	
	public boolean isReady() {
		return client != null && API_KEY != null && !API_KEY.isBlank();
	}

	@Override
	public void run() {
		for (int i = 0; i < loaders.length; i++) {
			Loader loader = LoaderFactory.factory(loaders[i]);
			if (null == loader) {
				logger.info("No implementation for " + loaders[i]);
			} else {
				logger.info(loader.getName() + " starting =======>");
			}

			loader.init();
			while (loader.hasNext()) {
				LoaderDocument doc = loader.next();
				if (null == doc.link) {
					logger.info(loader.getName() + " -> " + doc.title + " skipped. No file assigned");
					continue;
				}
				logger.info(loader.getName() + " -> " + doc.title);
				int code = send(doc);
				logger.info(loader.getName() + " -> " + "Doc posted. Response: " + code);
			}
			loader.close();
		}
		logger.info("<----- Task finished. Next execution planned in " + App.TIMEOUT + " seconds");

	}

	private int send(LoaderDocument doc) {
		try {
			String s = mapper.writeValueAsString(doc);
			HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(s)).uri(URI.create(URL))
					.setHeader("AUTHORIZATION", API_KEY).setHeader("Content-Type", "application/json").build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException e) {
			logger.warning(e.getMessage());
		} catch (InterruptedException e) {
			logger.warning(e.getMessage());
		}

		return 0;
	}

}
