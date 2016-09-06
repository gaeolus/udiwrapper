package udiwrapper.Device;

import org.json.JSONObject;

import java.util.Calendar;

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
    public UDIDevice(JSONObject deviceJson, Headers headers){
        super(deviceJson);
        di = headers.get("di");
        udi = headers.get("udi");
        issuingAgency = headers.get("issuing_agency");
        if (this.hasDonationIdNumber()) donationId = headers.get("donation_id");
        if (this.hasSerialNumber()) serialNumber = headers.get("serial_number");
        if (this.hasLotBatch()) lotNumber = headers.get("lot_number");

        if (this.hasExpirationDate()){
            expirationDate = Calendar.getInstance();
            expirationDate.setTime(headers.getDate("expiration_date"));
        }
        if (this.hasManufacturingDate()){
            manufacturingDate = Calendar.getInstance();
            manufacturingDate.setTime(headers.getDate("manufacturing_date"));
        }

    }

    public String getUdi() {
        // the Unique Device Identifier for the device
        return udi;
    }

    public String getDi() {
        // the Device Identifier for the device
        return di;
    }

    public String getIssuingAgency() {
        // the issuing agency
        return issuingAgency;
    }

    public String getDonationId() {
        // the donation ID for the device
        return donationId;
    }

    public String getSerialNumber() {
        // the serial number for the device
        return serialNumber;
    }

    public String getLotNumber() {
        // the lot number for the device
        return lotNumber;
    }

    public Calendar getManufacturingDate() {
        // the date the device was manufactured
        return manufacturingDate;
    }

    public Calendar getExpirationDate() {
        // the date the device expires
        return expirationDate;
    }
}
