package ua.com.alevel.facade;

import ua.com.alevel.persistence.entity.provider.Provider;
import ua.com.alevel.web.dto.request.ProviderRequestDto;
import ua.com.alevel.web.dto.response.ProviderResponseDto;

import java.util.List;

public interface ProviderFacade extends CrudFacade<ProviderRequestDto, ProviderResponseDto> {

    List<Provider> findAll();
}
