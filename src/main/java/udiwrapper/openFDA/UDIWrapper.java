package udiwrapper.openFDA;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class UDIWrapper {
    private boolean searchExists;
    private int total;
    private List<Object> devices;

    private UDIWrapper(String apiKey,
                       String searchProperty,
                       String searchValue,
                       String countValue,
                       String limitValue,
                       String skipValue){

        String SEARCH = "search";
        String COUNT = "count";
        String SKIP = "skip";
        String LIMIT = "limit";
        String FDA_SCHEME = "https";
        String FDA_HOST = "api.fda.gov";
        String FDA_DEVICE_PATH = "device";
        String FDA_UDI = "udi.json";
        String API_KEY = "api_key";
        HttpUrl searchUrl = new HttpUrl.Builder()
                .scheme(FDA_SCHEME)
                .host(FDA_HOST)
                .addPathSegment(FDA_DEVICE_PATH)
                .addPathSegment(FDA_UDI)
                .addQueryParameter(API_KEY, apiKey)
                .addQueryParameter(SEARCH, searchProperty + ":" + searchValue)
                .addQueryParameter(COUNT, countValue)
                .addQueryParameter(LIMIT, limitValue)
                .addQueryParameter(SKIP, skipValue)
                .build();


        Request request = new Request.Builder().url(searchUrl).build();
        OkHttpClient client = new OkHttpClient();

        searchExists = false;
        total = 0;
        devices = null;

        try {
            Response response = client.newCall(request).execute();
            searchExists = (response.code() == 200);
            if (searchExists && countValue.isEmpty()) {
                JSONObject metaObject = new JSONObject(response.body().string()).getJSONObject("meta");
                total = metaObject.getJSONObject("results").getInt("total");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean getSearchExists(){
        return searchExists;
    }

    public List<Object> getDevices(){
        return devices;
    }

    public int getTotal(){
        return total;
    }

    public static class Builder {
        private String apiKey;
        private String searchProperty;
        private String searchValue;
        private String countValue;
        private String limitValue;
        private String skipValue;

        // default constants
        private final String DEFAULT_SEARCH_PROPERTY = "identifiers.id";
        private final String DEFAULT_COUNT = "";
        private final String DEFAULT_LIMIT = "1";
        private final String DEFAULT_SKIP = "0";

        public Builder(String apiKey) {
            this.apiKey = apiKey;
        }

        public Builder setSearch(String searchProperty, String searchValue){
            if (searchProperty == null) {
                this.searchProperty = DEFAULT_SEARCH_PROPERTY;
            } else {
                this.searchProperty = searchProperty;
            }

            this.searchValue = searchValue;
            return this;
        }

        public Builder setCount(String count){
            this.countValue = count;
            return this;
        }

        public Builder setLimit(int limit){
            this.limitValue = Integer.toString(limit);
            return this;
        }

        public Builder setLimit(String limit){
            this.limitValue = limit;
            return this;
        }

        public Builder setSkip(int skip){
            this.skipValue = Integer.toString(skip);
            return this;
        }

        public Builder setSkip(String skip){
            this.skipValue = skip;
            return this;
        }

        public UDIWrapper build() {
            // set defaults if they weren't set by the user
            if (searchProperty == null){
                searchProperty = DEFAULT_SEARCH_PROPERTY;
            }
            if (countValue == null){
                countValue = DEFAULT_COUNT;
            }
            if (limitValue == null){
                limitValue = DEFAULT_LIMIT;
            }
            if (skipValue == null){
                skipValue = DEFAULT_SKIP;
            }

            // count and skip can't be used together. If they're both set, throw
            // a new exception
            if (!countValue.isEmpty() && !skipValue.equals("0")){
                throw new IllegalArgumentException("You must pick either count OR skip. You cannot use both");
            }

            // don't allow the api key to be null
            if (apiKey == null){
                throw new IllegalArgumentException("You must set the API Key!");
            }

            // don't allow the search value to be null
            if (searchValue == null){
                throw new IllegalArgumentException("A search value must be set!");
            }

            return new UDIWrapper(apiKey,
                    searchProperty,
                    searchValue,
                    countValue,
                    limitValue,
                    skipValue);
        }
    }
}
