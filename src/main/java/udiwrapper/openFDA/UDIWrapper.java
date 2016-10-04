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
    public enum DeviceProperties{
        BRAND_NAME ("brand_name"),
        BRAND_NAME_EXACT ("brand_name.exact"),
        CATALOG_NUMBER ("catalog_number"),
        CATALOG_NUMBER_EXACT ("catalog_number"),
        COMMERCIAL_DISTRIBUTION_DATE ("commercial_distribution_end_date"),
        COMMERCIAL_DISTRIBUTION_STATUS ("commercial_distribution_status"),
        COMMERCIAL_DISTRIBUTION_STATUS_EXACT ("commercial_distribution_status.exact"),
        COMPANY_NAME ("company_name"),
        COMPANY_NAME_EXACT ("company_name.exact"),
        DEVICE_COUNT_IN_BASE_PACKAGE ("device_count_in_base_package"),
        DEVICE_DESCRIPTION ("device_description"),
        HAS_DONATION_ID_NUMBER ("has_donation_id_number"),
        HAS_EXPIRATION_DATE ("has_expiration_date"),
        HAS_LOT_OR_BATCH_NUMBER ("has_lot_or_batch_number"),
        HAS_MANUFACTURING_DATE ("has_manufacturing_date"),
        HAS_SERIAL_NUMBER ("has_serial_date"),
        IS_COMBINATION_PRODUCT ("is_combination_product"),
        IS_DIRECT_MARKING_EXEMPT ("is_direct_marking_exempt"),
        IS_HCTP ("is_hct_p"),
        IS_KIT ("is_kit"),
        IS_LABELED_AS_NO_NRL ("is_labeled_as_no_nrl"),
        IS_LABELED_AS_NRL ("is_labeled_as_nrl"),
        IS_OTC ("is_otc"),
        IS_PM_EXEMPT ("is_pm_exempt"),
        IS_RX ("is_rx"),
        IS_SINGLE_USE ("is_single_use"),
        MRI_SAFETY ("mri_safety"),
        MRI_SAFETY_EXACT ("mri_safety.exact"),
        PUBLISH_DATE ("publish_date"),
        RECORD_STATUS ("record_status"),
        IS_STERILE ("sterilization.is_sterile"),
        IS_STERILIZATION_PRIOR_USE ("sterilization.is_sterilization_prior_use"),
        STERILIZATION_METHODS ("sterilization.sterilization_methods"),
        PRODUCT_CODE ("product_codes.code"),
        PRODUCT_CODE_EXACT ("product_codes.code.exact"),
        PRODUCT_CODE_NAME ("product_codes.name"),
        VERSION_OR_MODEL_NUMBER ("version_or_model_number"),
        VERSION_OR_MODEL_NUMBER_EXACT ("version_or_model_number.exact"),
        IDENTIFIER ("identifiers.id"),
        IDENTIFIER_ISSUING_AGENCY ("identifiers.issuing_agency"),
        PACKAGE_DISCONTINUE_DATE ("identifiers.package_discontinue_date"),
        PACKAGE_STATUS ("identifiers.package_status"),
        PACKAGE_STATUS_EXACT ("identifiers.package_status.exact"),
        QUANTITY_PER_PACKAGE ("identifiers.quantity_per_package"),
        IDENTIFIER_TYPE ("identifiers.type"),
        UNIT_OF_USE_ID ("identifiers.unit_of_use_id"),
        CONTACT_EMAIL ("customer_contacts.email"),
        CONTACT_PHONE ("customer_contacts.phone"),
        DEVICE_SIZE_TEXT ("device_sizes.text"),
        DEVICE_SIZE_TYPE ("device_sizes.type"),
        DEVICE_SIZE_TYPE_EXACT ("device_sizes.type.exact"),
        DEVICE_SIZE_VALUE ("device_sizes.value"),
        DEVICE_SIZES_UNIT ("device_sizes.unit"),
        STORAGE_HIGH_VALUE ("storage.high.value"),
        STORAGE_HIGH_UNIT ("storage.high.unit"),
        STORAGE_LOW_VALUE ("storage.low.value"),
        STORAGE_LOW_UNIT ("storage.low.unit"),
        STORAGE_SPECIAL_CONDITIONS ("storage.special_conditions"),
        STORAGE_TYPE ("storage.type"),
        STORAGE_TYPE_EXACT ("storage.type.exact"),
        OPENFDA_DEVICE_CLASS ("device_class"),
        OPENFDA_DEVICE_NAME ("device_name"),
        OPENFDA_DEVICE_NAME_EXACT ("device_name.exact"),
        OPENFDA_MEDICAL_SPECIALTY_DESCRIPTION ("medical_specialty_description"),
        OPENFDA_MEDICAL_SPECIALTY_DESCRIPTION_EXACT ("medical_specialty_description.exact"),
        OPENFDA_REGULATION_NUMBER ("regulation_number"),
        OPENFDA_REGULATION_NUMBER_EXACT ("regulation_number.exact"),


        ;
        private final String constant;

        private DeviceProperties(String constant){
            this.constant = constant;
        }

        @Override
        public String toString() {
            return this.constant;
        }
    }
    private String apiKey;
    private DeviceProperties searchProperty;
    private String searchValue;
    private String limitValue;
    private String skipValue;

    private boolean searchExists;
    private int total;
    private Map<String, Device> devices;

    private UDIWrapper(String apiKey,
                       DeviceProperties searchProperty,
                       String searchValue,
                       String limitValue,
                       String skipValue){

        this.apiKey = apiKey;
        this.searchProperty = searchProperty;
        this.searchValue = searchValue;
        this.limitValue = limitValue;
        this.skipValue = skipValue;

        performSearch();

    }

    public void alterSearch(DeviceProperties searchProperty,
                            String searchValue,
                            String limitValue,
                            String skipValue){
        if (searchProperty != null) this.searchProperty = searchProperty;
        if (searchValue != null) this.searchValue = searchValue;
        if (limitValue != null) this.limitValue = limitValue;
        if (skipValue != null) this.skipValue = skipValue;

        performSearch();
    }

    private void performSearch(){
        String SEARCH = "search";
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
            if (searchExists) {
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
        private DeviceProperties searchProperty;
        private String searchValue;
        private String limitValue;
        private String skipValue;

        // default constants
        private final DeviceProperties DEFAULT_SEARCH_PROPERTY = DeviceProperties.IDENTIFIER;
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
        public Builder setSearch(DeviceProperties searchProperty, String searchValue){
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
            if (limitValue == null){
                limitValue = DEFAULT_LIMIT;
            }
            if (skipValue == null){
                skipValue = DEFAULT_SKIP;
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
                    limitValue,
                    skipValue);
        }
    }
}
