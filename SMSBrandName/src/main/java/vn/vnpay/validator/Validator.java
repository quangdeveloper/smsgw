package vn.vnpay.validator;

import com.google.common.base.Strings;
import vn.vnpay.common.ErrorCustom;

import java.sql.Timestamp;

public abstract class Validator extends ErrorCustom {
    protected static final String EMPTY = "";

    protected int getLengthByte(String str) {
        if (Strings.isNullOrEmpty(str)) return 0;
        return str.getBytes().length;
    }

    protected String validateMaxLengthStr(String value, int maxLengthByte) {
        if (getLengthByte(value) > maxLengthByte) return INVALID_LENGTH;
        return EMPTY;
    }

    protected String validateNullOrMaxLengthStr(String value, int maxLengthByte) {
        if (Strings.isNullOrEmpty(value)) return REQUIRED;
        if (getLengthByte(value) > maxLengthByte) return INVALID_LENGTH;
        return EMPTY;
    }

    protected String validateNullOrMaxLengthLong(String value) {
        if (Strings.isNullOrEmpty(value)) return REQUIRED;
        try {
            Long.parseLong(value);
        } catch (Exception e) {
            return INVALID;
        }
        return EMPTY;
    }

    protected String validateNullOrMaxLengthInt(String value, int maxLengthByte) {
        if (Strings.isNullOrEmpty(value)) return REQUIRED;
        String regex = "^\\d+$";
        if (!value.matches(regex)) return INVALID;
        if (getLengthByte(value) > maxLengthByte) return INVALID_LENGTH;
        return EMPTY;
    }

    protected String validateInt(String value) {
        if(Strings.isNullOrEmpty(value)) return EMPTY;
        String regex = "^\\d*$";
        if (!value.matches(regex)) return INVALID;
        return EMPTY;
    }

    protected String checkRequired(String value) {
        if (Strings.isNullOrEmpty(value)) return REQUIRED;
        return EMPTY;
    }

    protected String validateTimestamp(String value) {
        try {
            Timestamp.valueOf(value);
        } catch (Exception e) {
            return INVALID;
        }
        return EMPTY;
    }

}
