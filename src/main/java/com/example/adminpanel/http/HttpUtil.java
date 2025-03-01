package com.example.adminpanel.http;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.adminpanel.entity.Group;
import com.example.adminpanel.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class HttpUtil {

	private final HttpClient client = HttpClient.newHttpClient();
	private String serverUri;
	
	public HttpUtil() {
		String propUri = "src/main/resources/application.properties";
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
					.uri(new URI(serverUri + "/group/"))
					.GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if(response.statusCode() != 200) {
				return null;
			}
			
			List<Group> groups = new Gson().fromJson(response.body(), new TypeToken<ArrayList<Group>>(){}.getType());
			System.out.println(groups);
			return groups.stream()
					.map(Group::getName)
					.toList();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean saveNewUser(User user) {
		try {
			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd").create();
			String userJson = gson.toJson(user);

			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(serverUri + "/user/create"))
					.header("Content-type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(userJson))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return response.statusCode() == 200;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Group> getGroups(int count) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(serverUri + "/group/get?count=" + count))
					.GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if(response.statusCode() != 200) {
				return null;
			}

			List<Group> groups = new Gson().fromJson(response.body(), new TypeToken<ArrayList<Group>>(){}.getType());
			System.out.println(groups);
			return groups;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Group> findGroupByParam(String paramName, String paramValue) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(serverUri + "/group/find?" + paramName + "=" + paramValue))
					.GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if(response.statusCode() != 200) {
				return null;
			}

			List<Group> groups = new Gson().fromJson(response.body(), new TypeToken<ArrayList<Group>>(){}.getType());
			System.out.println(groups);
			return groups;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean saveGroup(Group group) {
		try {
			String groupJson = new Gson().toJson(group);

			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(serverUri + "/group/"))
					.header("Content-type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(groupJson))
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return response.statusCode() == 200;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean deleteGroupById(int id) {
		try {

			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(serverUri + "/group/" + id))
					.DELETE()
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return response.statusCode() == 200;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
