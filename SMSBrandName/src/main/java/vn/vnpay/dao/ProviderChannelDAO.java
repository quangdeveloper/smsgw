package vn.vnpay.dao;

import vn.vnpay.bean.ProviderChannel;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.entities.ChannelQueryDto;
import vn.vnpay.modal.ProviderChannelModal;
import vn.vnpay.search.ProviderChannelSearch;

import java.util.List;

public interface ProviderChannelDAO {
    List<ProviderChannelModal> getChannelByFilter(ProviderChannelSearch providerChannelSearch);
    List<ChannelQueryDto> getAllChannel();
    ResponseEnum createProviderChannel(ProviderChannel providerChannel);
    ResponseEnum updateProviderChannel(ProviderChannel providerChannel);

}
