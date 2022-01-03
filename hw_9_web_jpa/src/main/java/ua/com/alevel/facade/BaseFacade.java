package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.RequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.ResponseDto;

import java.util.List;

public interface BaseFacade<REQ extends RequestDto, RES extends ResponseDto> {

    void create(REQ req);

    void update(REQ req, long id);

    void delete(long id);

    RES findById(long id);

    List<RES> findAll();

    PageData<RES> findAll(WebRequest request);
}
