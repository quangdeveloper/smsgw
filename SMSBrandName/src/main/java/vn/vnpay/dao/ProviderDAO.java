package vn.vnpay.dao;

import vn.vnpay.bean.Provider;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.entities.ProviderQueryDto;
import vn.vnpay.modal.ProviderModal;
import vn.vnpay.search.ProviderSearch;

import java.util.List;

public interface ProviderDAO {
    List<ProviderModal> getProviderByFilter(ProviderSearch providerSearch);
    List<ProviderQueryDto> getAllProvider();
    ResponseEnum createProvider(Provider provider);
    ResponseEnum updateProvider(Provider provider);
}
