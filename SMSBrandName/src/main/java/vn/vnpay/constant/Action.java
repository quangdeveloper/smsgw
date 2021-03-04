package vn.vnpay.constant;

public enum Action {
    INSERT((byte) 0, "INSERT"),
    UPDATE((byte) 1, "UPDATE"),
    DELETE((byte)2, "DELETE"),
    UPDATE_PASSWORD((byte)3, "UPDATE_PASSWORD");

    private byte code;
    private String name;

    Action(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
