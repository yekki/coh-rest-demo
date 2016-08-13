package demo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class RestExample {
    public static void PUT(String url, MediaType mediaType, String data) {
        process(url, "put", mediaType, data);
    }

    public static void GET(String url, MediaType mediaType) {
        process(url, "get", mediaType, null);
    }

    public static void POST(String url, MediaType mediaType, String data) {
        process(url, "post", mediaType, data);
    }

    public static void DELETE(String url, MediaType mediaType) {
        process(url, "delete", mediaType, null);
    }

    public static void process(String sUrl, String action,
                               MediaType mediaType,
                               String data) {
        Client client = ClientBuilder.newClient();
        Response response = null;

        WebTarget webTarget = client.target(sUrl);
        String responseType = MediaType.APPLICATION_XML;
        if (mediaType == MediaType.APPLICATION_JSON_TYPE) {
            responseType = MediaType.APPLICATION_JSON;
        }

        if (action.equalsIgnoreCase("get")) {
            response = webTarget.request(responseType).get();
        } else if (action.equalsIgnoreCase("post")) {
            Entity<String> person = Entity.entity(data, responseType);
            response = webTarget.request(responseType).post(person);
        } else if (action.equalsIgnoreCase("put")) {
            Entity<String> person = Entity.entity(data, responseType);
            response = webTarget.request(responseType).put(person);
        } else if (action.equalsIgnoreCase("delete")) {
            Entity<String> person = Entity.entity(data, responseType);
            response = webTarget.request(responseType).delete();
        }
        System.out.println(response.readEntity(String.class));
    }

    public static void main(String[] args) throws URISyntaxException,
            MalformedURLException, IOException {
        PUT("http://localhost:8080/dist-http-example/1",
                MediaType.APPLICATION_JSON_TYPE,
                "{\"name\":\"chris\",\"age\":32}");
        PUT("http://localhost:8080/dist-http-example/2",
                MediaType.APPLICATION_JSON_TYPE,
                "{\"name\":\"\ufeff\u30b8\u30e7\u30f3A\",\"age\":66}");
        PUT("http://localhost:8080/dist-http-example/3",
                MediaType.APPLICATION_JSON_TYPE,
                "{\"name\":\"adm\",\"age\":88}");
        POST("http://localhost:8080/dist-http-example/increment(age,1)",
                MediaType.APPLICATION_XML_TYPE, null);
        GET("http://localhost:8080/dist-http-example/1",
                MediaType.APPLICATION_JSON_TYPE);
        GET("http://localhost:8080/dist-http-example/1",
                MediaType.APPLICATION_XML_TYPE);
        GET("http://localhost:8080/dist-http-example/count()",
                MediaType.APPLICATION_XML_TYPE);
    }
}