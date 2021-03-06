package udiwrapper.openFDA.Device;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Device {
    private boolean hasDonationId;
    private boolean isPrescription;
    private boolean isLabeledAsNRL;
    private boolean hasSerialNumber;
    private boolean isDirectMarkingExempt;
    private boolean isLabeledAsNoNRL;
    private boolean isSingleUse;
    private boolean isOverTheCounter;
    private boolean hasManufacturingDate;
    private boolean isCombinationProduct;
    private boolean isKit;
    private boolean isPMExempt;
    private boolean hasLotOrBatchNumber;
    private boolean hasExpirationDate;
    private boolean isHCTP;
    private boolean isSterilizationPriorToUse;
    private boolean isSterile;
    private int countInBasePackage;
    private String MRISafety;
    private String recordStatus;
    private String commercialDistributionStatus;
    private String description;
    private String versionOrModelNumber;
    private String brandName;
    private String catalogNumber;
    private String companyName;
    private Calendar publishDate;
    private List<String> contactEmails;
    private List<String> contactPhones;
    private Map<String, Identifier> identifiers;
    private Map<String, ProductCode> productCodes;
    private Map<String, DeviceSize> deviceSizes;
    private Map<String, Storage> deviceStorages;

    public Device(JSONObject deviceJSON){
        hasDonationId = getJSONBoolean("has_donation_id_number", deviceJSON);
        isPrescription = getJSONBoolean("is_rx", deviceJSON);
        isLabeledAsNRL = getJSONBoolean("is_labeled_as_nrl", deviceJSON);
        hasSerialNumber = getJSONBoolean("has_serial_number", deviceJSON);
        isDirectMarkingExempt = getJSONBoolean("is_direct_marking_exempt", deviceJSON);
        isLabeledAsNoNRL = getJSONBoolean("is_labeled_as_no_nrl", deviceJSON);
        isSingleUse = getJSONBoolean("is_single_use", deviceJSON);
        isOverTheCounter = getJSONBoolean("is_otc", deviceJSON);
        hasManufacturingDate = getJSONBoolean("has_manufacturing_date", deviceJSON);
        isCombinationProduct = getJSONBoolean("is_combinationProduct", deviceJSON);
        isKit = getJSONBoolean("is_kit", deviceJSON);
        isPMExempt = getJSONBoolean("is_pm_exempt", deviceJSON);
        hasLotOrBatchNumber = getJSONBoolean("has_lot_or_batch_number", deviceJSON);
        hasExpirationDate = getJSONBoolean("has_expiration_date", deviceJSON);
        isHCTP = getJSONBoolean("is_hct_p", deviceJSON);

        JSONObject sterilizationObject = deviceJSON.getJSONObject("sterilization");
        isSterilizationPriorToUse =
                getJSONBoolean("is_sterilization_prior_to_use", sterilizationObject);
        isSterile = getJSONBoolean("is_sterile", sterilizationObject);

        countInBasePackage = getJSONInt("device_count_in_base_package", deviceJSON);
        MRISafety = getJSONString("mri_safety", deviceJSON);
        recordStatus = getJSONString("record_status", deviceJSON);
        commercialDistributionStatus = getJSONString("commercial_distribution_status", deviceJSON);
        description = getJSONString("device_description", deviceJSON);
        versionOrModelNumber = getJSONString("version_or_model_number", deviceJSON);
        brandName = getJSONString("brand_name", deviceJSON);
        catalogNumber = getJSONString("catalog_number", deviceJSON);
        companyName = getJSONString("company_name", deviceJSON);

        publishDate = Calendar.getInstance();
        String devicePublishDate = getJSONString("publish_date", deviceJSON);
        SimpleDateFormat format = new SimpleDateFormat("y-M-d", Locale.getDefault());
        try {
            publishDate.setTime(format.parse(devicePublishDate));
        } catch (ParseException e){
            e.printStackTrace();
        }

        identifiers = new HashMap<>();
        JSONArray identifiersArray = getJSONArray("identifiers", deviceJSON);
        if (identifiersArray != null){
            for (int i = 0; i < identifiersArray.length() ; i += 1){
                JSONObject currentIdentifier = identifiersArray.getJSONObject(i);
                String type = getJSONString("type", currentIdentifier);
                String issuingAgency = getJSONString("issuing_agency", currentIdentifier);
                String id = getJSONString("id", currentIdentifier);
                String packageStatus = getJSONString("package_status", currentIdentifier);
                String unitOfUseId = getJSONString("unit_of_use_id", currentIdentifier);
                int quantityPerPackage = getJSONInt("quantity_per_package", currentIdentifier);

                Identifier thisIdentifier = new Identifier(type, issuingAgency, id);
                if (packageStatus != null) thisIdentifier.setPackageStatus(packageStatus);
                if (unitOfUseId != null) thisIdentifier.setUnitOfUseId(unitOfUseId);
                if (quantityPerPackage != 0) thisIdentifier.setQuantityPerPackage(quantityPerPackage);

                identifiers.put(thisIdentifier.getType(), thisIdentifier);
            }
        }

        productCodes = new HashMap<>();
        JSONArray productCodeArray = getJSONArray("product_codes", deviceJSON);
        if (productCodeArray != null){
            for (int i = 0; i < productCodeArray.length() ; i += 1){
                JSONObject currentProductCode = productCodeArray.getJSONObject(i);
                String code = getJSONString("code", currentProductCode);
                String name = getJSONString("name", currentProductCode);
                JSONObject openFDAObject = currentProductCode.getJSONObject("openfda");
                String openFDAName = getJSONString("device_name", openFDAObject);
                String openFDASpecialty = getJSONString("medical_specialty_description", openFDAObject);
                String openFDADeviceClass = getJSONString("device_class", openFDAObject);
                String openFDARegulationNumber = getJSONString("regulation_number", openFDAObject);

                ProductCode thisProductCode = new ProductCode(code, name);
                if (openFDAName != null) thisProductCode.setOpenFDAName(openFDAName);
                if (openFDASpecialty != null) thisProductCode.setOpenFDASpecialtyDescription(openFDASpecialty);
                if (openFDADeviceClass != null) thisProductCode.setOpenFDADeviceClass(openFDADeviceClass);
                if (openFDARegulationNumber != null) thisProductCode.setOpenFDARegulationNumber(openFDARegulationNumber);

                productCodes.put(thisProductCode.getCode(), thisProductCode);
            }
        }

        contactEmails = new ArrayList<>();
        contactPhones = new ArrayList<>();
        JSONArray contacts = getJSONArray("customer_contacts", deviceJSON);
        if (contacts != null){
            for (int i = 0; i < contacts.length(); i += 1){
                JSONObject currentContactInfo = contacts.getJSONObject(i);
                String thisPhone = getJSONString("phone", currentContactInfo);
                String thisEmail = getJSONString("email", currentContactInfo);
                contactEmails.add(thisEmail);
                contactPhones.add(thisPhone);
            }
        }

        deviceSizes = new HashMap<>();
        JSONArray deviceSizeArray = getJSONArray("device_sizes", deviceJSON);
        if (deviceSizeArray != null){
            for (int i = 0; i < deviceSizeArray.length(); i += 1){
                JSONObject currentSize = deviceSizeArray.getJSONObject(i);
                String type = getJSONString("type", currentSize);
                String unit = getJSONString("unit", currentSize);
                String value = getJSONString("value", currentSize);

                DeviceSize deviceSize = new DeviceSize(type, unit, value);

                deviceSizes.put(deviceSize.getType(), deviceSize);
            }
        }

        deviceStorages = new HashMap<>();
        JSONArray deviceStorageArray = getJSONArray("storage", deviceJSON);
        if (deviceStorageArray != null){
            for (int i = 0; i < deviceStorageArray.length(); i += 1){
                JSONObject currentStorage = deviceStorageArray.getJSONObject(i);
                String type = getJSONString("type", currentStorage);

                Storage storage = new Storage(type);
                String specialConditions = getJSONString("special_conditions", currentStorage);
                if (specialConditions != null) storage.setSpecialConditions(specialConditions);
                if (currentStorage.has("high")){
                    int highValue = getJSONInt("value", currentStorage.getJSONObject("high"));
                    String highUnit = getJSONString("unit", currentStorage.getJSONObject("high"));
                    storage.setHigh(highValue, highUnit);
                }
                if (currentStorage.has("low")){
                    int lowValue = getJSONInt("value", currentStorage.getJSONObject("low"));
                    String lowUnit = getJSONString("unit", currentStorage.getJSONObject("low"));
                    storage.setLow(lowValue, lowUnit);
                }
                deviceStorages.put(storage.getType(), storage);
            }
        }

    }

    private boolean getJSONBoolean(String JsonKey, JSONObject deviceJson) {
        return deviceJson.has(JsonKey) && deviceJson.getBoolean(JsonKey);
    }

    private int getJSONInt(String JsonKey, JSONObject deviceJson){
        if (deviceJson.has(JsonKey)){
            return deviceJson.getInt(JsonKey);
        }
        return 0;
    }

    private String getJSONString(String JsonKey, JSONObject deviceJson){
        if (deviceJson.has(JsonKey)){
            return deviceJson.getString(JsonKey);
        }
        return null;
    }

    private JSONArray getJSONArray(String JsonKey, JSONObject deviceJson){
        if (deviceJson.has(JsonKey)){
            return deviceJson.getJSONArray(JsonKey);
        }
        return null;
    }

    /**
     *
     * @return Whether or not the Device has a donation ID.
     */
    public boolean hasDonationId() {
        return hasDonationId;
    }

    /**
     *
     * @return Whether or not the Device requires a prescription
     */
    public boolean isPrescription() {
        return isPrescription;
    }

    /**
     *
     * @return Whether or not the Device is labeled as No Rubber Latex
     */
    public boolean isLabeledAsNRL() {
        return isLabeledAsNRL;
    }

    /**
     *
     * @return Whether or not the device has a Serial Number
     */
    public boolean hasSerialNumber() {
        return hasSerialNumber;
    }

    /**
     *
     * @return Whether or not the device is Direct Marking Exempt
     */
    public boolean isDirectMarkingExempt() {
        return isDirectMarkingExempt;
    }

    /**
     *
     * @return Whether or not the device is labeled as "No NRL"
     */
    public boolean isLabeledAsNoNRL() {
        return isLabeledAsNoNRL;
    }

    /**
     *
     * @return Whether or not the device is Single Use
     */
    public boolean isSingleUse() {
        return isSingleUse;
    }

    /**
     *
     * @return Whether or not the device is available over the counter
     */
    public boolean isOverTheCounter() {
        return isOverTheCounter;
    }

    /**
     *
     * @return Whether or not the device has a manufacturing date
     */
    public boolean hasManufacturingDate() {
        return hasManufacturingDate;
    }

    /**
     *
     * @return Whether or not the device is a combination product
     */
    public boolean isCombinationProduct() {
        return isCombinationProduct;
    }

    /**
     *
     * @return Whether or not the device is part of a kit
     */
    public boolean isKit() {
        return isKit;
    }

    /**
     *
     * @return Whether or not the device is Pre-market Exempt
     */
    public boolean isPMExempt() {
        return isPMExempt;
    }

    /**
     *
     * @return Whether or not the device has a lot or batch number
     */
    public boolean hasLotOrBatchNumber() {
        return hasLotOrBatchNumber;
    }

    /**
     *
     * @return Whether or not the device has an expiration date
     */
    public boolean hasExpirationDate() {
        return hasExpirationDate;
    }

    /**
     *
     * @return Whether or not the device is HCTP
     */
    public boolean isHCTP() {
        return isHCTP;
    }

    /**
     *
     * @return Whether or not the device requires sterilization prior to use
     */
    public boolean isSterilizationPriorToUse() {
        return isSterilizationPriorToUse;
    }

    /**
     *
     * @return Whether or not the device is sterile
     */
    public boolean isSterile() {
        return isSterile;
    }

    /**
     *
     * @return The count of devices in its base packaging
     */
    public int getCountInBasePackage() {
        return countInBasePackage;
    }

    /**
     *
     * @return The MRI safety status of the device
     */
    public String getMRISafety() {
        return MRISafety;
    }

    /**
     *
     * @return The record status of the device
     */
    public String getRecordStatus() {
        return recordStatus;
    }

    /**
     *
     * @return The commercial distribution status of the device.
     */
    public String getCommercialDistributionStatus() {
        return commercialDistributionStatus;
    }

    /**
     *
     * @return A description of the device
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return The version or model number of the device
     */
    public String getVersionOrModelNumber() {
        return versionOrModelNumber;
    }

    /**
     *
     * @return The brand name of the device
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     *
     * @return The catalog number of the device
     */
    public String getCatalogNumber() {
        return catalogNumber;
    }

    /**
     *
     * @return The company name of the device
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @return The publish date of the device
     */
    public Calendar getPublishDate() {
        return publishDate;
    }

    /**
     *
     * @return The Device Identifier of the device
     */
    public String getDeviceIdentifier() {
        return getIdentifier("Primary").getId();
    }

    /**
     *
     * @return The types of Identifiers associated with the device. e.g. "Primary", "Package"
     */
    public Collection<String> getIdentifierTypes(){
        return identifiers.keySet();
    }

    /**
     *
     * @param type The type associated with the desired identifier
     * @return The identifier associated with the type provided
     */
    public Identifier getIdentifier(String type){
        return identifiers.get(type);
    }

    /**
     *
     * @return The types of product codes associated with a device. e.g. "LJS"
     */
    public Collection<String> getProductCodeTypes(){
        return productCodes.keySet();
    }

    /**
     * @param type The type associated with the desired product code
     * @return The product code associated with the type provided
     */
    public ProductCode getProductCode(String type){
        return productCodes.get(type);
    }

    /**
     *
     * @return The types of device sizes associated with a device. e.g. "Length", "Width"
     */
    public Collection<String> getDeviceSizeTypes(){
        return deviceSizes.keySet();
    }

    /**
     * @param type The type associated with the desired device size
     * @return The product size information for the type provided
     */
    public DeviceSize getDeviceSize(String type){
        return deviceSizes.get(type);
    }

    /**
     *
     * @return A list of contact emails associated with the device
     */
    public Collection<String> getContactEmails(){
        return contactEmails;
    }

    /**
     *
     * @return A list of contact phone numbers associated with the device
     */
    public Collection<String> getContactPhones(){
        return contactPhones;
    }

    /**
     *
     * @return The types of storage information associated with a device
     */
    public Collection<String> getStorageTypes(){
        return deviceStorages.keySet();
    }

    /**
     * @param type The type associated with the desired device storage information
     * @return The device storage information associated with the type provided
     */
    public Storage getStorage(String type){
        return deviceStorages.get(type);
    }

}
