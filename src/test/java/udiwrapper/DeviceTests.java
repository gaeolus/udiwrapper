package udiwrapper;

import org.junit.Test;

import udiwrapper.Device.Device;

import static org.junit.Assert.*;

public class DeviceTests {
    private static final Device device = UDIWrapper.fetchDIDevice("08717648200274");

    @Test
    public void testContact() throws Exception {
        assertEquals("+1(800)227-9902", device.getContacts().get(0).getPhone());
        assertEquals("AV.CUSTOMERCARE@AV.ABBOTT.COM", device.getContacts().get(0).getEmail());
        assertEquals(null, device.getContacts().get(0).getPhoneExtension());
        assertEquals("customerContact", device.getContacts().get(0).getType());
    }

    @Test
    public void testDeviceSize() throws Exception {
        // TODO implement. Need to find examples of Device Size first
    }

    @Test
    public void testEnvironmentalCondition() throws Exception {
        // TODO implement. Need to find examples of Device EnvironmentalConditions first
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
        assertEquals("NIQ", device.getProductCodes().get(0).getProductCode());
        assertEquals("Coronary drug-eluting stent", device.getProductCodes().get(0).getProductCodeName());
        assertEquals("fdaProductCode", device.getProductCodes().get(0).getType());
    }

    @Test
    public void testSterilization() throws Exception {
        assertEquals(null, device.getSterilization().getMethodTypes());
        assertEquals(true, device.getSterilization().isDeviceSterile());
        assertEquals(false, device.getSterilization().isSterilizePriorToUse());
    }
}
