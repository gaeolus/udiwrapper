package udiwrapper.Device;

import org.json.JSONException;
import org.json.JSONObject;

public class Sterilization {
    private boolean deviceSterile;
    private boolean sterilizePriorToUse;
    private String methodTypes;

    public Sterilization(JSONObject sterilizationObject){
        try {
            if (!sterilizationObject.isNull("deviceSterile")) deviceSterile = sterilizationObject.getBoolean("deviceSterile");
            if (!sterilizationObject.isNull("sterilizationPriorToUse")) sterilizePriorToUse = sterilizationObject.getBoolean("sterilizationPriorToUse");
            if (!sterilizationObject.isNull("methodTypes")) methodTypes = sterilizationObject.getString("methodTypes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isDeviceSterile() {
        return deviceSterile;
    }

    public boolean isSterilizePriorToUse() {
        return sterilizePriorToUse;
    }

    public String getMethodTypes() {
        return methodTypes;
    }
}
