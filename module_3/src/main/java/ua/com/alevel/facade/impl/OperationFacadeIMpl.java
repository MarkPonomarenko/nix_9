package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.OperationFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Operation;
import ua.com.alevel.service.OperationService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.OperationRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.OperationResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationFacadeIMpl implements OperationFacade {

    private final OperationService operationService;

    public OperationFacadeIMpl(OperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    public void create(OperationRequestDto operationRequestDto) {
        Operation operation = new Operation();
        operation.setValue(operationRequestDto.getValue());
        operation.setAccountIn(operationRequestDto.getAccount());
        operation.setCreated(new Date());
        operationService.create(operation);
    }

    @Override
    public void update(OperationRequestDto operationRequestDto, Long id) {
        Operation operation = operationService.findById(id);
        operation.setValue(operationRequestDto.getValue());
        operation.setAccountIn(operationRequestDto.getAccount());
        operation.setCreated(new Date());
        operationService.create(operation);
    }

    @Override
    public void delete(Long id) {
        operationService.delete(id);
    }

    @Override
    public OperationResponseDto findById(Long id) {
        return new OperationResponseDto(operationService.findById(id));
    }

    @Override
    public PageData<OperationResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Operation> all = operationService.findAll(dataTableRequest);

        List<OperationResponseDto> list = all.getItems().
                stream().
                map(OperationResponseDto::new).
                collect(Collectors.toList());

        PageData<OperationResponseDto> pageData = new PageData<>();
        pageData.setItems(list);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(all.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());

        return pageData;
    }
}
