package vn.vnpay.dao;

import vn.vnpay.bean.ProviderKey;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.ProviderKeyModal;
import vn.vnpay.search.ProviderKeySearch;

import java.util.List;

public interface ProviderKeyDAO {
    List<ProviderKeyModal> getProviderKeyByFilter(ProviderKeySearch providerKeySearch);
    ResponseEnum createProviderKey(ProviderKey providerKey);
    ResponseEnum updateProviderKey(ProviderKey providerKey);
}
