package net.shtab.hid;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
	static final Logger logger = Logger.getLogger("App");
	static final String URL = "https://interes.shtab.net/api/document/detect/";
	static final String API_KEY = System.getenv("RADA_API_KEY");

	static final HttpClient client = HttpClient.newBuilder().build();
	static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		logger.info("Let's download some docs!!!");
		if (client == null) {
			logger.log(Level.SEVERE, "Error creating HTTP client");
			return;
		}
		
		if (null == API_KEY || API_KEY.isBlank()) {
			logger.log(Level.SEVERE, "API key is not maintained");
			return;			
		}

		for (int i = 0; i < args.length; i++) {
			Loader loader = LoaderFactory.factory(args[i]);
			if(null == loader) {
				logger.info("No implementation for " + args[i]);
			}
			loader.init();
			loop: while (loader.hasNext()) {
				LoaderDocument doc = loader.next();
				logger.info(loader.getName() + " -> " + doc.title);
				int code = send(doc);
				switch (code) {
				case 201:
					continue loop;
				case 400:
					break loop;
				default:
					logger.log(Level.INFO, "Unexpected response " + code);
				}
			}
			loader.close();
		}
	}

	private static int send(LoaderDocument doc) {
		try {
			HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(mapper.writeValueAsString(doc)))
					.uri(URI.create(URL)).setHeader("AUTHORIZATION", API_KEY).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			logger.log(Level.INFO, response.body().substring(1, 50));
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}

		return 0;
	}
}
