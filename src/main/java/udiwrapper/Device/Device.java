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
    private boolean isDeviceHCTP;
    private boolean isDeviceKit;
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
    private boolean isOTC;
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
            isDeviceHCTP = device.getBoolean("deviceHCTP");
            isDeviceKit = device.getBoolean("deviceKit");
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
            isOTC = device.getBoolean("otc");

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
                Date distDate = format.parse(device.getString("deviceCommDistributionEndDate"));

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

    // TODO verify that comments are accurate

    public boolean isDMExempt() {
        // whether the Device is exempt from affixing a UDI permanently on the device itself
        return isDMExempt;
    }

    public boolean isPremarketExempt() {
        // whether the Device is exempt from pre-market approval
        return isPremarketExempt;
    }

    public boolean isDeviceHCTP() {
        // whether the device is a Human Cellular and Tissue-based Product
        return isDeviceHCTP;
    }

    public boolean isDeviceKit() {
        // whether the device is a kit
        return isDeviceKit;
    }

    public boolean isDeviceCombinationProduct() {
        // whether or not the device is intended to be used in combination with other devices
        return isDeviceCombinationProduct;
    }

    public boolean isSingleUse() {
        // whether the device is single use
        return isSingleUse;
    }

    public boolean hasLotBatch() {
        // whether the device has a lot batch number
        return hasLotBatch;
    }

    public boolean hasSerialNumber() {
        // whether the device has a serial number
        return hasSerialNumber;
    }

    public boolean hasManufacturingDate() {
        // whether the device has a serial number
        return hasManufacturingDate;
    }

    public boolean hasExpirationDate() {
        // whether the device has an expiration date
        return hasExpirationDate;
    }

    public boolean hasDonationIdNumber() {
        // whether the device has a Donation ID number
        return hasDonationIdNumber;
    }

    public boolean isLabeledContainsNRL() {
        // whether the device contains Natural Rubber Latex
        return labeledContainsNRL;
    }

    public boolean isLabeledNoNRL() {
        // whether the device is labeled as not having Natural Rubber Latex
        return labeledNoNRL;
    }

    public boolean requiresRX() {
        // whether the device requires a prescription for use
        return requiresRX;
    }

    public boolean isOTC() {
        // whether the device is an Over-the-Counter product
        return isOTC;
    }

    public int getDeviceCount() {
        // TODO
        return deviceCount;
    }

    public String getRecordStatus() {
        // TODO
        return recordStatus;
    }

    public String getDeviceDescription() {
        // device description
        return deviceDescription;
    }

    public String getMRISafetyStatus() {
        // the MRI Safety Status of the device
        return MRISafetyStatus;
    }

    public String getCommercialDistributionStatus() {
        // the commercial distribution status of the device
        return commercialDistributionStatus;
    }

    public String getBrandName() {
        // the brand name of the device
        return brandName;
    }

    public String getVersionModelNumber() {
        // the version model number of the device
        return versionModelNumber;
    }

    public String getCatalogNumber() {
        // the catalog number of the device
        return catalogNumber;
    }

    public String getCompanyName() {
        // the name of the company that manufactures the device
        return companyName;
    }

    public Calendar getPublishDate() {
        // the date the device information was published
        return publishDate;
    }

    public Calendar getCommercialDistributionEndDate() {
        // the date that commercial distribution is set to end
        return commercialDistributionEndDate;
    }

    public Sterilization getSterilization() {
        // information regarding sterilization of the device
        // See @Sterilization
        return sterilization;
    }

    public Identifier getIdentifiers() {
        // various identifiers of the device including the DI
        return identifiers;
    }

    public ArrayList<Contact> getContacts() {
        // a list contact information for the manufacturer of the device
        return contacts;
    }

    public ArrayList<ProductCode> getProductCodes() {
        // a list of the device's product codes
        return productCodes;
    }

    public ArrayList<DeviceSize> getDeviceSizes() {
        // a list of device sizes
        return deviceSizes;
    }

    public ArrayList<EnvironmentalCondition> getEnvironmentalConditions() {
        // a list of different environmental conditions for the device, such as storage handling
        // or any other special environmental information
        return environmentalConditions;
    }
}