package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Operation;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.service.OperationService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.OperationResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.UserResponseDto;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountFacadeImpl implements AccountFacade {

    private final AccountService accountService;
    private final UserService userService;
    private final OperationService operationService;

    public AccountFacadeImpl(AccountService accountService, UserService userService, OperationService operationService) {
        this.accountService = accountService;
        this.userService = userService;
        this.operationService = operationService;
    }

    @Override
    public Set<OperationResponseDto> getOperations(Long accountId) {
        Set<Operation> operations = accountService.getOperations(accountId);
        Set<OperationResponseDto> set = new HashSet<>();
        for (Operation operation : operations) {
            OperationResponseDto operationResponseDto = new OperationResponseDto(operation);
            set.add(operationResponseDto);
        }
        return set;
    }

    @Override
    public void addOperation(Long accountId, Long operationId) {
        accountService.addOperation(accountId, operationId);
    }

    @Override
    public void removeOperation(Long accountId, Long operationId) {
        accountService.removeOperation(accountId, operationId);
    }

    @Override
    public void create(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        if (accountRequestDto.getBalance() != null)
            account.setBalance(accountRequestDto.getBalance());
        else
            account.setBalance(0.0);
        account.setOperations(accountRequestDto.getOperations());
        account.setCreated(new Date());
        account.setUpdated(new Date());
        account.setOwner(accountRequestDto.getOwner());
        accountService.create(account);
    }

    @Override
    public void update(AccountRequestDto accountRequestDto, Long id) {
        Account account = accountService.findById(id);
        account.setBalance(accountRequestDto.getBalance());
        account.setUpdated(new Date());
        account.setOwner(accountRequestDto.getOwner());
        account.setOperations(accountRequestDto.getOperations());
        accountService.update(account);
    }

    @Override
    public void delete(Long id) {
        accountService.delete(id);
    }

    @Override
    public AccountResponseDto findById(Long id) {
        return new AccountResponseDto(accountService.findById(id));
    }

    @Override
    public PageData<AccountResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Account> all = accountService.findAll(dataTableRequest);

        List<AccountResponseDto> list = all.getItems().
                stream().
                map(AccountResponseDto::new).
                collect(Collectors.toList());

        PageData<AccountResponseDto> pageData = new PageData<>();
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
