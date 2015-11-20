package mik.model;

import java.io.Serializable;

/**
 * Created by mikitjuk on 19.11.15.
 */
public class ResultTop implements Serializable, Comparable{
    private int paramId;
    private int result;

    public ResultTop(int paramId, int result) {
        this.paramId = paramId;
        this.result = result;
    }

    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int compareTo(Object obj) {
        ResultTop tmp = (ResultTop)obj;

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

        ResultTop resultTop = (ResultTop) o;

        if (paramId != resultTop.paramId) return false;
        if (result != resultTop.result) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = paramId;
        result1 = 31 * result1 + result;
        return result1;
    }
}
