package udiwrapper.openFDA;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import udiwrapper.openFDA.Device.Device;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class UDIWrapper {
    private boolean searchExists;
    private int total;
    private Map<String, Device> devices;

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
        devices = new HashMap<>();

        try {
            Response response = client.newCall(request).execute();
            searchExists = (response.code() == 200);
            if (searchExists && countValue.isEmpty()) {
                JSONObject baseJSON = new JSONObject(response.body().string());
                JSONObject metaObject = baseJSON.getJSONObject("meta");
                JSONArray resultsArray = baseJSON.getJSONArray("results");
                total = metaObject.getJSONObject("results").getInt("total");

                for (int i = 0; i < resultsArray.length(); i += 1){
                    JSONObject currentDeviceJSON = resultsArray.getJSONObject(i);

                    Device currentDevice = new Device(currentDeviceJSON);

                    devices.put(currentDevice.getDeviceIdentifier(), currentDevice);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @return Whether the search corresponds to devices.
     */
    public boolean getSearchExists(){
        return searchExists;
    }

    /**
     *
     * @return A map of Devices with their respective Device Identifiers as their keys.
     */
    public Map<String, Device> getDevices(){
        return devices;
    }

    /**
     *
     * @return A list of the Device Identifiers associated with the returned Devices.
     */
    public Collection<String> getDeviceIdentifiers(){
        return devices.keySet();
    }

    /**
     *
     * @param deviceIdentifier The Device Identifier corresponding to the device being retrieved
     * @return The {@link Device} associated with the Device Identifier
     */
    public Device getDevice(String deviceIdentifier){
        return devices.get(deviceIdentifier);
    }

    /**
     *
     * @return The total number of devices returned from the search
     */
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

        /**
         *
         * @param apiKey the API Key to be used for the search
         */
        public Builder(String apiKey) {
            this.apiKey = apiKey;
        }

        /**
         *
         * @param searchProperty The device property to search for. If null, the property defaults to the device identifier"
         * @param searchValue The value of the property to search
         *
         */
        public Builder setSearch(String searchProperty, String searchValue){
            if (searchProperty == null) {
                this.searchProperty = DEFAULT_SEARCH_PROPERTY;
            } else {
                this.searchProperty = searchProperty;
            }

            this.searchValue = searchValue;
            return this;
        }

        /**
         *
         * @param count The device property to count
         *
         */
        public Builder setCount(String count){
            this.countValue = count;
            return this;
        }

        /**
         *
         * @param limit The number of devices to be returned from a search. e.g. 1
         *
         */
        public Builder setLimit(int limit){
            this.limitValue = Integer.toString(limit);
            return this;
        }

        /**
         *
         * @param limit The number of devices to be returned from a search. e.g. "1"
         *
         */
        public Builder setLimit(String limit){
            this.limitValue = limit;
            return this;
        }

        /**
         *
         * @param skip The number of devices to skip before returning devices. e.g. 10
         *
         */
        public Builder setSkip(int skip){
            this.skipValue = Integer.toString(skip);
            return this;
        }

        /**
         *
         * @param skip The number of devices to skip before returning devices. e.g. "10"
         *
         */
        public Builder setSkip(String skip){
            this.skipValue = skip;
            return this;
        }

        /**
         *
         * @return The {@link UDIWrapper} being built.
         */
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
