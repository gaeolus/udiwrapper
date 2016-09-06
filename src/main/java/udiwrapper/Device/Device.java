package udiwrapper.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class Device {
    // deliberately omitting GMDNTerm field because of their copyright statement
    // https://www.gmdnagency.org/Info.aspx?pageid=22

    private boolean isDMExempt;
    private boolean isPremarketExempt;
    private boolean hasDeviceHCTP;
    private boolean hasDeviceKit;
    private boolean isDeviceCombinationProduct;
    private boolean isSingleUse;
    private boolean hasLotBatch;
    private boolean hasSerialNumber;
    private boolean hasManufacturingDate;
    private boolean hasExpirationDate;
    private boolean hasDonationIdNumber;
    private boolean labeledContainsNRL;
    private boolean labeledNoNRL;
    private boolean requiresRX;
    private boolean requiresOTC;
    private int deviceCount;
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
    private Sterilization sterilization;
    private Identifier identifiers;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<ProductCode> productCodes = new ArrayList<>();
    private ArrayList<DeviceSize> deviceSizes = new ArrayList<>();
    private ArrayList<EnvironmentalCondition> environmentalConditions = new ArrayList<>();

    // All of the information about a Device is in the JSON response
    public Device(JSONObject deviceJson){
        try {
            // get the relevant JSON object
            JSONObject device = deviceJson.getJSONObject("gudid").getJSONObject("device");

            // set the booleans first
            isDMExempt = device.getBoolean("DMExempt");
            isPremarketExempt = device.getBoolean("premarketExempt");
            hasDeviceHCTP = device.getBoolean("deviceHCTP");
            hasDeviceKit = device.getBoolean("deviceKit");
            isDeviceCombinationProduct = device.getBoolean("deviceCombinationProduct");
            isSingleUse = device.getBoolean("singleUse");
            hasLotBatch = device.getBoolean("lotBatch");
            hasSerialNumber = device.getBoolean("serialNumber");
            hasManufacturingDate = device.getBoolean("manufacturingDate");
            hasExpirationDate = device.getBoolean("expirationDate");
            hasDonationIdNumber = device.getBoolean("donationIdNumber");
            labeledContainsNRL = device.getBoolean("labeledContainsNRL");
            labeledNoNRL = device.getBoolean("labeledNoNRL");
            requiresRX = device.getBoolean("rx");
            requiresOTC = device.getBoolean("otc");

            // get the ints
            deviceCount = device.getInt("deviceCount");

            // get the strings
            recordStatus = device.getString("deviceRecordStatus");
            commercialDistributionStatus = device.getString("deviceCommDistributionStatus");
            brandName = device.getString("brandName");
            versionModelNumber = device.getString("versionModelNumber");
            catalogNumber = device.getString("catalogNumber");
            companyName = device.getString("companyName");
            deviceDescription = device.getString("deviceDescription");
            MRISafetyStatus = device.getString("MRISafetyStatus");

            // turn the strings holding dates into Calendar Objects
            try {
                DateFormat format = new SimpleDateFormat("y-M-D", Locale.getDefault());
                Date pubDate = format.parse(device.getString("devicePublishDate"));
                Date distDate = format.parse(device.getString("deviceCommDistributinoEndDate"));

                publishDate = Calendar.getInstance();
                commercialDistributionEndDate = (Calendar) publishDate.clone();

                publishDate.setTime(pubDate);
                commercialDistributionEndDate.setTime(distDate);
            } catch (ParseException e){
                e.printStackTrace();
            }

            // set objects
            sterilization = new Sterilization(device.getJSONObject("sterilization"));
            identifiers = new Identifier(device.getJSONObject("identifiers").getJSONObject("identifier"));

            //create array lists with objects
            JSONObject contactsObject = device.getJSONObject("contacts");
            Iterator<?> contactsKeys = contactsObject.keys();
            while (contactsKeys.hasNext()){
                String key = (String) contactsKeys.next();
                Contact currentContact = new Contact(contactsObject.getJSONObject(key), key);
                contacts.add(currentContact);
            }

            JSONObject productCodeObject = device.getJSONObject("productCodes");
            Iterator<?> productCodeKeys = productCodeObject.keys();
            while (productCodeKeys.hasNext()){
                String key = (String) productCodeKeys.next();
                ProductCode currentProductCode = new ProductCode(productCodeObject.getJSONObject(key), key);
                productCodes.add(currentProductCode);
            }

            JSONObject deviceSizeObject = device.getJSONObject("deviceSizes");
            Iterator<?> deviceSizeKeys = deviceSizeObject.keys();
            while (deviceSizeKeys.hasNext()){
                String key = (String) deviceSizeKeys.next();
                DeviceSize currentDeviceSize = new DeviceSize(deviceSizeObject.getJSONObject(key), key);
                deviceSizes.add(currentDeviceSize);
            }

            JSONObject environmentalConditionsObject = device.getJSONObject("environmentalConditions");
            Iterator<?> environmentalConditionKeys = environmentalConditionsObject.keys();
            while (environmentalConditionKeys.hasNext()){
                String key = (String) environmentalConditionKeys.next();
                EnvironmentalCondition currentEnvironmentalCondition =
                        new EnvironmentalCondition(environmentalConditionsObject.getJSONObject(key), key);
                environmentalConditions.add(currentEnvironmentalCondition);
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}