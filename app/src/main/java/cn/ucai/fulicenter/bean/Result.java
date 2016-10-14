package cn.ucai.fulicenter.bean;

/**
 * Created by Think on 2016/10/14.
 */
public class Result {



    private int retCode;
    private boolean retMsg;
    private Object retData;

    public Result(int retCode, boolean retMsg, Object retData) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retData = retData;
    }

    public Result(int retCode) {

        this.retCode = retCode;
    }

    public int getRetCode() {

        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public boolean isRetMsg() {
        return retMsg;
    }

    public void setRetMsg(boolean retMsg) {
        this.retMsg = retMsg;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "Result{" +
                "retCode=" + retCode +
                ", retMsg=" + retMsg +
                ", retData=" + retData +
                '}';
    }
}
