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

    void setHighValue(int value){
        this.highValue = value;
    }

    void setHighUnit(String unit){
        this.highUnit = unit;
    }

    void setLowValue(int value){
        this.lowValue = value;
    }

    void setLowUnit(String unit){
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
