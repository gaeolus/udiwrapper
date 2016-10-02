package udiwrapper.openFDA.Device;

public class ProductCode {
    private String code;
    private String name;
    private String openFDAName;
    private String openFDASpecialtyDescription;
    private String openFDADeviceClass;
    private String openFDARegulationNumber;

    ProductCode(String code, String name){
        this.code = code;
        this.name = name;
    }

    void setOpenFDAName(String name){
        this.openFDAName = name;
    }

    void setOpenFDASpecialtyDescription(String specialtyDescription){
        this.openFDASpecialtyDescription = specialtyDescription;
    }

    void setOpenFDADeviceClass(String deviceClass){
        this.openFDADeviceClass = deviceClass;
    }

    void setOpenFDARegulationNumber(String regulationNumber){
        this.openFDARegulationNumber = regulationNumber;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getOpenFDAName() {
        return openFDAName;
    }

    public String getOpenFDASpecialtyDescription() {
        return openFDASpecialtyDescription;
    }

    public String getOpenFDADeviceClass() {
        return openFDADeviceClass;
    }

    public String getOpenFDARegulationNumber() {
        return openFDARegulationNumber;
    }
}
