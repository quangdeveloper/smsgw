package vn.vnpay.dao;

import vn.vnpay.bean.Keyword;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.entities.KeywordQueryDto;
import vn.vnpay.modal.KeywordModal;
import vn.vnpay.search.KeywordSearch;

import java.util.List;

public interface KeywordDAO {
    List<KeywordModal> getKeywordByFilter(KeywordSearch keywordSearch);
    List<KeywordQueryDto> getAllKeyword();
    ResponseEnum createKeyword(Keyword keyword);
    ResponseEnum updateKeyword(Keyword keyword);
}
