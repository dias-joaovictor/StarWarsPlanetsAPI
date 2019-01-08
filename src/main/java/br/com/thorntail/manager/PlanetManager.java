
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

import br.com.thorntail.exception.manager.BusinessException;
import br.com.thorntail.model.Planet;

@ApplicationScoped
public class PlanetManager {

	private static final int PLANETS_TOTAL_MAPPEDS = 61;

	private static final String HTTPS_URL_SWAPI = "https://swapi.co/api/planets/{0}/?format=json";

	private ConcurrentMap<String, Planet> inMemoryPlanetStorage = new ConcurrentHashMap<>();

	public Optional<Planet> getInMemoryPlanet(String planetName) throws BusinessException {
		if (planetName != null) {
			Long startTime = System.currentTimeMillis();
			loadInMemoryPlanets();
			System.out.println("Tempo total => "+ (System.currentTimeMillis() - startTime)/1000 +" segundos");
			return Optional.<Planet>ofNullable(inMemoryPlanetStorage.get(planetName.toLowerCase()));
		}
		return Optional.<Planet>empty();
	}

	private void loadInMemoryPlanets() throws BusinessException{
		if (inMemoryPlanetStorage.isEmpty() && inMemoryPlanetStorage.size() != PLANETS_TOTAL_MAPPEDS) {
			inMemoryPlanetStorage.clear();
			for (int i = 1; i <= PLANETS_TOTAL_MAPPEDS; i++) {
				populateInMemoryPlanetStorage(i);
			}

		}
	}

	private void populateInMemoryPlanetStorage(Integer urlParam) throws BusinessException {
		Long startTime = System.currentTimeMillis();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpUriRequest getRequest = new HttpGet(MessageFormat.format(HTTPS_URL_SWAPI, Integer.toString(urlParam)));
		getRequest.addHeader(HttpHeaders.ACCEPT, "application/json");

		try (CloseableHttpResponse httpResponse = httpClient.execute(getRequest)) {
			String content = EntityUtils.toString(httpResponse.getEntity());
			JSONObject jsonObject = new JSONObject(content);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				addInMemoryPlanet(jsonObject);
			}
			System.out.println("Tempo total requisicao "+ urlParam +" => "+ (System.currentTimeMillis() - startTime) +" milisegundos");
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	private void addInMemoryPlanet(JSONObject jsonObject) {
		Planet planet = new Planet();
		planet.setName((String) jsonObject.get("name"));
		planet.setClimate((String) jsonObject.get("climate"));
		planet.setTerrain((String) jsonObject.get("terrain"));
		planet.setMovies(jsonObject.getJSONArray("films").toList().size());
		inMemoryPlanetStorage.put(planet.getName().toLowerCase(), planet);
	}

}
