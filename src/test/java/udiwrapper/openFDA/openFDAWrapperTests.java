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
    private String goodDi = "00851788001273";
    private String badDi = "something";
    private UDIWrapper goodWrapper = builder.setSearch(UDIWrapper.DeviceProperties.IDENTIFIER, goodDi).build();
    private UDIWrapper badWrapper = builder.setSearch(UDIWrapper.DeviceProperties.IDENTIFIER, badDi).build();

    @Test
    public void testApiKeyNotNull(){
        assertNotNull(apiKey);
    }

    @Test
    public void testBadDiExists(){
        assertFalse(badWrapper.getSearchExists());
    }

    @Test
    public void testGoodDiExists(){
        assertTrue(goodWrapper.getSearchExists());
    }

    @Test
    public void testGetDeviceKeys(){
        assertTrue(goodWrapper.getDeviceIdentifiers().contains(goodDi));
    }

    @Test
    public void testGetDeviceWithKey(){
        assertEquals("On-X Mitral Heart Valve with Standard Sewing Ring", goodWrapper.getDevice(goodDi).getBrandName());
    }

    @Test
    public void testDefaultSearch(){
        assertNotNull(goodWrapper.getDevices());
    }

    @Test
    public void testAdverseEventTotal(){
        // test it again
        assertEquals(2, goodWrapper.getTotalAdverseEvents(goodDi));
    }

    @Test
    public void testGetTotal(){
        assertEquals(1, goodWrapper.getTotal());
    }

    @Test
    public void testSkipping(){
        goodWrapper.alterSearch(UDIWrapper.DeviceProperties.BRAND_NAME, "X", null, "10");
        assertTrue(goodWrapper.getSearchExists());
    }
}
