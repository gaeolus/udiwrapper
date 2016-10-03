package udiwrapper.openFDA.Device;

public class DeviceSize {
    private String type;
    private String value;
    private String unit;

    DeviceSize(String type, String unit, String value){
        this.type = type;
        this.value = value;
        this.unit = unit;
    }

    /**
     *
     * @return The type of device size
     */
    public String getType(){
        return type;
    }

    /**
     *
     * @return The value for this type of device size
     */
    public String getValue(){
        return value;
    }

    /**
     *
     * @return The unit the value is measured in
     */
    public String getUnit(){
        return unit;
    }
}
