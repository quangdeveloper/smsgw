package vn.vnpay.dao;

import vn.vnpay.bean.SenderProvider;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.SenderProviderModal;
import vn.vnpay.search.SenderProviderSearch;

import java.util.List;

public interface SenderProviderDAO {
    List<SenderProviderModal> getSenderProviderByFilter(SenderProviderSearch senderProviderSearch);
    ResponseEnum createSenderProvider(SenderProvider senderProvider);
    ResponseEnum updateSenderProvider(SenderProvider senderProvider);
}
