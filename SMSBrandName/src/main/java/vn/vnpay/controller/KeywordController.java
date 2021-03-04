package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.bean.Keyword;
import vn.vnpay.common.*;
import vn.vnpay.constant.Action;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.daoimp.KeywordIMP;
import vn.vnpay.modal.KeywordModal;
import vn.vnpay.search.KeywordSearch;
import vn.vnpay.validator.KeywordValidate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class KeywordController {

    private static final Logger LOG = LogManager.getLogger(KeywordController.class);

    @Autowired
    KeywordIMP keywordIMP;

    @RequestMapping(value = "/get_keyword_by_filter", method = RequestMethod.POST)
    public List<KeywordModal> getKeyword(@RequestBody KeywordSearch keywordSearch) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return keywordIMP.getKeywordByFilter(keywordSearch);
    }

    @RequestMapping(value = "/create_keyword", method = RequestMethod.POST)
    public Response create(@RequestBody Keyword keyword) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin create keyword with request: {}", GsonCustom.getGsonBuilder().toJson(keyword));
        Map<String, String> mapError = KeywordValidate.getInstance().validateInput(keyword, Action.INSERT);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(keywordIMP.createKeyword(keyword));
    }

    @RequestMapping(value = "/update_keyword", method = RequestMethod.POST)
    public Response update(@RequestBody Keyword keyword) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        LOG.info("Begin update keyword with request: {}", GsonCustom.getGsonBuilder().toJson(keyword));
        Map<String, String> mapError = KeywordValidate.getInstance().validateInput(keyword, Action.UPDATE);
        Response response = new Response();
        if (mapError.size() > 0) {
            response.setCode(ResponseEnum.ERROR_VALIDATE.getCode());
            response.setDescription(GsonCustom.getGsonBuilder().toJson(mapError));
            LOG.warn("Error validate: {}", GsonCustom.getGsonBuilder().toJson(mapError));
            return response;
        }
        return response.buildResponse(keywordIMP.updateKeyword(keyword));
    }
}
