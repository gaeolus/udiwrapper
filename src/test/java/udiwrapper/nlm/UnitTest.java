package udiwrapper.nlm;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Headers;
import udiwrapper.nlm.Device.Device;
import udiwrapper.nlm.Device.UDIDevice;
import udiwrapper.nlm.UDIWrapper;

import static org.junit.Assert.*;

public class UnitTest {

    // removing these tests as the nlm package is being deprecated

    @Test
    public void testPlaceHolder(){
        assertTrue(true);
    }
    /*
    private String goodDI = "08717648200274";
    private String badDI = "not a valid DI";
    // I don't quite understand how this is a valid UDI, but it's the UDI given as an example
    // on the NLM documentation page: https://accessgudid.nlm.nih.gov/resources/developers/device_lookup_api
    private String goodUDI = "=/08717648200274=,000025=A99971312345600=>014032=}013032&,1000000000000XYZ123";
    private String badUDI = "not a valid UDI";

    // JSON string response for DI Device
    private String goodDIDeviceJson = "{\"gudid\":{\"device\":{\"deviceRecordStatus\":\"Published\",\"devicePublishDate\":\"2015-05-08\",\"deviceCommDistributionEndDate\":null,\"deviceCommDistributionStatus\":\"In Commercial Distribution\",\"identifiers\":{\"identifier\":{\"deviceId\":\"08717648200274\",\"deviceIdType\":\"Primary\",\"deviceIdIssuingAgency\":\"GS1\",\"containsDINumber\":null,\"pkgQuantity\":null,\"pkgDiscontinueDate\":null,\"pkgStatus\":null}},\"brandName\":\"XIENCE ALPINE\",\"versionModelNumber\":\"1145350-28\",\"catalogNumber\":\"1145350-28\",\"companyName\":\"ABBOTT VASCULAR INC.\",\"deviceCount\":\"1\",\"deviceDescription\":\"XIENCE Alpine Everolimus Eluting Coronary Stent System 3.50 mm x 28 mm / Over-The-Wire\",\"DMExempt\":\"false\",\"premarketExempt\":\"false\",\"deviceHCTP\":\"false\",\"deviceKit\":\"false\",\"deviceCombinationProduct\":\"true\",\"singleUse\":\"true\",\"lotBatch\":\"true\",\"serialNumber\":\"true\",\"manufacturingDate\":\"false\",\"expirationDate\":\"true\",\"donationIdNumber\":\"false\",\"labeledContainsNRL\":\"false\",\"labeledNoNRL\":\"false\",\"MRISafetyStatus\":\"MR Conditional\",\"rx\":\"true\",\"otc\":\"false\",\"contacts\":{\"customerContact\":{\"phone\":\"+1(800)227-9902\",\"phoneExtension\":null,\"email\":\"AV.CUSTOMERCARE@AV.ABBOTT.COM\"}},\"gmdnTerms\":{\"gmdn\":{\"gmdnPTName\":\"Drug-eluting coronary artery stent, non-bioabsorbable-polymer-coated\",\"gmdnPTDefinition\":\"A sterile non-bioabsorbable metal tubular mesh structure covered with a non-bioabsorbable polymer and a drug coating that is designed to be implanted, via a delivery catheter, into a coronary artery (or saphenous vein graft) to maintain its patency typically in a patient with symptomatic atherosclerotic heart disease. The drug coating is slowly released and intended to inhibit restenosis by reducing vessel smooth muscle cell proliferation. Disposable devices associated with implantation may be included.\"}},\"productCodes\":{\"fdaProductCode\":{\"productCode\":\"NIQ\",\"productCodeName\":\"Coronary drug-eluting stent\"}},\"deviceSizes\":null,\"environmentalConditions\":{\"storageHandling\":[{\"storageHandlingType\":\"Handling Environment Temperature\",\"storageHandlingHigh\":{\"unit\":\"Degrees Celsius\",\"value\":\"30\"},\"storageHandlingLow\":{\"unit\":\"Degrees Celsius\",\"value\":\"15\"},\"storageHandlingSpecialConditionText\":null},{\"storageHandlingType\":\"Special Storage Condition, Specify\",\"storageHandlingHigh\":{\"unit\":\"\",\"value\":\"\"},\"storageHandlingLow\":{\"unit\":\"\",\"value\":\"\"},\"storageHandlingSpecialConditionText\":\"Store in a dry, dark, cool place. Protect from light. Store at 25 degrees C; excursions between 15 to 30 degrees C permitted.\"},{\"storageHandlingType\":\"Storage Environment Temperature\",\"storageHandlingHigh\":{\"unit\":\"Degrees Celsius\",\"value\":\"25\"},\"storageHandlingLow\":{\"unit\":\"Degrees Celsius\",\"value\":\"25\"},\"storageHandlingSpecialConditionText\":null}]},\"sterilization\":{\"deviceSterile\":\"true\",\"sterilizationPriorToUse\":\"false\",\"methodTypes\":null}}}}";
    private String badDIDeviceJson = "{\"error\":\"No device found for DI 0877648200274\"}";

    // Headers returned for UDI
    Headers headers = new Headers.Builder()
            .add("donation_id", "A999713123456")
            .add("di", "08717648200274")
            .add("lot_number", "000000000000XYZ123")
            .add("manufacturing_date", "2013-02-01")
            .add("expiration_date", "2014-02-01")
            .add("serial_number", "000025")
            .add("issuing_agency", "ICCBBA")
            .add("udi", goodUDI)
            .build();

    // known values for the DI/UDI Device associated with goodDI and goodUDI
    private String brandName = "XIENCE ALPINE";
    private String contactEmail = "AV.CUSTOMERCARE@AV.ABBOTT.COM";
    private boolean isSterile = true;

    // UDI specific values to test against
    private String lotNumber = "000000000000XYZ123";
    private String expirationDate = "2014-02-01";
    private String serialNumber = "000025";

    @Test
    public void testDeviceExists() throws Exception {
        assertTrue(UDIWrapper.deviceExists(goodDI));
        assertFalse(UDIWrapper.deviceExists(badDI));
    }

    @Test
    public void testFetchDIDevice() throws Exception {
        Device device = UDIWrapper.fetchDIDevice(goodDI);
        assertNotNull(device);
        assertNull(UDIWrapper.fetchDIDevice(badDI));
    }

    @Test
    public void testFetchUDIDevice() throws Exception {
        UDIDevice device = UDIWrapper.fetchUDIDevice(goodUDI);
        assertNotNull(device);
        assertNull(UDIWrapper.fetchUDIDevice(badUDI));
    }

    @Test
    public void testGoodDIDevice() throws Exception {
        JSONObject deviceJson = new JSONObject(goodDIDeviceJson);
        Device device = new Device(deviceJson);
        assertNotNull(device);
        assertEquals(brandName, device.getBrandName());
        assertEquals(contactEmail, device.getContacts().get("customerContact").getEmail());
        assertEquals(isSterile, device.getSterilization().isDeviceSterile());
    }

    @Test(expected = JSONException.class)
    public void testBadDIDevice() throws Exception {
        JSONObject deviceJson = new JSONObject(badDIDeviceJson);
        Device device = new Device(deviceJson);
        assertNull(device);
    }

    @Test
    public void testGoodUDIDevice() throws Exception {
        JSONObject deviceJson = new JSONObject(goodDIDeviceJson);
        UDIDevice device = new UDIDevice(deviceJson, headers);
        assertNotNull(device);

        Calendar expirationCal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("y-M-d", Locale.getDefault());
        Date date = dateFormat.parse(expirationDate);
        expirationCal.setTime(date);

        assertEquals(expirationCal, device.getExpirationDate());
        assertEquals(lotNumber, device.getLotNumber());
        assertEquals(serialNumber, device.getSerialNumber());
    }

    @Test(expected = JSONException.class)
    public void testBadUDIDevice() throws Exception {
        JSONObject deviceJson = new JSONObject(badDIDeviceJson);
        UDIDevice device = new UDIDevice(deviceJson, headers);
        assertNull(device);

    }
    */

}
