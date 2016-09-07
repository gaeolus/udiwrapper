package udiwrapper.Device;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Headers;

public class UDIDevice extends Device {
    private String udi;
    private String di;
    private String issuingAgency;
    private String donationId;
    private String serialNumber;
    private String lotNumber;
    private Calendar manufacturingDate;
    private Calendar expirationDate;

    // The JSON response for a UDI request returns the same JSON as a DI request
    // but also returns UDI-specific information in the headers of the response
    public UDIDevice(JSONObject deviceJson, Headers headers) throws ParseException{
        super(deviceJson);
        di = headers.get("di");
        udi = headers.get("udi");
        issuingAgency = headers.get("issuing_agency");
        if (this.hasDonationIdNumber()) donationId = headers.get("donation_id");
        if (this.hasSerialNumber()) serialNumber = headers.get("serial_number");
        if (this.hasLotBatch()) lotNumber = headers.get("lot_number");

        DateFormat format = new SimpleDateFormat("y-M-d", Locale.getDefault());
        if (this.hasExpirationDate()){
            expirationDate = Calendar.getInstance();
            Date expDate = format.parse(headers.get("expiration_date"));
            expirationDate.setTime(expDate);
        }
        if (this.hasManufacturingDate()) {
            manufacturingDate = Calendar.getInstance();
            Date manDate = format.parse(headers.get("manufacturing_date"));
            manufacturingDate.setTime(manDate);
        }

    }

    /**
     * @return the Unique Device Identifier of the device
     */
    public String getUdi() {
        // the Unique Device Identifier for the device
        return udi;
    }

    /**
     * @return the Device Identifier of the device.
     */
    public String getDi() {
        // the Device Identifier for the device
        return di;
    }

    /**
     * @return the issuing agency. e.g. "ICCBBA"
     */
    public String getIssuingAgency() {
        // the issuing agency
        return issuingAgency;
    }

    /**
     * @return todo
     */
    public String getDonationId() {
        // the donation ID for the device
        return donationId;
    }

    /**
     * @return the serial number of the device
     */
    public String getSerialNumber() {
        // the serial number for the device
        return serialNumber;
    }

    /**
     * @return the lot number of the device
     */
    public String getLotNumber() {
        // the lot number for the device
        return lotNumber;
    }

    /**
     * @return the manufacturing date of the device
     */
    public Calendar getManufacturingDate() {
        // the date the device was manufactured
        return manufacturingDate;
    }

    /**
     * @return the expiration date of the device
     */
    public Calendar getExpirationDate() {
        // the date the device expires
        return expirationDate;
    }
}
