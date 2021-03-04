package vn.vnpay.common;

import com.google.common.base.Strings;

import java.sql.Timestamp;

public class Convert {
    private static Convert instance;
    private Convert(){

    };
    public static Convert getInstance(){
        if(instance == null){
            synchronized (Convert.class){
                if(instance == null){
                    instance = new Convert();
                }
            }
        }
        return instance;
    }

    public Integer convertStringToInteger(String str){
        if(Strings.isNullOrEmpty(str)){
            return  null;
        }
        return Integer.parseInt(str);
    }

    public Long convertTimeStampToUnix(String time){
        Timestamp timestamp = Timestamp.valueOf(time);
        return timestamp.getTime()/1000L;
    }
    public Timestamp convertTimeStampJavaToPostgresql(String time){
        Timestamp timestamp = Timestamp.valueOf(time);
        Long timeUnix = timestamp.getTime()/1000L + 7*3600;
        return new Timestamp(timeUnix*1000L);
    }
}
