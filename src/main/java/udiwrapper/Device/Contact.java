package udiwrapper.Device;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {
    private String type;
    private String phone;
    private String phoneExtension;
    private String email;

    public Contact(JSONObject contactObject, String type){
        this.type = type;
        try {
            if (!contactObject.isNull("phone")) phone = contactObject.getString("phone");
            if (!contactObject.isNull("email")) email = contactObject.getString("email");
            if (!contactObject.isNull("phoneExtension")) phoneExtension = contactObject.getString("phoneExtension");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneExtension() {
        return phoneExtension;
    }
}
