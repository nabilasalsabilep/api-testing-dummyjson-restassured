package context;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class TestContext {
    private Response response;
    private static final Map<String, Object> data = new HashMap<>();

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void set(String key, Object value) {
        // Implementation to save the key-value pair in a context store
        // This could be a map or any other storage mechanism
        data.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        // Implementation to retrieve the value by key and convert it to the specified type
       return type.cast(data.get(key));
    }
}
