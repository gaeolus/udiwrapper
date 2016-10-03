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

    /**
     *
     * @return The type of identifier. e.g. "Primary"
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return The issuing agency of the identifier
     */
    public String getIssuingAgency() {
        return issuingAgency;
    }

    /**
     *
     * @return The package status
     */
    public String getPackageStatus() {
        return packageStatus;
    }

    /**
     *
     * @return The quantity per package
     */
    public int getQuantityPerPackage() {
        return quantityPerPackage;
    }

    /**
     *
     * @return The Unit of Use ID
     */
    public String getUnitOfUseId() {
        return unitOfUseId;
    }

    /**
     *
     * @return The ID
     */
    public String getId() {
        return id;
    }
}
