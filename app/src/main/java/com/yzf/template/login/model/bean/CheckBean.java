package com.yzf.template.login.model.bean;

/**
 * 校验bean
 *
 * @author Yzf
 * @date 2018-7-25 10:16:08
 */
public class CheckBean {

    /**
     * result : 1
     */

    private String result;
    private String img;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
