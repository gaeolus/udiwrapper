package udiwrapper.nlm.Device;

import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class DeviceSizeInformation {
    private String type;
    private NameValuePair size;
    private String sizeText;

    public DeviceSizeInformation(JSONObject deviceSizeObject) throws JSONException {
        if (!deviceSizeObject.isNull("sizeType")) type = deviceSizeObject.getString("sizeType");
        if (!deviceSizeObject.isNull("size")){
            JSONObject namevaluepair = deviceSizeObject.getJSONObject("size");
            String unit = namevaluepair.getString("unit");
            String value = namevaluepair.getString("value");
            size = new NameValuePair(unit, value);
        }
        if (!deviceSizeObject.isNull("sizeText")) sizeText = deviceSizeObject.getString("sizeText");
    }

    public String getType() {
        return type;
    }

    public NameValuePair getSize(){
        return size;
    }

    public String getSizeText(){
        return sizeText;
    }
}
