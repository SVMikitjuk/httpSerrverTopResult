package mik.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by mikitjuk on 18.11.15.
 */
public class RequestAdd implements Serializable, Comparable {

    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("level_id")
    private int levelId;
    @JsonProperty("result")
    private int result;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int compareTo(Object obj) {
        RequestAdd tmp = (RequestAdd)obj;

        if(this.result < tmp.result)
            return -1;
        else if(this.result > tmp.result)
            return 1;

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestAdd result1 = (RequestAdd) o;

        if (levelId != result1.levelId) return false;
        if (result != result1.result) return false;
        if (userId != result1.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = userId;
        result1 = 31 * result1 + levelId;
        result1 = 31 * result1 + result;
        return result1;
    }
}
