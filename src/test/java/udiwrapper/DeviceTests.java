package udiwrapper;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import udiwrapper.Device.Device;
import udiwrapper.Device.UDIDevice;

import static org.junit.Assert.*;

public class DeviceTests {
    private static final Device device = UDIWrapper.fetchDIDevice("08717648200274");
    private static final UDIDevice udiDevice = UDIWrapper.fetchUDIDevice("=/08717648200274=,000025=A99971312345600=>014032=}013032&,1000000000000XYZ123");

    @Test
    public void testContact() throws Exception {
        assertEquals("customerContact", device.getContacts().get("customerContact").getType());
        assertEquals("+1(800)227-9902", device.getContacts().get("customerContact").getPhone());
        assertEquals("AV.CUSTOMERCARE@AV.ABBOTT.COM", device.getContacts().get("customerContact").getEmail());
        assertEquals(null, device.getContacts().get("customerContact").getPhoneExtension());
    }

    @Test
    public void testDeviceSize() throws Exception {
        // TODO implement. Need to find examples of Device Size first
    }

    @Test
    public void testEnvironmentalCondition() throws Exception {
        assertEquals("storageHandling", device.getEnvironmentalConditions().get("storageHandling").getType());

        assertEquals("Handling Environment Temperature",
                device.getEnvironmentalConditions()
                        .get("storageHandling")
                        .getConditions()
                        .get("Handling Environment Temperature")
                        .getType());

        assertEquals("Degrees Celsius",
                device.getEnvironmentalConditions()
                        .get("storageHandling")
                        .getConditions()
                        .get("Handling Environment Temperature")
                        .getHigh()
                        .getUnit());

        assertEquals("Degrees Celsius",
                device.getEnvironmentalConditions()
                        .get("storageHandling")
                        .getConditions()
                        .get("Handling Environment Temperature")
                        .getLow()
                        .getUnit());

        assertEquals("30",
                device.getEnvironmentalConditions()
                        .get("storageHandling")
                        .getConditions()
                        .get("Handling Environment Temperature")
                        .getHigh()
                        .getValue());

        assertEquals("Store in a dry, dark, cool place. Protect from light. Store at 25 degrees C; excursions between 15 to 30 degrees C permitted.",
                device.getEnvironmentalConditions()
                        .get("storageHandling")
                        .getConditions()
                        .get("Special Storage Condition, Specify")
                        .getSpecialConditionText());
    }

    @Test
    public void testIdentifier() throws Exception {
        assertEquals("08717648200274", device.getIdentifiers().getDeviceId());
        assertEquals("Primary", device.getIdentifiers().getDeviceIdType());
        assertEquals("GS1", device.getIdentifiers().getDeviceIdIssuingAgency());
        assertEquals(null, device.getIdentifiers().getContainsDINumber());
        assertEquals(null, device.getIdentifiers().getPkgQuantity());
        assertEquals(null, device.getIdentifiers().getPkgDiscontinueDate());
        assertEquals(null, device.getIdentifiers().getPkgStatus());
    }

    @Test
    public void testProductCode() throws Exception {
        assertEquals("fdaProductCode", device.getProductCodes().get("fdaProductCode").getType());
        assertEquals("NIQ", device.getProductCodes().get("fdaProductCode").getProductCode());
        assertEquals("Coronary drug-eluting stent", device.getProductCodes().get("fdaProductCode").getProductCodeName());
    }

    @Test
    public void testSterilization() throws Exception {
        assertEquals(null, device.getSterilization().getMethodTypes());
        assertEquals(true, device.getSterilization().isDeviceSterile());
        assertEquals(false, device.getSterilization().isSterilizePriorToUse());
    }

    @Test
    public void testStrings() throws Exception {
        assertEquals("Published", device.getRecordStatus());
        assertEquals("XIENCE Alpine Everolimus Eluting Coronary Stent System 3.50 mm x 28 mm / Over-The-Wire",
                device.getDeviceDescription());
        assertEquals("MR Conditional", device.getMRISafetyStatus());
        assertEquals("In Commercial Distribution", device.getCommercialDistributionStatus());
        assertEquals("XIENCE ALPINE", device.getBrandName());
        assertEquals("1145350-28", device.getVersionModelNumber());
        assertEquals("1145350-28", device.getCatalogNumber());
        assertEquals("ABBOTT VASCULAR INC.", device.getCompanyName());
    }

    @Test
    public void testBooleans() throws Exception {
        assertFalse(device.isDMExempt());
        assertFalse(device.isPremarketExempt());
        assertFalse(device.isDeviceHCTP());
        assertFalse(device.isDeviceKit());
        assertTrue(device.isDeviceCombinationProduct());
        assertTrue(device.isSingleUse());
        assertTrue(device.hasLotBatch());
        assertFalse(device.hasSerialNumber());
        assertFalse(device.hasManufacturingDate());
        assertTrue(device.hasExpirationDate());
        assertFalse(device.hasDonationIdNumber());
        assertFalse(device.isLabeledContainsNRL());
        assertFalse(device.isLabeledNoNRL());
        assertTrue(device.requiresRX());
        assertFalse(device.isOTC());
    }

    @Test
    public void testCalendars() throws Exception {
        Calendar publishDate = Calendar.getInstance();
        publishDate.setTimeInMillis(1420696800000L);
        assertNull(device.getCommercialDistributionEndDate());
        assertEquals(publishDate, device.getPublishDate());
    }

    @Test
    public void testIntegers() throws Exception {
        assert(device.getDeviceCount() == 1);
    }

    @Test
    public void testUDIDevice() throws Exception {
        assertEquals("=/08717648200274=,000025=A99971312345600=>014032=}013032",
                udiDevice.getUdi());
        assertEquals("08717648200274", udiDevice.getDi());
        assertEquals("ICCBBA", udiDevice.getIssuingAgency());
        assertNull(udiDevice.getDonationId());
        assertNull(udiDevice.getDonationId());
        assertNull(udiDevice.getLotNumber());
    }

}
