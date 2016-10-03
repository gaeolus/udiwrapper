package udiwrapper.openFDA.Device;

public class Storage{
    private String type;
    private int highValue;
    private String highUnit;
    private int lowValue;
    private String lowUnit;
    private String specialConditions;

    Storage(String type){
        this.type = type;
    }

    void setHigh(int value, String unit){
        this.highValue = value;
        this.highUnit = unit;
    }

    void setLow(int value, String unit){
        this.lowValue = value;
        this.lowUnit = unit;
    }

    void setSpecialConditions(String conditions){
        this.specialConditions = conditions;
    }

    /**
     *
     * @return The high value for the storage type
     */
    public int getHighValue(){ return highValue; }

    /**
     *
     * @return The low value for the storage type
     */
    public int getLowValue(){ return lowValue; }

    /**
     *
     * @return The unit used for the high value
     */
    public String getHighUnit(){ return highUnit; }

    /**
     *
     * @return The unit used for the low value
     */
    public String getLowUnit(){ return lowUnit; }

    /**
     *
     * @return Special condition text for the storage type
     */
    public String getSpecialConditions(){ return specialConditions; }

    /**
     *
     * @return The type of storage
     */
    public String getType(){ return type; }

}
