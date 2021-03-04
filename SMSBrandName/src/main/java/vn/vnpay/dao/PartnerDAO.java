package vn.vnpay.dao;

import vn.vnpay.bean.Partner;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.entities.PartnerQueryDto;
import vn.vnpay.modal.PartnerModal;
import vn.vnpay.search.PartnerSearch;

import java.util.List;

public interface PartnerDAO {
    List<PartnerModal> getPartnerByFilter(PartnerSearch partnerSearch);
    List<PartnerQueryDto> getAllPartner();
    ResponseEnum createPartner(Partner partner);
    ResponseEnum updatePartner(Partner partner);
}
