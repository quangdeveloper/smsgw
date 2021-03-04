package vn.vnpay.controller;

import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import vn.vnpay.common.ApiProperties;
import vn.vnpay.common.GsonCustom;
import vn.vnpay.common.LogCommon;
import vn.vnpay.config.ApiConfig;
import vn.vnpay.constant.CommandCode;
import vn.vnpay.constant.Respcode;
import vn.vnpay.daoimp.*;
import vn.vnpay.entities.*;
import vn.vnpay.manager.CommandManager;

import java.util.List;

public class DataController {
    private static final Logger LOG = LogManager.getLogger(DataController.class);
    static String TEST = "TEST";
    private static final String KEY_PARTNER = "VNPAY_SMSMNP_SYSTEM_PARTNERS" + TEST; //fixme
    private static final String KEY_CHANNEL = "VNPAY_SMSMNP_SYSTEM_CHANNELS" + TEST;
    private static final String KEY_PROVIDER_PREFIX = "VNPAY_SMSMNP_SYSTEM_PROVIDER_PREFIX" + TEST;
    private static final String KEY_PROVIDER = "VNPAY_SMSMNP_SYSTEM_PROVIDER" + TEST;
    private static final String KEY_ROUTING = "VNPAY_SMSMNP_SYSTEM_ROUTINGS" + TEST;
    private static final String KEY_KEYWORD = "VNPAY_SMSMNP_SYSTEM_KEYWORDS" + TEST;
    private static final String KEY_SENDER = "VNPAY_SMSMNP_SYSTEM_SENDERS" + TEST;
    private static final String KEY_CONFIG_PARTNER = "VNPAY_SMSMNP_SYSTEM_PARTNER_CONFIGS" + TEST;

    private static DataController instance;

    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private boolean reloadPartner() {
        PartnerIMP partnerIMP = new PartnerIMP();
        try {
            List<PartnerQueryDto> list = partnerIMP.getAllPartner();
            if (list == null || list.size() == 0) {
                LOG.warn("list partner is empty");
                return false;
            }
            String sPartner = GsonCustom.getGsonBuilder().toJson(list,
                    new TypeToken<List<PartnerQueryDto>>() {
                    }.getType());
            LOG.info("Set key: {}, value: {}", KEY_PARTNER, sPartner);
            String result = CommandManager.getInstance().set(KEY_PARTNER, sPartner);
            List<PartnerQueryDto> listResult = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get(KEY_PARTNER),
                    new TypeToken<List<PartnerQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult));
            return !result.isEmpty();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return false;
        }
    }

    private boolean reloadChannel() {
        ProviderChannelIMP providerChannelIMP = new ProviderChannelIMP();
        try {
            List<ChannelQueryDto> list = providerChannelIMP.getAllChannel();
            if (list == null || list.size() == 0) {
                LOG.warn("list channel is empty");
                return false;
            }
            String sPartner = GsonCustom.getGsonBuilder().toJson(list,
                    new TypeToken<List<ChannelQueryDto>>() {
                    }.getType());
            LOG.info("Set key: {}, value: {}", KEY_CHANNEL, sPartner);
            String result = CommandManager.getInstance().set(KEY_CHANNEL, sPartner);
            List<ChannelQueryDto> listResult = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get(KEY_CHANNEL),
                    new TypeToken<List<ChannelQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult));
            return !result.isEmpty();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return false;
        }
    }

    private boolean reloadPrefix() {
        ProviderPrefixIMP providerPrefixIMP = new ProviderPrefixIMP();
        try {
            List<PrefixQueryDto> list = providerPrefixIMP.getAllProviderPrefix();
            if (list == null || list.size() == 0) {
                LOG.warn("list prefix is empty");
                return false;
            }
            String sPartner = GsonCustom.getGsonBuilder().toJson(list,
                    new TypeToken<List<PrefixQueryDto>>() {
                    }.getType());
            LOG.info("Set key: {}, value: {}", KEY_PROVIDER_PREFIX, sPartner);
            String result = CommandManager.getInstance().set(KEY_PROVIDER_PREFIX, sPartner);
            List<PrefixQueryDto> listResult = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get(KEY_PROVIDER_PREFIX),
                    new TypeToken<List<PrefixQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult));
            return !result.isEmpty();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return false;
        }
    }

    private boolean reloadProvider() {
        ProviderIMP providerIMP = new ProviderIMP();
        try {
            List<ProviderQueryDto> list = providerIMP.getAllProvider();
            if (list == null || list.size() == 0) {
                LOG.warn("list provider is empty");
                return false;
            }
            String sPartner = GsonCustom.getGsonBuilder().toJson(list,
                    new TypeToken<List<ProviderQueryDto>>() {
                    }.getType());
            LOG.info("Set key: {}, value: {}", KEY_PROVIDER, sPartner);
            String result = CommandManager.getInstance().set(KEY_PROVIDER, sPartner);
            List<ProviderQueryDto> listResult = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get(KEY_PROVIDER),
                    new TypeToken<List<ProviderQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult));
            return !result.isEmpty();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return false;
        }
    }

    private boolean reloadRouting() {
        RoutingIMP routingIMP = new RoutingIMP();
        try {
            List<RoutingQueryDto> list = routingIMP.getAllRouting();
            if (list == null || list.size() == 0) {
                LOG.warn("list routing is empty");
                return false;
            }
            String sPartner = GsonCustom.getGsonBuilder().toJson(list,
                    new TypeToken<List<RoutingQueryDto>>() {
                    }.getType());
            LOG.info("Set key: {}, value: {}", KEY_ROUTING, sPartner);
            String result = CommandManager.getInstance().set(KEY_ROUTING, sPartner);
            List<RoutingQueryDto> listResult = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get(KEY_ROUTING),
                    new TypeToken<List<RoutingQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult));
            return !result.isEmpty();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return false;
        }
    }

    private boolean reloadKeyword() {
        KeywordIMP keywordIMP = new KeywordIMP();
        try {
            List<KeywordQueryDto> list = keywordIMP.getAllKeyword();
            if (list == null || list.size() == 0) {
                LOG.warn("list keyword is empty");
                return false;
            }
            String sPartner = GsonCustom.getGsonBuilder().toJson(list,
                    new TypeToken<List<KeywordQueryDto>>() {
                    }.getType());
            LOG.info("Set key: {}, value: {}", KEY_KEYWORD, sPartner);
            String result = CommandManager.getInstance().set(KEY_KEYWORD, sPartner);
            List<KeywordQueryDto> listResult = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get(KEY_KEYWORD),
                    new TypeToken<List<KeywordQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult));
            return !result.isEmpty();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return false;
        }
    }

    private boolean reloadSender() {
        SenderIMP senderIMP = new SenderIMP();
        try {
            List<SenderQueryDto> list = senderIMP.getAllSender();
            if (list == null || list.size() == 0) {
                LOG.warn("list sender is empty");
                return false;
            }
            String sPartner = GsonCustom.getGsonBuilder().toJson(list,
                    new TypeToken<List<SenderQueryDto>>() {
                    }.getType());
            LOG.info("Set key: {}, value: {}", KEY_SENDER, sPartner);
            String result = CommandManager.getInstance().set(KEY_SENDER, sPartner);
            List<SenderQueryDto> listResult = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get(KEY_SENDER),
                    new TypeToken<List<SenderQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult));

            List<PartnerConfigQueryDto> listResult2 = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get("VNPAY_SMSMNP_SYSTEM_PARTNER_CONFIGS"),
                    new TypeToken<List<PartnerConfigQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult2));
            return !result.isEmpty();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return false;
        }
    }

    private boolean reloadPartnerConfig() {
        ConfigPartnerIMP configPartnerIMP = new ConfigPartnerIMP();
        try {
            List<PartnerConfigQueryDto> list = configPartnerIMP.getAllConfigPartner();
            if (list == null || list.size() == 0) {
                LOG.warn("list config partner is empty");
                return false;
            }
            String sPartner = GsonCustom.getGsonBuilder().toJson(list,
                    new TypeToken<List<PartnerConfigQueryDto>>() {
                    }.getType());
            LOG.info("Set key: {}, value: {}", KEY_CONFIG_PARTNER, sPartner);
            String result = CommandManager.getInstance().set(KEY_CONFIG_PARTNER, sPartner);
            List<PartnerConfigQueryDto> listResult = GsonCustom.getGsonBuilder().fromJson(CommandManager.getInstance().get(KEY_CONFIG_PARTNER),
                    new TypeToken<List<PartnerConfigQueryDto>>() {
                    }.getType());
            System.out.println(GsonCustom.getGsonBuilder().toJson(listResult));
            return !result.isEmpty();
        } catch (Exception ex) {
            LOG.error("Error ", ex);
            return false;
        }
    }

    public ReloadResponse reloadData(ReloadRequest request, String clientIP) {
        LOG.info("Begin reload with input: {}", GsonCustom.getGsonBuilder().toJson(request));
        ReloadResponse resp = ReloadResponse.builder().token(ThreadContext.get(LogCommon.TOKEN)).build();

        if (checkIpAllow(clientIP)) {
            resp.setRespCode(Respcode.IP_NOT_REGISTERED.code());
            resp.setDescription(Respcode.IP_NOT_REGISTERED.description());
            return resp;
        }
        if (!validateAuthen(request.getApiuser(), request.getApikey())) {
            resp.setRespCode(Respcode.INVALID_AUTHEN.code());
            resp.setDescription(Respcode.INVALID_AUTHEN.description());
            return resp;
        }

        boolean result;
        switch (request.getCommandCode()) {
            case CommandCode.CHANNEL_RELOAD:
                result = reloadChannel();
                break;
            case CommandCode.PARTNER_RELOAD:
                result = reloadPartner();
                break;
            case CommandCode.PARTNER_CONFIG_RELOAD:
                result = reloadPartnerConfig();
                break;
            case CommandCode.PREFIX_RELOAD:
                result = reloadPrefix();
                break;
            case CommandCode.PROVIDER_RELOAD:
                result = reloadProvider();
                break;
            case CommandCode.ROUTING_RELOAD:
                result = reloadRouting();
                break;
//            case CommandCode.TEMPLATE_RELOAD:
//                result = reloadTemplate(requ.getToken());
//                break;
            case CommandCode.KEYWORD_RELOAD:
                result = reloadKeyword();
                break;
            case CommandCode.SENDER_RELOAD:
                result = reloadSender();
                break;
            default:
                result = false;
        }

        if (result) {
            resp.setRespCode(Respcode.SUCCESS.code());
            resp.setDescription(request.getCommandCode() + "_" + Respcode.SUCCESS.description());
        } else {
            resp.setRespCode(Respcode.SYSTEM_ERROR.code());
            resp.setDescription(request.getCommandCode() + "_" + Respcode.SYSTEM_ERROR.description());
        }
        return resp;
    }

    private boolean validateAuthen(String user, String key) {
        return user.equals(ApiProperties.getProperties().getProperty(ApiConfig.API_USER))
                && key.equals(ApiProperties.getProperties().getProperty(ApiConfig.API_KEY));
    }

    private boolean checkIpAllow(String clientIP) {
        return Boolean.parseBoolean(ApiProperties.getProperties().getProperty(ApiConfig.IS_CHECK_IP))
                && !ApiProperties.getProperties().getProperty(ApiConfig.IP_WHITE_LIST).contains(clientIP);
    }
}
