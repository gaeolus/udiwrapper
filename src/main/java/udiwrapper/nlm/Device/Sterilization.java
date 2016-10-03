package udiwrapper.nlm.Device;

import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class Sterilization {
    private boolean deviceSterile;
    private boolean sterilizePriorToUse;
    private String methodTypes;

    public Sterilization(JSONObject sterilizationObject) throws JSONException {
        if (!sterilizationObject.isNull("deviceSterile")) deviceSterile = sterilizationObject.getBoolean("deviceSterile");
        if (!sterilizationObject.isNull("sterilizationPriorToUse")) sterilizePriorToUse = sterilizationObject.getBoolean("sterilizationPriorToUse");
        if (!sterilizationObject.isNull("methodTypes")) methodTypes = sterilizationObject.getString("methodTypes");
    }

    /**
     * @return whether the device is sterile.
     */
    public boolean isDeviceSterile() {
        return deviceSterile;
    }

    /**
     * @return whether the device should be sterilized prior to use
     */
    public boolean isSterilizePriorToUse() {
        return sterilizePriorToUse;
    }

    /**
     * @return sterilization methods to achieve the required device sterilization
     */
    public String getMethodTypes() {
        return methodTypes;
    }
}
