package vn.vnpay.dao;

import vn.vnpay.bean.Routing;
import vn.vnpay.constant.ResponseEnum;
import vn.vnpay.entities.RoutingQueryDto;
import vn.vnpay.modal.RoutingModal;
import vn.vnpay.search.RoutingSearch;

import java.util.List;

public interface RoutingDAO {
    List<RoutingModal> getRoutingByFilter(RoutingSearch routingSearch);
    List<RoutingQueryDto> getAllRouting();
    ResponseEnum createRouting(Routing routing);
    ResponseEnum updateRouting(Routing routing);
}
