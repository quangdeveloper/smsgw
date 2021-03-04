package vn.vnpay.constant;

public enum Respcode {

    SUCCESS("00") {
        @Override
        public String description() {
            return "SUCCESS";
        }
    },
    INVALID_MOBILENO("01") {
        @Override
        public String description() {
            return "INVALID_MOBILENO";
        }
    },
    INVALID_LEN("02") {
        @Override
        public String description() {
            return "INVALID_LEN";
        }
    },   
    INVALID_AUTHEN("04") {
        @Override
        public String description() {
            return "INVALID_AUTHEN";
        }
    },
    PROVIDER_NOT_CONNECT("05") {
        @Override
        public String description() {
            return "PROVIDER_NOT_CONNECT";
        }
    },
    IP_NOT_REGISTERED("06") {
        @Override
        public String description() {
            return "IP_NOT_REGISTERED";
        }
    },
    PROVIDER_TIMEOUT("08") {
        @Override
        public String description() {
            return "PROVIDER_TIMEOUT";
        }
    },
    INVALID_TYPE("11") {
        @Override
        public String description() {
            return "INVALID_TYPE";
        }
    },
    PROVIDER_NOT_REGISTERED("12") {
        @Override
        public String description() {
            return "PROVIDER_NOT_REGISTERED";
        }
    },
    AMOUNT_INVALID("13") {
        @Override
        public String description() {
            return "AMOUNT_INVALID";
        }
    },
    MAC_INVALID("79") {
        @Override
        public String description() {
            return "MAC_INVALID";
        }
    },
    PARTNER_NOT_FOUND("80") {
        @Override
        public String description() {
            return "PARTNER_NOT_FOUND";
        }
    },
    PARTNER_NOT_SUPPORT("81") {
        @Override
        public String description() {
            return "PARTNER_NOT_SUPPORT";
        }
    },
    PROVIDER_NOT_FOUND("82") {
        @Override
        public String description() {
            return "ProviderCode not found.";
        }
    },
    PROVIDER_NOT_SUPPORT("83") {
        @Override
        public String description() {
            return "PROVIDER_NOT_SUPPORT";
        }
    },
    ROUTING_NOT_FOUND("84") {
        @Override
        public String description() {
            return "ROUTING_NOT_FOUND";
        }
    },
    INVALID_SENDER("85") {
        @Override
        public String description() {
            return "INVALID_SENDER";
        }
    },   
    INVALID_KEYWORD("86") {
        @Override
        public String description() {
            return "INVALID_KEYWORD";
        }
    },
    INVALID_TEMPLATE("87") {
        @Override
        public String description() {
            return "INVALID_TEMPLATE";
        }
    },
    
    MESSAGE_NOT_ENCRYPT("88") {
        @Override
        public String description() {
            return "MESSAGE_NOT_ENCRYPT";
        }
    },
    
    
    MESSAGE_ENCRYPT("89") {
        @Override
        public String description() {
            return "MESSAGE_ENCRYPT";
        }
    },
    
    
    
    DUPLICATE_TRANSACTION("90") {
        @Override
        public String description() {
            return "DUPLICATE_TRANSACTION";
        }
    },
    SYSTEM_ERROR("96") {
        @Override
        public String description() {
            return "SYSTEM_ERROR";
        }
    },
    INVALID_DATA("97") {
        @Override
        public String description() {
            return "INVALID_DATA";
        }
    },
    TRANS_EXPIRE("98") {
        @Override
        public String description() {
            return "Transaction expiration.";
        }
    },
    EXCEPTION("99") {
        @Override
        public String description() {
            return "EXCEPTION";
        }
    },
    TEMPLATE_NOT_ACTIVE("-1") {
        @Override
        public String description() {
            return "TEMPLATE_NOT_ACTIVE";
        }
    },
    CARD_NOT_VALID("62") {
        @Override
        public String description() {
            return "Card not Valid.";
        }
    },
    
    UNICODE_NOT_SUPPORT("12") {
        @Override
        public String description() {
            return "UNICODE_NOT_SUPPORT";
        }
    } 
    
    ;
    
    private final String i;

    Respcode(String i) {
        this.i = i;
    }

    public String code() {
        return i;
    }

    public abstract String description();

}
