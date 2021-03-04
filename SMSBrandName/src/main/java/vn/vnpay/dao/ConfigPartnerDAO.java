package vn.vnpay.dao;

import vn.vnpay.bean.PartnerConfig;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.entities.PartnerConfigQueryDto;
import vn.vnpay.modal.ConfigPartnerModal;
import vn.vnpay.search.ConfigPartnerSearch;

import java.util.List;

public interface ConfigPartnerDAO {
    List<ConfigPartnerModal> getConfigPartnerByFilter(ConfigPartnerSearch configPartnerSearch);
    List<PartnerConfigQueryDto> getAllConfigPartner();
    ResponseEnum createConfigPartner(PartnerConfig partnerConfig);
    ResponseEnum updateConfigPartner(PartnerConfig partnerConfig);
}
