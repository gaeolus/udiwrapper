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
    private UDIWrapper udiWrapper = UDIWrapper.setApiKey(apiKey);

    // meta-test to make sure the API Key is set as an environment variable
    @Test
    public void testApiKeyNotNull(){
        assertNotNull(apiKey);
    }

    @Test
    public void testGoodDiExists(){
        assertTrue(udiWrapper.getDeviceExists("08717648200274"));
    }

    @Test
    public void testBadDiExists(){
        assertFalse(udiWrapper.getDeviceExists("something"));
    }

    @Test
    public void testDefaultSearch(){
        assertNotNull(udiWrapper.fetchDevice("08717648200274"));
    }

    @Test
    public void testSearching(){
        int numberReturned = 262;
        assertEquals(numberReturned, udiWrapper.setSearch("brand_name", "XIENCE").getTotal());
    }

    @Test
    public void testSkipping(){
        assertTrue(udiWrapper.setSkip("261").setSearch("brand_name", "XIENCE").getDeviceExists());
        assertFalse(udiWrapper.setSkip("262").setSearch("brand_name", "XIENCE").getDeviceExists());
    }

    @Test
    public void testCounting(){
        assertTrue(udiWrapper.setCount("brand_name").getDeviceExists());
    }
}
