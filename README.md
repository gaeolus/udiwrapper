# What this is
UDIWrapper is a Java library for wrapping the JSON returned from the [FDA's](https://www.open.fda.gov/) [Device/UDI API endpoint](https://open.fda.gov/device/udi/).

If you have a medical device's DI (Device Identifier number), the API will return a trove of information about that Device. This wrapper provides easy access to that information.

In version 1.0.0 you could only retrieve one device at a time. The UDIWrapper and Device object using the National Library of Medicine's API have been deprecated, but they are still available in the udiwrapper.nlm package.
In version 2 you can search for any device property, and receive any number of devices. It's also possible to skip a number of devices before receiving devices.

# Download
This repo is on Maven Central. To use with Gradle, add this to your build.gradle
```gradle
dependencies {
    ...
    compile 'com.smithsocial:udiwrapper:2.0.2'
}
```
To use with Maven:
```xml
    ...
    <dependencies>
        ...
        <dependency>
            <groupId>com.smithsocial</groupId>
            <artifactId>udiwrapper</artifactId>
            <version>2.0.2</version>
        </dependency>
    </dependencies>
```

# Usage
First, build the UDIWrapper:
```java
import udiwrapper.openFDA
import udiwrapper.openFDA.Device
...
String myApiKey = "YOUR_API_KEY";
String deviceId = "08717648200274";

//setting search to null defaults the search property to device ID
UDIWrapper mUDIWrapper = new UDIWrapper.Builder(myApiKey)
                                        .setSearch(null, deviceId)
                                        .build();
```
You can then get information about the search
```java
// test whether or not the search returned at least one device
if (mUDIWrapper.searchExists()){
    // get the total number of devices returned
    int totalDevicesReturned = mUDIWrapper.getTotal();
    
    // get the map of the devices and their identifiers
    HashMap<String, Device> devices = mUDIWrapper.getDevices();
    
    // or get only the device identifiers for all the devices returned
    Collection<String> identifiers = mUDIWrapper.getDeviceIdentifiers();
    
    // you can use those identifiers to get the device
    String deviceIdentifier = identifiers.iterator().next();
    Device device = mUDIWrapper.getDevice(deviceIdentifier);
}

```
With version 2, you can search for any device property you want:
```java

UDIWrapper brandSearch = new UDIWrapper.Builder(myApiKey)
                                        .setSearch(UDIWrapper.DeviceProperties.COMPANY_NAME, "Cool")
                                        .setLimit(10)
                                        .build();

```
You can also alter the search:
```java
// Get the first ten results from a search
UDIWrapper brandSearch = new UDIWrapper.Builder(myApiKey)
                                        .setSearch(UDIWrapper.DeviceProperties.BRAND_NAME, "XIENCE")
                                        .setLimit(10)
                                        .build();
                                        
Iterator<String> iterator = udiWrapper.getDeviceIdentifiers().iterator();
while (iterator.hasNext()){
    Device device = udiWrapper.getDevice(iterator.next());
    // do something with each device
}

// alter the search. If you set the argument as null, that parameter won't be altered
// alterSearch(String searchValue, String searchProperty, String limit, String skip)
// This skips the first ten results
brandSearch.alterSearch(null, null, null, 10);
```
# Documentation
Check [the documentation](https://bensmith41.github.io/udiwrapper) for more information.

# Future plans
- Support for displaying adverse events for a given device - from the [FDA's device/event API endpoint](https://open.fda.gov/device/event/)
- Support for displaying recall status for a given device - from the [FDA's device/recall API endpoint](https://open.fda.gov/device/recall/)
