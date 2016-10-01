package udiwrapper.openFDA;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class UDIWrapper {
    private String apiKey;
    private String searchProperty;
    private String searchValue;
    private String countValue;
    private String limitValue;
    private String skipValue;

    private final String SEARCH = "search";
    private final String COUNT = "count";
    private final String SKIP = "skip";
    private final String LIMIT = "limit";
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

    private UDIWrapper(String apiKey,
                       String searchProperty,
                       String searchValue,
                       String countValue,
                       String limitValue,
                       String skipValue){
        this.apiKey = apiKey;
        this.searchProperty = searchProperty;
        this.searchValue = searchValue;
        this.countValue = countValue;
        this.limitValue = limitValue;
        this.skipValue = skipValue;
    }

    public boolean getSearchExists(){
        HttpUrl searchUrl = OPEN_FDA_UDI_BUILDER
                .addQueryParameter(SEARCH, searchProperty + ":" + searchValue)
                .addQueryParameter(COUNT, countValue)
                .addQueryParameter(LIMIT, limitValue)
                .addQueryParameter(SKIP, skipValue)
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

    public List<Object> getDevices(){
        return null;
    }

    public int getTotal(){
        return 0;
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
            if (!countValue.equals("") && !skipValue.equals("0")){
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

            UDIWrapper wrapper =
                    new UDIWrapper(apiKey,
                            searchProperty,
                            searchValue,
                            countValue,
                            limitValue,
                            skipValue);

            return wrapper;
        }
    }
}
