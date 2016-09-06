package udiwrapper.Device;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductCode {
    private String type;
    private String productCode;
    private String productCodeName;

    public ProductCode(JSONObject productCodeObject, String type) throws JSONException {
        this.type = type;
        if (!productCodeObject.isNull("productCode")) productCode = productCodeObject.getString("productCode");
        if (!productCodeObject.isNull("productCodeName")) productCodeName = productCodeObject.getString("productCodeName");
    }

    public String getType() {
        return type;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductCodeName() {
        return productCodeName;
    }
}
