package udiwrapper.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/**
 * The wrapper class for the JSON returned by the
 * {@see <a href="https://nlm.nih.gov/" target="_blank" rel="noopener">
 * National Library of Medicine</a>}'s
 * {@see <a href="https://accessgudid.nlm.nih.gov/resources/developers/device_lookup_api" target="_blank" rel="noopener">
 * device_lookup API.
 * </a>}
 */
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
    private Integer deviceCount;
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
    private HashMap<String, Contact> contacts;
    private HashMap<String, ProductCode> productCodes;
    private HashMap<String, DeviceSize> deviceSizes;
    private HashMap<String, EnvironmentalCondition> environmentalConditions;

    // All of the information about a Device is in the JSON response

    /**
     * @param deviceJson JSON returned by the NLM's
     *          {@see <a href="https://accessgudid.nlm.nih.gov/resources/developers/device_lookup_api" target="_blank" rel="noopener">
     *              device_lookup API
     *          </a>}
     * @throws JSONException
     * @throws ParseException
     */
    public Device(JSONObject deviceJson) throws JSONException, ParseException {
        // get the relevant JSON object
        JSONObject device = deviceJson.getJSONObject("gudid").getJSONObject("device");

        // set the booleans first
        if (!device.isNull("DMExempt")) isDMExempt = device.getBoolean("DMExempt");
        if (!device.isNull("premarketExempt")) isPremarketExempt = device.getBoolean("premarketExempt");
        if (!device.isNull("deviceHCTP")) isDeviceHCTP = device.getBoolean("deviceHCTP");
        if (!device.isNull("deviceKit")) isDeviceKit = device.getBoolean("deviceKit");
        if (!device.isNull("deviceCombinationProduct")) isDeviceCombinationProduct = device.getBoolean("deviceCombinationProduct");
        if (!device.isNull("singleUse")) isSingleUse = device.getBoolean("singleUse");
        if (!device.isNull("lotBatch")) hasLotBatch = device.getBoolean("lotBatch");
        if (!device.isNull("serialNumber")) hasSerialNumber = device.getBoolean("serialNumber");
        if (!device.isNull("manufacturingDate")) hasManufacturingDate = device.getBoolean("manufacturingDate");
        if (!device.isNull("expirationDate")) hasExpirationDate = device.getBoolean("expirationDate");
        if (!device.isNull("donationIdNumber")) hasDonationIdNumber = device.getBoolean("donationIdNumber");
        if (!device.isNull("labeledContainsNRL")) labeledContainsNRL = device.getBoolean("labeledContainsNRL");
        if (!device.isNull("labeledNoNRL")) labeledNoNRL = device.getBoolean("labeledNoNRL");
        if (!device.isNull("rx")) requiresRX = device.getBoolean("rx");
        if (!device.isNull("otc")) isOTC = device.getBoolean("otc");

        // get the ints
        if (!device.isNull("deviceCount")) deviceCount = device.getInt("deviceCount");

        // get the strings
        if (!device.isNull("deviceRecordStatus")) recordStatus = device.getString("deviceRecordStatus");
        if (!device.isNull("deviceCommDistributionStatus")) commercialDistributionStatus = device.getString("deviceCommDistributionStatus");
        if (!device.isNull("brandName")) brandName = device.getString("brandName");
        if (!device.isNull("versionModelNumber")) versionModelNumber = device.getString("versionModelNumber");
        if (!device.isNull("catalogNumber")) catalogNumber = device.getString("catalogNumber");
        if (!device.isNull("companyName")) companyName = device.getString("companyName");
        if (!device.isNull("deviceDescription")) deviceDescription = device.getString("deviceDescription");
        if (!device.isNull("MRISafetyStatus")) MRISafetyStatus = device.getString("MRISafetyStatus");

        // turn the strings holding dates into Calendar Objects
        DateFormat format = new SimpleDateFormat("y-M-D", Locale.getDefault());
        if (!device.isNull("devicePublishDate")){
            Date pubDate = format.parse(device.getString("devicePublishDate"));
            publishDate = Calendar.getInstance();
            publishDate.setTime(pubDate);
        }
        if (!device.isNull("deviceCommDistributionEndDate")){
            Date distDate = format.parse(device.getString("deviceCommDistributionEndDate"));
            commercialDistributionEndDate = Calendar.getInstance();
            commercialDistributionEndDate.setTime(distDate);
        }

        // set objects
        if (!device.isNull("sterilization")) sterilization = new Sterilization(device.getJSONObject("sterilization"));
        if (!device.isNull("identifiers")){
            if (!device.getJSONObject("identifiers").isNull("identifier")) {
                identifiers = new Identifier(device.getJSONObject("identifiers").getJSONObject("identifier"));
            }
        }

        //create array lists with objects
        if (!device.isNull("contacts")){
            contacts = new HashMap<>();
            JSONObject contactsObject = device.getJSONObject("contacts");
            Iterator<?> contactsKeys = contactsObject.keys();
            while (contactsKeys.hasNext()){
                String key = (String) contactsKeys.next();
                Contact currentContact = new Contact(contactsObject.getJSONObject(key), key);
                contacts.put(key, currentContact);
            }
        }

        if (!device.isNull("productCodes")){
            productCodes = new HashMap<>();
            JSONObject productCodeObject = device.getJSONObject("productCodes");
            Iterator<?> productCodeKeys = productCodeObject.keys();
            while (productCodeKeys.hasNext()){
                String key = (String) productCodeKeys.next();
                ProductCode currentProductCode = new ProductCode(productCodeObject.getJSONObject(key), key);
                productCodes.put(key, currentProductCode);
            }
        }

        if (!device.isNull("deviceSizes")){
            deviceSizes = new HashMap<>();
            JSONObject deviceSizeObject = device.getJSONObject("deviceSizes");
            Iterator<?> deviceSizeKeys = deviceSizeObject.keys();
            while (deviceSizeKeys.hasNext()){
                String key = (String) deviceSizeKeys.next();
                DeviceSize currentDeviceSize = new DeviceSize(deviceSizeObject.getJSONObject(key), key);
                deviceSizes.put(key, currentDeviceSize);
            }
        }

        if (!device.isNull("environmentalConditions")){
            environmentalConditions = new HashMap<>();
            JSONObject environmentalConditionsObject = device.getJSONObject("environmentalConditions");
            Iterator<?> environmentalConditionKeys = environmentalConditionsObject.keys();
            while (environmentalConditionKeys.hasNext()){
                String key = (String) environmentalConditionKeys.next();
                EnvironmentalCondition currentEnvironmentalCondition =
                        new EnvironmentalCondition(environmentalConditionsObject.getJSONArray(key), key);
                environmentalConditions.put(key, currentEnvironmentalCondition);
            }
        }
    }

    // TODO verify that comments are accurate

    /**
     * @return whether the Device is exempt from affixing a UDI permanently on the device itself
     */
    public boolean isDMExempt() {
        return isDMExempt;
    }

    /**
     * @return whether the device is exempt from pre-market approval
     */
    public boolean isPremarketExempt() {
        return isPremarketExempt;
    }

    /**
     * @return whether the device is a Human Cellular and Tissue-based Product
     */
    public boolean isDeviceHCTP() {
        return isDeviceHCTP;
    }

    /**
     * @return whether the device is part of a kit
     */
    public boolean isDeviceKit() {
        return isDeviceKit;
    }

    /**
     * @return whether the device is intended to be used in combination with other devices
     */
    public boolean isDeviceCombinationProduct() {
        return isDeviceCombinationProduct;
    }

    /**
     * @return whether the device is single use
     */
    public boolean isSingleUse() {
        return isSingleUse;
    }

    /**
     * @return whether the device has a lot batch number
     */
    public boolean hasLotBatch() {
        return hasLotBatch;
    }

    /**
     * @return whether the device has a serial number
     */
    public boolean hasSerialNumber() {
        return hasSerialNumber;
    }

    /**
     * @return whether the device has a serial number
     */
    public boolean hasManufacturingDate() {
        return hasManufacturingDate;
    }

    /**
     * @return whether the device has an expiration date
     */
    public boolean hasExpirationDate() {
        return hasExpirationDate;
    }

    /**
     * @return whether the device has a Donation ID number
     */
    public boolean hasDonationIdNumber() {
        return hasDonationIdNumber;
    }

    /**
     * @return whether the device contains Natural Rubber Latex
     */
    public boolean isLabeledContainsNRL() {
        return labeledContainsNRL;
    }

    /**
     * @return whether the device is labeled as not having Natural Rubber Latex
     */
    public boolean isLabeledNoNRL() {
        return labeledNoNRL;
    }

    /**
     * @return whether the device requires a prescription for use
     */
    public boolean requiresRX() {
        return requiresRX;
    }

    /**
     * @return whether the device is an Over-the-Counter product
     */
    public boolean isOTC() {
        return isOTC;
    }

    /**
     * @return todo
     */
    public Integer getDeviceCount() {
        return deviceCount;
    }

    /**
     * @return todo
     */
    public String getRecordStatus() {
        return recordStatus;
    }

    /**
     * @return a description of the device
     */
    public String getDeviceDescription() {
        return deviceDescription;
    }

    /**
     * @return the MRI Safety Status of the device
     */
    public String getMRISafetyStatus() {
        return MRISafetyStatus;
    }

    /**
     * @return the commercial distribution status of the device
     */
    public String getCommercialDistributionStatus() {
        return commercialDistributionStatus;
    }

    /**
     * @return the brand name of the device
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @return the version model number of the device
     */
    public String getVersionModelNumber() {
        return versionModelNumber;
    }

    /**
     * @return the catalog number of the device
     */
    public String getCatalogNumber() {
        return catalogNumber;
    }

    /**
     * @return the name of the company that manufactures the device
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @return the date the device information was published
     */
    public Calendar getPublishDate() {
        return publishDate;
    }

    /**
     * @return the date that commercial distribution is set to end
     */
    public Calendar getCommercialDistributionEndDate() {
        return commercialDistributionEndDate;
    }

    /**
     * @return information regarding {@link Sterilization} of the device.
     */
    public Sterilization getSterilization() {
        return sterilization;
    }

    /**
     * @return various {@link Identifier}s of the device including the DI.
     */
    public Identifier getIdentifiers() {
        return identifiers;
    }

    /**
     * @return a list of {@link Contact} information for the manufacturer of the device.
     */
    public HashMap<String, Contact> getContacts() {
        return contacts;
    }

    /**
     * @return a list of the device's {@link ProductCode}s.
     */
    public HashMap<String, ProductCode> getProductCodes() {
        return productCodes;
    }

    /**
     * @return a list of {@link DeviceSize}s
     */
    public HashMap<String, DeviceSize> getDeviceSizes() {
        return deviceSizes;
    }

    /**
     * @return a list of different environmental conditions for the device,
     * such as storage handling or any other special environmental information.
     * See {@link EnvironmentalCondition}
     */
    public HashMap<String, EnvironmentalCondition> getEnvironmentalConditions() {
        return environmentalConditions;
    }
}