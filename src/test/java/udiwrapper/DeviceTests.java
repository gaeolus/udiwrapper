package udiwrapper;

import org.junit.Test;

import udiwrapper.Device.Device;

import static org.junit.Assert.*;

public class DeviceTests {
    private static final Device device = UDIWrapper.fetchDIDevice("08717648200274");

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
}
