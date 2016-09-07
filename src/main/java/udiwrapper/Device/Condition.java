package udiwrapper.Device;

import org.json.JSONObject;

public class Condition {
    private String type;
    private NameValuePair high;
    private NameValuePair low;
    private String specialConditionText;

    public Condition(JSONObject storageHandlingObject, String metaType) {
        if (!storageHandlingObject.isNull(metaType + "Type")) type = storageHandlingObject.getString(metaType + "Type");
        if (!storageHandlingObject.isNull(metaType + "SpecialConditionText")) specialConditionText = storageHandlingObject.getString(metaType + "SpecialConditionText");

        if (!storageHandlingObject.isNull(metaType + "High")){
            String unit = storageHandlingObject.getJSONObject(metaType + "High").getString("unit");
            String value = storageHandlingObject.getJSONObject(metaType + "High").getString("value");
            high = new NameValuePair(unit, value);
        }
        if (!storageHandlingObject.isNull(metaType + "Low")){
            String unit = storageHandlingObject.getJSONObject(metaType + "Low").getString("unit");
            String value = storageHandlingObject.getJSONObject(metaType + "Low").getString("value");
            low = new NameValuePair(unit, value);
        }

    }

    public String getType() {
        return type;
    }

    public NameValuePair getHigh(){
        return high;
    }

    public NameValuePair getLow() {
        return low;
    }

    public String getSpecialConditionText(){
        return specialConditionText;
    }
}
