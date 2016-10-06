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
    private UDIWrapper udiWrapper = builder.setSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "08717648200274").build();

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
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "08717648200274", null, null);
        assertNotNull(udiWrapper.getDevices());
    }

    @Test
    public void testSearching(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.BRAND_NAME, "XIENCE", null, null);
        int numberReturned = 262;
        assertEquals(numberReturned, udiWrapper.getTotal());
    }

    @Test
    public void testSkipping(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.BRAND_NAME, "XIENCE", null, 10);
        assertTrue(udiWrapper.getSearchExists());
    }

    @Test
    public void testGetDeviceKeys(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "08717648200274", null, "0");
        assertTrue(udiWrapper.getDeviceIdentifiers().contains("08717648200274"));
    }

    @Test
    public void testGetDeviceWithKey(){
        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "08717648200274", null, null);
        assertEquals("XIENCE ALPINE", udiWrapper.getDevice("08717648200274").getBrandName());
    }

    @Test
    public void testAlterSearch(){
        UDIWrapper udiWrapper = builder
                .setSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "08717648200274")
                .build();

        assertTrue(udiWrapper.getSearchExists());

        udiWrapper.alterSearch(UDIWrapper.DeviceProperties.IDENTIFIER, "badidentifier", null, null);
        assertFalse(udiWrapper.getSearchExists());
    }
}
