package vn.vnpay.constant;

public enum ResponseEnum {
    OK("200", "SUCCESS"),
    ERROR_VALIDATE("201", "ERROR VALIDATE"),
    ERROR_INSERT("202", "ERROR INSERT"),
    ERROR_DUPLICATE("203", "KEY EXISTS"),
    ERROR_RELOAD("204", "ERROR RELOAD"),
    ERROR_UPDATE("205", "ERROR UPDATE"),
    ERROR_DELETE("206", "ERROR DELETE"),
    INTERNAL_SERVER("500","INTERNAL SERVER");

    private String code;
    private String name;

    ResponseEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ResponseEnum getResponseByCode(String code) {
        for (ResponseEnum responseEnum : ResponseEnum.values()) {
            if (responseEnum.code.equalsIgnoreCase(code)) {
                return responseEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
