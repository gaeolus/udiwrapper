package udiwrapper.Device;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class Device {
    // deliberately omitting GMDNTerm field because of their copyright statement
    // https://www.gmdnagency.org/Info.aspx?pageid=22

    private boolean DMExempt;
    private boolean premarketExempt;
    private boolean deviceHCTP;
    private boolean deviceKit;
    private boolean deviceCombinationProduct;
    private boolean singleUse;
    private boolean lotBatch;
    private boolean hasSerialNumber;
    private boolean hasManufacturingDate;
    private boolean hasExpirationDate;
    private boolean hasDonationIdNumber;
    private boolean labeledContainsNRL;
    private boolean labeledNoNRL;
    private boolean rx;
    private boolean otc;
    private int deviceCount;
    private String di;
    private String recordStatus;
    private String deviceDescription;
    private String MRISafetyStatus;
    private String commercialDistributionStatus;
    private String brandName;
    private String versionModelNumber;
    private String catalogNumber;
    private String companyName;
    private Calendar publishDate;
    private Calendar commercialDistributionEndDate;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<ProductCode> productCodes = new ArrayList<>();
    private ArrayList<DeviceSize> deviceSizes = new ArrayList<>();
    private ArrayList<EnvironmentalCondition> environmentalConditions = new ArrayList<>();
    private Sterilization sterilization;
    private Identifier identifiers;

    // All of the information about a Device is in the JSON response
    public Device(JSONObject deviceJson){

    }
}