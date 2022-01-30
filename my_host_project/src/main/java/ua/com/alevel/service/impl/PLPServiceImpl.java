package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.provider.Provider;
import ua.com.alevel.persistence.entity.server.Server;
import ua.com.alevel.persistence.repository.server.ProviderRepository;
import ua.com.alevel.persistence.repository.server.ServerRepository;
import ua.com.alevel.service.PLPService;
import ua.com.alevel.util.WebUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PLPServiceImpl implements PLPService {

    private final ServerRepository serverRepository;

    private final ProviderRepository providerRepository;

    public PLPServiceImpl(ServerRepository serverRepository, ProviderRepository providerRepository) {
        this.serverRepository = serverRepository;
        this.providerRepository = providerRepository;
    }


    @Override
    public List<Server> search(Map<String, Object> queryMap) {
        if (queryMap.get(WebUtil.PROVIDER_PARAM) != null) {
            Long providerId = (Long) queryMap.get(WebUtil.PROVIDER_PARAM);
            Optional<Provider> provider = providerRepository.findById(providerId);
            if (provider.isEmpty()) {
                throw new EntityNotFoundException("this provider not found");
            }
            return serverRepository.findByProvider(provider.get());
        }
        if (queryMap.get(WebUtil.SERVER_SEARCH_PARAM) != null) {
            String serverName = (String) queryMap.get(WebUtil.SERVER_SEARCH_PARAM);
            return serverRepository.findByCpuModelContaining(serverName);
        }
        return serverRepository.findAll();
    }
}
