package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.web.dto.response.ServerPLPDto;

import java.util.List;

public interface PLPFacade {

    List<ServerPLPDto> search(WebRequest webRequest);
}
