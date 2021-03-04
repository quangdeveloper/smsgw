package vn.vnpay.dao;


import vn.vnpay.bean.ProviderPrefix;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.entities.PrefixQueryDto;
import vn.vnpay.modal.ProviderPrefixModal;
import vn.vnpay.search.ProviderPrefixSearch;

import java.util.List;

public interface ProviderPrefixDAO {
    List<ProviderPrefixModal> getProviderPrefixByFilter(ProviderPrefixSearch providerPrefixSearch);
    List<PrefixQueryDto> getAllProviderPrefix();
    ResponseEnum createProviderPrefix(ProviderPrefix providerPrefix);
    ResponseEnum updateProviderPrefix(ProviderPrefix providerPrefix);
}
