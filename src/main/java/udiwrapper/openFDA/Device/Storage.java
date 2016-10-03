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

    public int getHighValue(){ return highValue; }
    public int getLowValue(){ return lowValue; }
    public String getHighUnit(){ return highUnit; }
    public String getLowUnit(){ return lowUnit; }
    public String getSpecialConditions(){ return specialConditions; }
    public String getType(){ return type; }

}
