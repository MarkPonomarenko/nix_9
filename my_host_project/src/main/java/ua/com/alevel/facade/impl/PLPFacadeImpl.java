package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import ua.com.alevel.config.annotations.InjectLog;
import ua.com.alevel.facade.PLPFacade;
import ua.com.alevel.logger.LoggerLevel;
import ua.com.alevel.logger.LoggerService;
import ua.com.alevel.persistence.entity.server.Server;
import ua.com.alevel.service.PLPService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.response.ServerPLPDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PLPFacadeImpl implements PLPFacade {

    @InjectLog
    private LoggerService loggerService;

    private final PLPService plpService;

    public PLPFacadeImpl(PLPService plpService) {
        this.plpService = plpService;
    }

    @Override
    public List<ServerPLPDto> search(WebRequest webRequest) {
        Map<String, Object> queryMap = new HashMap<>();
        if (webRequest.getParameterMap().get(WebUtil.PROVIDER_PARAM) != null) {
            String[] params = webRequest.getParameterMap().get(WebUtil.PROVIDER_PARAM);
            Long providerId = Long.parseLong(params[0]);
            queryMap.put(WebUtil.PROVIDER_PARAM, providerId);
            loggerService.commit(LoggerLevel.INFO, "add " + WebUtil.PROVIDER_PARAM + ": " + providerId);
        }
        if (webRequest.getParameterMap().get(WebUtil.SERVER_SEARCH_PARAM) != null) {
            String[] params = webRequest.getParameterMap().get(WebUtil.SERVER_SEARCH_PARAM);
            String serverName = params[0];
            queryMap.put(WebUtil.SERVER_SEARCH_PARAM, serverName);
            loggerService.commit(LoggerLevel.INFO, "add " + WebUtil.SERVER_SEARCH_PARAM + ": " + serverName);
        }
        List<Server> servers = plpService.search(queryMap);
        List<ServerPLPDto> serverPLPDtos = servers.stream().map(ServerPLPDto::new).toList();
        return serverPLPDtos;
    }
}
