package vn.vnpay.constant;

public enum Status {
    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active"),
    DELETED(2, "Deleted");
    private int code;
    private String name;

    Status(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Status getStatusByCode(int code) {
        for (Status status : Status.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
