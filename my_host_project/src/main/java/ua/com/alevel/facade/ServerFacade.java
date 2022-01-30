package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.web.dto.request.ServerRequestDto;
import ua.com.alevel.web.dto.response.ServerResponseDto;

import java.util.List;

public interface ServerFacade extends CrudFacade<ServerRequestDto, ServerResponseDto> {

//    List<ServerResponseDto> search(WebRequest request);
}
