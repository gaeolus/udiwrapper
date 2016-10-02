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

    public String getType(){
        return type;
    }

    public String getValue(){
        return value;
    }

    public String getUnit(){
        return unit;
    }
}
