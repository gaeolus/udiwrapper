package udiwrapper.Device;

public class NameValuePair {
    private String unit;
    private String value;

    public NameValuePair(String unit, String value){
        this.unit = unit;
        this.value = value;
    }

    /**
     * @return the unit
     */
    public String getUnit(){
        return unit;
    }

    /**
     * @return the value
     */
    public String getValue(){
        return value;
    }
}
