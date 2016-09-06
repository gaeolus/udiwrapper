package udiwrapper.Device;

import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.Headers;

public class UDIDevice extends Device {
    private String udi;
    private String donationId;
    private String issuingAgency;
    private String serialNumber;
    private String lotNumber;
    private Calendar manufacturingDate;
    private Calendar expirationDate;

    // The JSON response for a UDI request returns the same JSON as a DI request
    // but also returns UDI-specific information in the headers of the response
    public UDIDevice(JSONObject deviceJson, Headers headers){
        super(deviceJson);
    }

}
