package ua.com.alevel.facade;

import ua.com.alevel.web.dto.request.ServerRequestDto;
import ua.com.alevel.web.dto.response.ServerResponseDto;

public interface ServerFacade extends CrudFacade<ServerRequestDto, ServerResponseDto> {

//    List<ServerResponseDto> search(WebRequest request);
}
