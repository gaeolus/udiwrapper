package udiwrapper.Device;

import org.json.JSONException;
import org.json.JSONObject;

public class Condition {
    private String type;
    private NameValuePair high;
    private NameValuePair low;
    private String specialConditionText;

    public Condition(JSONObject storageHandlingObject, String metaType) throws JSONException {
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

    /**
     * @return the type of condition. e.g. "storageHandling"
     */
    public String getType() {
        return type;
    }

    /**
     * @return a {@link NameValuePair} of a unit and value.
     * These may be empty strings, but not null.
     */
    public NameValuePair getHigh(){
        return high;
    }

    /**
     * @return a {@link NameValuePair} of a unit and value.
     * These may be empty strings, but not null.
     */
    public NameValuePair getLow() {
        return low;
    }

    /**
     * @return the special condition text for a device which may not
     * have associated numbers. e.g. "store in a cool, dry place."
     * May be an empty string, but not null.
     */
    public String getSpecialConditionText(){
        return specialConditionText;
    }
}
