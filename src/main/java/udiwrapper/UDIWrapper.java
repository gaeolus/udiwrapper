package udiwrapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import udiwrapper.Device.Device;
import udiwrapper.Device.UDIDevice;

public class UDIWrapper {
    private static final String DI_URL = "https://accessgudid.nlm.nih.gov/api/v1/devices/lookup.json?di=";
    private static final String UDI_URL = "https://accessgudid.nlm.nih.gov/api/v1/devices/lookup.json?udi=";

    // to get whether or not the device exists, the DI must be used
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

    // fetch the Device information with a DI. Returns the Device if the DI is valid
    // and null if the DI doesn't exist
    public static Device fetchDIDevice(String DI){
        return null;
    }

    // fetch the Device information with a DI. Returns the Device if the DI is valid
    // and null if the DI doesn't exist
    public static UDIDevice fetchUDIDevice(String UDI){
        return null;
    }

}
