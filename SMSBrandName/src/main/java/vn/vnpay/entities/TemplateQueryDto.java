package vn.vnpay.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TemplateQueryDto {
    private String PARTNER_ID;
    private String PROVIDER_ID;
    private String LABELID;
    private String LABEL;
    private String TEMPLATE;
    private String TEMPLATEID;
    private String CONTRACTID;
    private String CONTRACTTYPEID;
    private String KEYWORD;
    private String STATUS;
    private long START_DATE;
    private long END_DATE;

    @Override
    public String toString() {
        return "TemplateQueryDto{" + "PARTNER_ID=" + PARTNER_ID + ", PROVIDER_ID=" + PROVIDER_ID + ", LABELID=" + LABELID + ", LABEL=" + LABEL + ", TEMPLATE=" + TEMPLATE + ", TEMPLATEID=" + TEMPLATEID + ", CONTRACTID=" + CONTRACTID + ", CONTRACTTYPEID=" + CONTRACTTYPEID + ", KEYWORD=" + KEYWORD + ", STATUS=" + STATUS + ", START_DATE=" + START_DATE + ", END_DATE=" + END_DATE + '}';
    }


}
