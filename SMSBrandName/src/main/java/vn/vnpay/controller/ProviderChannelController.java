package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.vnpay.bean.ProviderChannel;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.ProviderChannelIMP;
import vn.vnpay.entities.ChannelQueryDto;
import vn.vnpay.modal.ProviderChannelModal;
import vn.vnpay.search.ProviderChannelSearch;
import vn.vnpay.validator.ProviderChannelValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class ProviderChannelController {
    private static final Logger LOG = LogManager.getLogger(ProviderChannelController.class);

    @Autowired
    ProviderChannelIMP providerChannelIMP;

    @RequestMapping(value = "/channel", method = RequestMethod.POST)
    public List<ProviderChannelModal> getChannel(@RequestBody ProviderChannelSearch providerChannelSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));

        return providerChannelIMP.getChannelByFilter(providerChannelSearch);
    }

    @RequestMapping(value = "/create_provider_channel", method = RequestMethod.POST)
    public Response create(@RequestBody ProviderChannel providerChannel) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create provider channel with request: {}", GsonCustom.getGsonBuilder().toJson(providerChannel));
        Map<String, String> mapError = ProviderChannelValidate.getInstance().validateInput(providerChannel, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(providerChannelIMP.createProviderChannel(providerChannel));
    }

    @RequestMapping(value = "/update_provider_channel", method = RequestMethod.POST)
    public Response update(@RequestBody ProviderChannel providerChannel) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update provider channel with request: {}", GsonCustom.getGsonBuilder().toJson(providerChannel));
        Map<String, String> mapError = ProviderChannelValidate.getInstance().validateInput(providerChannel, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(providerChannelIMP.updateProviderChannel(providerChannel));
    }
}
