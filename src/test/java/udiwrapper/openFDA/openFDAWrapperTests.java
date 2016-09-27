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
}
