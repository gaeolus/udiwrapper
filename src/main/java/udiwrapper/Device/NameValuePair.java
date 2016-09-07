package udiwrapper.Device;

public class NameValuePair {
    private String unit;
    private String value;

    public NameValuePair(String unit, String value){
        this.unit = unit;
        this.value = value;
    }

    public String getUnit(){
        return unit;
    }

    public String getValue(){
        return value;
    }
}
