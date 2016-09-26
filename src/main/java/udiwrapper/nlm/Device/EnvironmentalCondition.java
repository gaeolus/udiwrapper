package udiwrapper.nlm.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class EnvironmentalCondition {
    private String type;
    private HashMap<String, Condition> conditions = new HashMap<>();

    public EnvironmentalCondition(JSONArray environmentalConditionObject, String type) throws JSONException{
        this.type = type;
        for (int i = 0; i < environmentalConditionObject.length(); i += 1){
            JSONObject currentObject = environmentalConditionObject.getJSONObject(i);
            Condition condition = new Condition(currentObject, type);
            conditions.put(condition.getType(), condition);
        }
    }

    /**
     * @return the type of condition. e.g. "storageHandling"
     */
    public String getType() {
        return type;
    }

    /**
     * @return a list of {@link Condition}s
     */
    public HashMap<String, Condition> getConditions() {
        return conditions;
    }
}
