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

    /**
     *
     * @return The product code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @return The name of the product code
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return The openFDA Name
     */
    public String getOpenFDAName() {
        return openFDAName;
    }

    /**
     *
     * @return The openFDA Specialty Description
     */
    public String getOpenFDASpecialtyDescription() {
        return openFDASpecialtyDescription;
    }

    /**
     *
     * @return The openFDA Device Class
     */
    public String getOpenFDADeviceClass() {
        return openFDADeviceClass;
    }

    /**
     *
     * @return The openFDA Regulation Number
     */
    public String getOpenFDARegulationNumber() {
        return openFDARegulationNumber;
    }
}
