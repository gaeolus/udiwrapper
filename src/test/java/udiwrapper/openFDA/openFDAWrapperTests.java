package udiwrapper.openFDA;

import org.junit.Test;

import static org.junit.Assert.*;

class Environment {
    String getFdaApiKey() {
        return System.getenv("FDA_API_KEY");
    }
}
public class openFDAWrapperTests {
    private String apiKey = new Environment().getFdaApiKey();
    private UDIWrapper.Builder builder = new UDIWrapper.Builder(apiKey);
    private UDIWrapper udiWrapper = builder.setSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "00851788001273").build();
    private String goodDi = "00851788001273";

    @Test
    public void testApiKeyNotNull(){
        assertNotNull(apiKey);
    }

    @Test
    public void testGoodDiExists(){
        assertTrue(udiWrapper.getSearchExists());
    }

    @Test
    public void testBadDiExists(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "test", null, null);
        assertFalse(udiWrapper.getSearchExists());
    }

    @Test
    public void testDefaultSearch(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, goodDi, null, null);
        assertNotNull(udiWrapper.getDevices());
    }

    @Test
    public void testSearching(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.BRAND_NAME, "X", null, null);
        int numberReturned = 7367;
        assertEquals(numberReturned, udiWrapper.getTotal());
    }

    @Test
    public void testSkipping(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.BRAND_NAME, "X", null, "10");
        assertTrue(udiWrapper.getSearchExists());
    }

    @Test
    public void testGetDeviceKeys(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, goodDi, null, "0");
        assertTrue(udiWrapper.getDeviceIdentifiers().contains(goodDi));
    }

    @Test
    public void testGetDeviceWithKey(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, goodDi, null, null);
        assertEquals("On-X Mitral Heart Valve with Standard Sewing Ring", udiWrapper.getDevice(goodDi).getBrandName());
    }

    @Test
    public void testAlterSearch(){
        UDIWrapper udiWrapperTest = builder
                .setSearch(UDIWrapper.DeviceProperties.IDENTIFIER, goodDi)
                .build();

        assertTrue(udiWrapperTest.getSearchExists());

        udiWrapperTest.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "badidentifier", null, null);
        assertFalse(udiWrapperTest.getSearchExists());
    }

    @Test
    public void testAdverseEventTotal(){
        UDIWrapper udiWrapper = builder.setSearch(UDIWrapper.DeviceProperties.IDENTIFIER, goodDi).build();

        assertEquals(2, udiWrapper.getTotalAdverseEvents(goodDi));

        // test it again
        assertEquals(2, udiWrapper.getTotalAdverseEvents(goodDi));
    }
}
