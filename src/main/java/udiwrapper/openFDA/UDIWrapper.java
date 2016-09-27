package udiwrapper.openFDA;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class UDIWrapper {
    private String apiKey;
    private String searchProperty;
    private String searchValue;
    private int countValue;
    private int limitValue;
    private int skipValue;

    private final String DEFAULT_SEARCH_PROPERTY = "identifiers.id:";
    private final String SEARCH = "search";
    private final String COUNT = "count";
    private final String SKIP = "skip";
    private final String LIMIT = "limit";
    private final String DEFAULT_COUNT = "identifiers.id";
    private final String DEFAULT_LIMIT = "1";
    private final String DEFAULT_SKIP = "0";
    private final String FDA_SCHEME = "https";
    private final String FDA_HOST = "api.fda.gov";
    private final String FDA_DEVICE_PATH = "device";
    private final String FDA_UDI = "udi.json";
    private final String API_KEY = "api_key";

    private HttpUrl.Builder OPEN_FDA_UDI_BUILDER = new HttpUrl.Builder()
            .scheme(FDA_SCHEME)
            .host(FDA_HOST)
            .addPathSegment(FDA_DEVICE_PATH)
            .addPathSegment(FDA_UDI)
            .addQueryParameter(API_KEY, apiKey);

    private UDIWrapper(String apiKey){
        this.apiKey = apiKey;
    }

    public static UDIWrapper setApiKey(String apiKey){
        return new UDIWrapper(apiKey);
    }

    public boolean getDeviceExists(String deviceId){
        searchValue = deviceId;
        HttpUrl searchUrl = OPEN_FDA_UDI_BUILDER
                .addQueryParameter(SEARCH, DEFAULT_SEARCH_PROPERTY + searchValue)
                .addQueryParameter(COUNT, DEFAULT_COUNT)
                .addQueryParameter(LIMIT, DEFAULT_LIMIT)
                .addQueryParameter(SKIP, DEFAULT_SKIP)
                .build();

        Request request = new Request.Builder().url(searchUrl).build();
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            return response.code() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
