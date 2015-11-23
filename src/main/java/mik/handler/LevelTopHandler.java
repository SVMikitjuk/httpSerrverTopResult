package mik.handler;

/**
 * Created by mikitjuk on 18.11.15.
 */
public class LevelTopHandler extends InfoHandler {

    @Override
    public String getTitleList() {
        return "topOfLevel";
    }

    @Override
    public String getTitle() {
        return "Level TOP!";
    }

    @Override
    public String getParam() {
        return "level_id";
    }

}
