package vn.vnpay.dao;

import vn.vnpay.bean.Sender;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.entities.SenderQueryDto;
import vn.vnpay.modal.SenderModal;
import vn.vnpay.search.SenderSearch;


import java.util.List;

public interface SenderDAO {
    List<SenderModal> getSenderByFilter(SenderSearch senderSearch);
    List<SenderQueryDto> getAllSender();
    ResponseEnum createSender(Sender sender);
    ResponseEnum updateSender(Sender sender);
}