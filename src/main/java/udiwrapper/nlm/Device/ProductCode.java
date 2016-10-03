package udiwrapper.nlm.Device;

import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class ProductCode {
    private String type;
    private String productCode;
    private String productCodeName;

    public ProductCode(JSONObject productCodeObject, String type) throws JSONException {
        this.type = type;
        if (!productCodeObject.isNull("productCode")) productCode = productCodeObject.getString("productCode");
        if (!productCodeObject.isNull("productCodeName")) productCodeName = productCodeObject.getString("productCodeName");
    }

    /**
     * @return the type of product code. e.g. "fdaProductCode"
     */
    public String getType() {
        return type;
    }

    /**
     * @return the product code. e.g. "NIQ"
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * @return the product code's name. e.g. "Coronary drug-eluting stent"
     */
    public String getProductCodeName() {
        return productCodeName;
    }
}
