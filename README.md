# What this is
UDIWrapper is a Java library for wrapping the JSON returned from the [National Library of Medicine's](https://www.nlm.nih.gov/) Medical [Device Lookup API](https://accessgudid.nlm.nih.gov/resources/developers/device_lookup_api).

If you have a medical device's DI (Device Identifier number) or UDI (Unique Device Identifier number), the API will return a trove of information about that Device. This wrapper provides easy access to that information.

# Usage
To get general device information, get the ```Device``` using the DI:
```java
String deviceId = "08717648200274";
Device myDevice = UDIWrapper.fetchDIDevice(deviceId);
```
To get more detailed information about a specific device, use the UDI to get a ```UDIDevice```:
```java
// note: this is not a valid UDI
String deviceUDI = "(01)08717648200274(02)000025(03)A99971312345600";
UDIDevice myDevice = UDIWrapper.fetchUDIDevice(deviceUDI);
```
# Future plans
- A sample Android App
- Support for displaying adverse events for a given device - from the [FDA's device/event API](https://open.fda.gov/device/event/)
- Support for displaying recall status for a given device - from the [FDA's device/recall API](https://open.fda.gov/device/recall/)
