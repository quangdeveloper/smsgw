package vn.vnpay.dao;

import vn.vnpay.bean.Business;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.modal.BusinessModal;
import vn.vnpay.search.BusinessSearch;

import java.util.List;

public interface BusinessDAO {
    List<BusinessModal> getBusinessByFilter(BusinessSearch businessSearch);
    ResponseEnum createBusiness(Business business);
    ResponseEnum updateBusiness(Business business);
}
