
package br.com.thorntail.manager;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.enterprise.context.ApplicationScoped;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import br.com.thorntail.model.Planet;

@ApplicationScoped
public class PlanetManager {

	private static final int PLANETS_TOTAL_MAPPEDS = 61;

	private static final String HTTPS_URL_SWAPI = "https://swapi.co/api/planets/{0}/?format=json";

	private ConcurrentMap<String, Planet> inMemoryPlanetStorage = new ConcurrentHashMap<>();

	public Optional<Planet> getInMemoryPlanet(String planetName) {
		if (planetName != null) {
			loadInMemoryPlanets();
			return Optional.<Planet>ofNullable(inMemoryPlanetStorage.get(planetName.toLowerCase()));
		}
		return Optional.<Planet>empty();
	}

	private void loadInMemoryPlanets() {
		if (inMemoryPlanetStorage.isEmpty() && inMemoryPlanetStorage.size() != PLANETS_TOTAL_MAPPEDS) {
			inMemoryPlanetStorage.clear();
			for (int i = 1; i <= PLANETS_TOTAL_MAPPEDS; i++) {
				populateInMemoryPlanetStorage(i);
			}

		}
	}

	private void populateInMemoryPlanetStorage(Integer urlParam) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpUriRequest getRequest = new HttpGet(MessageFormat.format(HTTPS_URL_SWAPI, Integer.toString(urlParam)));
		getRequest.addHeader(HttpHeaders.ACCEPT, "application/json");

		try (CloseableHttpResponse httpResponse = httpClient.execute(getRequest)) {
			String content = EntityUtils.toString(httpResponse.getEntity());
			JSONObject jsonObject = new JSONObject(content);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				addPlanet(jsonObject);
			}
		} catch (IOException e) {
			// handle exception
		}
	}

	private void addPlanet(JSONObject jsonObject) {
		Planet planet = new Planet();
		planet.setName((String) jsonObject.get("name"));
		planet.setClimate((String) jsonObject.get("climate"));
		planet.setTerrain((String) jsonObject.get("terrain"));
		planet.setMovies(jsonObject.getJSONArray("films").toList().size());
		inMemoryPlanetStorage.put(planet.getName().toLowerCase(), planet);
	}

}
