package com.example.adminpanel.http;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.adminpanel.entity.Group;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HttpUtil {

	private final HttpClient client = HttpClient.newHttpClient();
	private String serverUri;
	
	public HttpUtil() {
		String propUri = HttpUtil.class.getResource("application.properties").toExternalForm();
		Properties properties = new Properties();
		try {
			
			properties.load(new FileInputStream(propUri));
			serverUri = properties.getProperty("server_uri");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> getGroupNames() {
		try {
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(serverUri + "/group"))
					.GET().build();
			HttpResponse<String> response = client.send(request, null);
			if(response.statusCode() != 200) {
				return null;
			}
			
			List<Group> groups = new Gson().fromJson(response.body(), new TypeToken<ArrayList<Group>>(){}.getType());
			System.out.println(groups);
			return groups.stream()
					.map((e) -> e.getName())
					.toList();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
