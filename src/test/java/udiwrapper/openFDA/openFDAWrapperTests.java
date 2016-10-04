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

    @Test
    public void testApiKeyNotNull(){
        assertNotNull(apiKey);
    }

    @Test
    public void testGoodDiExists(){
        UDIWrapper udiWrapper = builder.setSearch(null, "08717648200274").build();
        assertTrue(udiWrapper.getSearchExists());
    }

    @Test
    public void testBadDiExists(){
        UDIWrapper udiWrapper = builder.setSearch(null, "test").build();
        assertFalse(udiWrapper.getSearchExists());
    }

    @Test
    public void testDefaultSearch(){
        UDIWrapper udiWrapper = builder.setSearch(null, "08717648200274").build();
        assertNotNull(udiWrapper.getDevices());
    }

    @Test
    public void testSearching(){
        UDIWrapper udiWrapper = builder.setSearch("brand_name", "XIENCE").build();
        int numberReturned = 262;
        assertEquals(numberReturned, udiWrapper.getTotal());
    }

    @Test
    public void testSkipping(){
        UDIWrapper udiWrapper = builder
                .setSearch("brand_name", "XIENCE")
                .setSkip(261)
                .build();
        assertTrue(udiWrapper.getSearchExists());
    }

    @Test
    public void testGetDeviceKeys(){
        UDIWrapper udiWrapper = builder
                .setSearch("identifiers.id", "08717648200274")
                .build();
        assertTrue(udiWrapper.getDeviceIdentifiers().contains("08717648200274"));
    }

    @Test
    public void testGetDeviceWithKey(){
        UDIWrapper udiWrapper = builder
                .setSearch("identifiers.id", "08717648200274")
                .build();
        assertEquals("XIENCE ALPINE", udiWrapper.getDevice("08717648200274").getBrandName());
    }

    @Test
    public void testAlterSearch(){
        UDIWrapper udiWrapper = builder
                .setSearch(null, "08717648200274")
                .build();

        assertTrue(udiWrapper.getSearchExists());

        udiWrapper.alterSearch(null, "badidentifier", null, null);
        assertFalse(udiWrapper.getSearchExists());
    }
}
