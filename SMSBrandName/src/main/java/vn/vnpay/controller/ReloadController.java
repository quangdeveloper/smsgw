package vn.vnpay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.common.LogCommon;
import vn.vnpay.entities.ReloadRequest;
import vn.vnpay.entities.ReloadResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/smsgw")
public class ReloadController {
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/reload", method = RequestMethod.POST)
    public ReloadResponse reload(@RequestBody ReloadRequest request) {
        ThreadContext.put(LogCommon.TOKEN, UUID.randomUUID().toString().replaceAll("-", ""));
        return DataController.getInstance().reloadData(request, getClientIp()); //fixme clienIP
    }

    private String getClientIp() {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println(remoteAddr);
        return remoteAddr;
    }
}
