package udiwrapper.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DeviceSize {

    private String type;
    private HashMap<String, DeviceSizeInformation> sizeInformation = new HashMap<>();

    public DeviceSize(JSONArray deviceSizeObject, String type) throws JSONException {
        this.type = type;
        for (int i = 0; i < deviceSizeObject.length(); i += 1){
            JSONObject currentObject = deviceSizeObject.getJSONObject(i);
            DeviceSizeInformation information = new DeviceSizeInformation(currentObject);
            sizeInformation.put(information.getType(), information);
        }
    }

    public String getType() {
        return type;
    }

    public HashMap<String, DeviceSizeInformation> getSizeInformation() {
        return sizeInformation;
    }
}
