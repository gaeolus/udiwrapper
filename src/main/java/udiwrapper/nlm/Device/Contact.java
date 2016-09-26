package udiwrapper.nlm.Device;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {
    private String type;
    private String phone;
    private String phoneExtension;
    private String email;

    public Contact(JSONObject contactObject, String type) throws JSONException {
        this.type = type;
        if (!contactObject.isNull("phone")) phone = contactObject.getString("phone");
        if (!contactObject.isNull("email")) email = contactObject.getString("email");
        if (!contactObject.isNull("phoneExtension")) phoneExtension = contactObject.getString("phoneExtension");
    }

    /**
     * @return the type of contact information. e.g. "Handling Environment Temperature"
     */
    public String getType() {
        return type;
    }

    /**
     * @return the phone number to contact. e.g. "+1(800)227-9902"
     * May be null.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the email address. e.g. "AV.CUSTOMERCARE@AV.ABBOTT.COM"
     * May be null.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the phone extension of the contact. May be null.
     */
    public String getPhoneExtension() {
        return phoneExtension;
    }
}
