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

    }

    private boolean getJSONBoolean(String JsonKey, JSONObject deviceJson){
        if (deviceJson.has(JsonKey)){
            return deviceJson.getBoolean(JsonKey);
        }
        return false;
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

    public boolean hasDonationId() {
        return hasDonationId;
    }

    public boolean isPrescription() {
        return isPrescription;
    }

    public boolean isLabeledAsNRL() {
        return isLabeledAsNRL;
    }

    public boolean hasSerialNumber() {
        return hasSerialNumber;
    }

    public boolean isDirectMarkingExempt() {
        return isDirectMarkingExempt;
    }

    public boolean isLabeledAsNoNRL() {
        return isLabeledAsNoNRL;
    }

    public boolean isSingleUse() {
        return isSingleUse;
    }

    public boolean isOverTheCounter() {
        return isOverTheCounter;
    }

    public boolean hasManufacturingDate() {
        return hasManufacturingDate;
    }

    public boolean isCombinationProduct() {
        return isCombinationProduct;
    }

    public boolean isKit() {
        return isKit;
    }

    public boolean isPMExempt() {
        return isPMExempt;
    }

    public boolean hasLotOrBatchNumber() {
        return hasLotOrBatchNumber;
    }

    public boolean hasExpirationDate() {
        return hasExpirationDate;
    }

    public boolean isHCTP() {
        return isHCTP;
    }

    public boolean isSterilizationPriorToUse() {
        return isSterilizationPriorToUse;
    }

    public boolean isSterile() {
        return isSterile;
    }

    public int getCountInBasePackage() {
        return countInBasePackage;
    }

    public String getMRISafety() {
        return MRISafety;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public String getCommercialDistributionStatus() {
        return commercialDistributionStatus;
    }

    public String getDescription() {
        return description;
    }

    public String getVersionOrModelNumber() {
        return versionOrModelNumber;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Calendar getPublishDate() {
        return publishDate;
    }

    public String getDeviceIdentifier() {
        return getIdentifier("Primary").getId();
    }

    public Collection<String> getIdentifierTypes(){
        return identifiers.keySet();
    }

    public Identifier getIdentifier(String type){
        return identifiers.get(type);
    }

    public Collection<String> getProductCodeTypes(){
        return productCodes.keySet();
    }

    public ProductCode getProductCode(String type){
        return productCodes.get(type);
    }

    public Collection<String> getDeviceSizeTypes(){
        return deviceSizes.keySet();
    }

    public DeviceSize getDeviceSize(String type){
        return deviceSizes.get(type);
    }

    public Collection<String> getContactEmails(){
        return contactEmails;
    }

    public Collection<String> getContactPhones(){
        return contactPhones;
    }
}
