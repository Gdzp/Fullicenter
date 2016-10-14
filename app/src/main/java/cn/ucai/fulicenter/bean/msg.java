package cn.ucai.fulicenter.bean;

/**
 * Created by Think on 2016/10/14.
 */
public class msg {
    /**
     * success : true
     * msg :
     */

    private boolean success;
    private String msg;

    public msg(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public msg(boolean success) {

        this.success = success;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "msg{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
