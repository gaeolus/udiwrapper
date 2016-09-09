package udiwrapper;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import udiwrapper.Device.Device;
import udiwrapper.Device.UDIDevice;

public class UDIWrapper {
    private static final String DI_URL = "https://accessgudid.nlm.nih.gov/api/v1/devices/lookup.json?di=";
    private static final String UDI_URL = "https://accessgudid.nlm.nih.gov/api/v1/devices/lookup.json?udi=";
    private static final String ADVERSE_URL = "https://api.fda.gov/device/event.json?search=device.other_id_number.exact:";

    /**
     * @param DI the Device Identifier to be checked against. Currently,
     *           checking via Unique Device Identifier is not supported.
     * @return true if the device was found. false if the device wasn't found
     */
    public static boolean deviceExists(String DI){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DI_URL + DI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.code() == 200;
        } catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @param DI the Device Identifier. To fetch a Unique Device, see {@link #fetchUDIDevice(String)}
     * @return the {@link Device} associated with the given Device Identifier (DI)
     */
    @Nullable
    public static Device fetchDIDevice(String DI){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DI_URL + DI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200){
                JSONObject responseJson = new JSONObject(response.body().string());
                try {
                    return new Device(responseJson);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param UDI the Unique Device Identifier. To fetch a Device using the general
     *            Device Identifier, see {@link #fetchDIDevice(String)}
     * @return the {@link UDIDevice} associated with the given Unique Device Identifier (UDI)
     */
    @Nullable
    public static UDIDevice fetchUDIDevice(String UDI){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(UDI_URL + UDI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200){
                JSONObject responseJson = new JSONObject(response.body().string());
                Headers responseHeaders = response.headers();
                try {
                    return new UDIDevice(responseJson, responseHeaders);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param DI the Device Identifier. Currently checking whether an adverse event
     *           exists for a Unique Device is not supported
     * @return true if there are adverse events associated with the device. false if
     * there are none.
     */
    public static boolean adverseEventsExist(String DI) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(ADVERSE_URL + DI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.code() == 200;
        } catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }


    /**
     * @param DI the Device Identifier. Currently counting adverse events
     *           with a Unique Device is not supported
     * @return the number of adverse events associated with a Device
     */
    public static int adverseEventCount(String DI) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(ADVERSE_URL + DI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200){
                JSONObject responseJson = new JSONObject(response.body().string());
                JSONObject results = responseJson.getJSONObject("meta").getJSONObject("results");
                return results.getInt("total");
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return 0;
    }

}
