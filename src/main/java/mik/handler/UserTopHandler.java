package mik.handler;

/**
 * Created by mikitjuk on 18.11.15.
 */
public class UserTopHandler extends InfoHandler {

    @Override
    public String getTitleList() {
        return "topOfUser";
    }

    @Override
    public String getTitle() {
        return "User TOP!";
    }

    @Override
    public String getParam() {
        return "user_id";
    }

}
