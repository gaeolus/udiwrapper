package udiwrapper.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Identifier {
    private String deviceId;
    private String deviceIdType;
    private String deviceIdIssuingAgency;
    private String containsDINumber;
    private String pkgStatus;
    private Integer pkgQuantity;
    private Calendar pkgDiscontinueDate;

    public Identifier(JSONObject identifierJson) throws JSONException, ParseException {
        if (!identifierJson.isNull("deviceId")) deviceId = identifierJson.getString("deviceId");
        if (!identifierJson.isNull("deviceIdType")) deviceIdType = identifierJson.getString("deviceIdType");
        if (!identifierJson.isNull("deviceIdIssuingAgency")) deviceIdIssuingAgency = identifierJson.getString("deviceIdIssuingAgency");
        if (!identifierJson.isNull("containsDINumber")) containsDINumber = identifierJson.getString("containsDINumber");
        if (!identifierJson.isNull("pkgQuantity")) pkgQuantity = identifierJson.getInt("pkgQuantity");
        if (!identifierJson.isNull("pkgStatus")) pkgStatus = identifierJson.getString("pkgStatus");

        DateFormat format = new SimpleDateFormat("y-M-d", Locale.getDefault());
        if (!identifierJson.isNull("devicePublishDate")){
            Date pubDate = format.parse(identifierJson.getString("devicePublishDate"));
            pkgDiscontinueDate = Calendar.getInstance();
            pkgDiscontinueDate.setTime(pubDate);
        }
    }

    /**
     * @return the device ID.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @return the device ID type. e.g. "Primary"
     */
    public String getDeviceIdType() {
        return deviceIdType;
    }

    /**
     * @return the issuing agency of the Device ID. e.g. "GS1"
     */
    public String getDeviceIdIssuingAgency() {
        return deviceIdIssuingAgency;
    }

    /**
     * @return todo
     */
    public String getContainsDINumber() {
        return containsDINumber;
    }

    /**
     * @return todo
     */
    public Integer getPkgQuantity() {
        return pkgQuantity;
    }

    /**
     * @return todo
     */
    public Calendar getPkgDiscontinueDate() {
        return pkgDiscontinueDate;
    }

    /**
     * @return todo
     */
    public String getPkgStatus() {
        return pkgStatus;
    }
}
