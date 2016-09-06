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

}
