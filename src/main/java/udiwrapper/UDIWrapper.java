package udiwrapper;

import udiwrapper.Device.Device;
import udiwrapper.Device.UDIDevice;

public class UDIWrapper {

    // to get whether or not the device exists, either a DI or a UDI can be used
    public static boolean deviceExists(String deviceID){
        return false;
    }

    // fetch the Device information with a DI. Returns the Device if the DI is valid
    // and null if the DI doesn't exist
    public static Device fetchDIDevice(String DI){
        return null;
    }

    // fetch the Device information with a DI. Returns the Device if the DI is valid
    // and null if the DI doesn't exist
    public static UDIDevice fetchUDIDevice(String UDI){
        return null;
    }

}
