package udiwrapper.openFDA;

import org.json.JSONObject;
import org.junit.Test;
import udiwrapper.openFDA.Device.Device;
import udiwrapper.openFDA.Device.Identifier;

import java.util.Calendar;

import static org.junit.Assert.*;
public class openFDADeviceTests {
    private final String deviceString = "{ \"has_donation_id_number\": \"false\", \"mri_safety\": \"Labeling does not contain MRI Safety Information\", \"record_status\": \"Published\", \"is_rx\": \"true\", \"is_labeled_as_nrl\": \"false\", \"commercial_distribution_status\": \"In Commercial Distribution\", \"device_description\": \"Hickman 9 Fr Pediatric Length Dual-Lumen CV Catheter, Cutdown Tray with SureCuff Tissue Ingrowth Cuff\", \"has_serial_number\": \"false\", \"sterilization\": { \"is_sterilization_prior_use\": \"false\", \"is_sterile\": \"true\" }, \"is_direct_marking_exempt\": \"false\", \"is_labeled_as_no_nrl\": \"false\", \"is_single_use\": \"true\", \"identifiers\": [ { \"package_status\": \"In Commercial Distribution\", \"unit_of_use_id\": \"00801741051746\", \"issuing_agency\": \"GS1\", \"quantity_per_package\": \"24\", \"id\": \"10801741051743\", \"type\": \"Package\" }, { \"issuing_agency\": \"GS1\", \"id\": \"00801741051746\", \"type\": \"Primary\" } ], \"is_otc\": \"false\", \"version_or_model_number\": \"0600320\", \"has_manufacturing_date\": \"false\", \"brand_name\": \"Hickman 9 F Pediatric Dual-Lumen CV Catheter\", \"is_combination_product\": \"false\", \"is_kit\": \"true\", \"product_codes\": [ { \"code\": \"LJS\", \"name\": \"Catheter,intravascular,therapeutic,long-term greater than 30 days\", \"openfda\": { \"device_name\": \"Catheter,Intravascular,Therapeutic,Long-Term Greater Than 30 Days\", \"medical_specialty_description\": \"General Hospital\", \"device_class\": \"2\", \"regulation_number\": \"880.5970\" } } ], \"is_pm_exempt\": \"false\", \"device_count_in_base_package\": \"1\", \"has_lot_or_batch_number\": \"true\", \"customer_contacts\": [ { \"phone\": \"+1(800)545-0890\", \"email\": \"medical.services@crbard.com\" } ], \"catalog_number\": \"0600320\", \"company_name\": \"Bard Access Systems, Inc.\", \"has_expiration_date\": \"true\", \"is_hct_p\": \"false\", \"gmdn_terms\": [ { \"name\": \"Central venous catheter\", \"definition\": \"A sterile, flexible tube intended to be introduced into a neck or thoracic vein and often advanced into the superior vena cava for various infusion/aspiration procedures (i.e., non-dedicated) including the intravenous administration of nutrients, fluids, chemotherapeutic agents or other drugs, and blood sampling or delivery; it may also be used to monitor venous pressure. The proximal end of this central venous catheter (CVC) is typically fixed to the patient for long-term use. It may include supportive devices associated with introduction (e.g., guidewire, introducer). This is a single-use device.\" } ], \"publish_date\": \"2015-06-27\", \"device_sizes\": [ { \"unit\": \"Centimeter\", \"type\": \"Length\", \"value\": \"35\" }, { \"unit\": \"French\", \"type\": \"Catheter Gauge\", \"value\": \"9\" } ] }";
    private final JSONObject deviceJSONForTesting = new JSONObject(deviceString);
    private final Device device = new Device(deviceJSONForTesting);

    @Test
    public void testDeviceNotNull(){
        assertNotNull(device);
    }

    @Test
    public void testDeviceIdentifier() {
        String deviceIdentifier = "00801741051746";
        assertEquals(deviceIdentifier, device.getDeviceIdentifier());
    }

    @Test
    public void testHasDonationId(){
        assertFalse(device.hasDonationId());
    }

    @Test
    public void testIsRx(){
        assertTrue(device.isPrescription());
    }

    @Test
    public void testIsLabledAsNRL(){
        assertFalse(device.isLabeledAsNRL());
    }

    @Test
    public void testHasSerialNumber(){
        assertFalse(device.hasSerialNumber());
    }

    @Test
    public void testIsDirectMarkingExempt(){
        assertFalse(device.isDirectMarkingExempt());
    }

    @Test
    public void testIsLabeledAsNoNRL(){
        assertFalse(device.isLabeledAsNoNRL());
    }

    @Test
    public void testIsSingleUse(){
        assertTrue(device.isSingleUse());
    }

    @Test
    public void testIsOTC(){
        assertFalse(device.isOverTheCounter());
    }

    @Test
    public void testHasManufacturingDate(){
        assertFalse(device.hasManufacturingDate());
    }

    @Test
    public void testIsCombinationProduct(){
        assertFalse(device.isCombinationProduct());
    }

    @Test
    public void testIsKit(){
        assertTrue(device.isKit());
    }

    @Test
    public void testIsPMExempt(){
        assertFalse(device.isPMExempt());
    }

    @Test
    public void testHasLotOrBatchNumber(){
        assertTrue(device.hasLotOrBatchNumber());
    }

    @Test
    public void testHasExpirationDate(){
        assertTrue(device.hasExpirationDate());
    }

    @Test
    public void testIsHCTP(){
        assertFalse(device.isHCTP());
    }

    @Test
    public void testMRISafety(){
        String MRISafety = "Labeling does not contain MRI Safety Information";
        assertEquals(MRISafety, device.getMRISafety());
    }

    @Test
    public void testRecordStatus(){
        String recordStatus = "Published";
        assertEquals(recordStatus, device.getRecordStatus());
    }

    @Test
    public void testCommercialDistributionStatus(){
        String commercialDistributionStatus = "In Commercial Distribution";
        assertEquals(commercialDistributionStatus, device.getCommercialDistributionStatus());
    }

    @Test
    public void testDeviceDescription(){
        String description = "Hickman 9 Fr Pediatric Length Dual-Lumen CV Catheter, Cutdown Tray with SureCuff Tissue Ingrowth Cuff";
        assertEquals(description, device.getDescription());
    }

    @Test
    public void testVersionOrModelNumber(){
        String versionOrModelNumber = "0600320";
        assertEquals(versionOrModelNumber, device.getVersionOrModelNumber());
    }

    @Test
    public void testBrandName(){
        String brandName = "Hickman 9 F Pediatric Dual-Lumen CV Catheter";
        assertEquals(brandName, device.getBrandName());
    }

    @Test
    public void testCatalogNumber(){
        String catalogNumber = "0600320";
        assertEquals(catalogNumber, device.getCatalogNumber());
    }

    @Test
    public void testCompanyName(){
        String companyName = "Bard Access Systems, Inc.";
        assertEquals(companyName, device.getCompanyName());
    }

    @Test
    public void testDeviceCountInBasePackage(){
        int deviceCountInBasePackage = 1;
        assertEquals(deviceCountInBasePackage, device.getCountInBasePackage());
    }

    @Test
    public void testPublishDate(){
        Calendar publishDate = Calendar.getInstance();
        publishDate.set(2015, Calendar.JUNE, 27, 0,0,0);
        assertEquals(publishDate.get(Calendar.YEAR), device.getPublishDate().get(Calendar.YEAR));
        assertEquals(publishDate.get(Calendar.DAY_OF_MONTH), device.getPublishDate().get(Calendar.DAY_OF_MONTH));
        assertEquals(publishDate.get(Calendar.MONTH), device.getPublishDate().get(Calendar.MONTH));
    }

    @Test
    public void testSterilization(){
        assertFalse(device.isSterilizationPriorToUse());
        assertTrue(device.isSterile());
    }

    @Test
    public void testIdentifiers(){
        assertTrue(device.getIdentifierTypes().contains("Primary"));
        assertTrue(device.getIdentifierTypes().contains("Package"));
        assertFalse(device.getIdentifierTypes().contains("Something Random"));

        Identifier primaryIdentifier = device.getIdentifier("Primary");
        Identifier packageIdentifier = device.getIdentifier("Package");

        assertEquals("00801741051746", primaryIdentifier.getId());
        assertEquals("GS1", primaryIdentifier.getIssuingAgency());
        assertEquals("Primary", primaryIdentifier.getType());

        assertEquals("10801741051743", packageIdentifier.getId());
        assertEquals("GS1", packageIdentifier.getIssuingAgency());
        assertEquals(24, packageIdentifier.getQuantityPerPackage());
        assertEquals("00801741051746", packageIdentifier.getUnitOfUseId());
        assertEquals("In Commercial Distribution", packageIdentifier.getPackageStatus());
        assertEquals("Package", packageIdentifier.getType());
    }

    @Test
    public void testProductCodes(){
        assertTrue(device.getProductCodeTypes().contains("LJS"));

        ProductCode productCode = device.getProductCode("LJS");

        assertEquals("Catheter,intravascular,therapeutic,long-term greater than 30 days",
                productCode.getName());
        assertEquals("Catheter,Intravascular,Therapeutic,Long-Term Greater Than 30 Days",
                productCode.getOpenFDAName());
        assertEquals("General Hospital",
                productCode.getOpenFDASpecialtyDescription());
        assertEquals("2",
                productCode.getOpenFDADeviceClass());
        assertEquals("880.5970",
                productCode.getOpenFDARegulationNumber());
    }

    @Test
    public void testCustomerContacts(){
        assertTrue(device.getContactEmails().contains("medical.services@crbard.com"));
        assertTrue(device.getContactPhones().contains("+1(800)545-0890"));

    }

    @Test
    public void testDeviceSizes(){
        assertTrue(device.getDeviceSizeTypes().contains("Length"));
        assertTrue(device.getDeviceSizeTypes().contains("Catheter Gauge"));

        DeviceSize length = device.getDeviceSize("Length");
        DeviceSize catheterGauge = device.getSize("Catheter Gauge");

        assertEquals("35", length.getValue());
        assertEquals("Centimeter", length.getUnit());

        assertEquals("9", catheterGauge.getValue());
        assertEquals("French", catheterGauge.getUnit());
    }

}
