package udiwrapper.openFDA.Device;

public class Identifier {
    private String type;
    private String issuingAgency;
    private String packageStatus;
    private String unitOfUseId;
    private String id;
    private int quantityPerPackage;

    Identifier(String type, String issuingAgency, String id){
        this.type = type;
        this.issuingAgency = issuingAgency;
        this.id = id;
    }

    void setPackageStatus(String packageStatus){
        this.packageStatus = packageStatus;
    }

    void setQuantityPerPackage(int quantityPerPackage){
        this.quantityPerPackage = quantityPerPackage;
    }

    void setUnitOfUseId(String unitOfUseId){
        this.unitOfUseId = unitOfUseId;
    }

    public String getType() {
        return type;
    }

    public String getIssuingAgency() {
        return issuingAgency;
    }

    public String getPackageStatus() {
        return packageStatus;
    }

    public int getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public String getUnitOfUseId() {
        return unitOfUseId;
    }

    public String getId() {
        return id;
    }
}
