package vn.vnpay.dao;

import vn.vnpay.bean.SenderPartner;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.SenderPartnerModal;
import vn.vnpay.search.SenderPartnerSearch;


import java.util.List;

public interface SenderPartnerDAO {
    List<SenderPartnerModal> getSenderPartnerByFilter(SenderPartnerSearch senderPartnerSearch);
    ResponseEnum createSenderPartner(SenderPartner senderPartner);
    ResponseEnum updateSenderPartner(SenderPartner senderPartner);
}
