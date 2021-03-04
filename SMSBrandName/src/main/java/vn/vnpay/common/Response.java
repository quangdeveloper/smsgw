package vn.vnpay.common;

import vn.vnpay.constant.ResponseEnum;

public class Response {
    private String code;
    private String description;

    public Response buildResponse(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.description = responseEnum.getName();
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
