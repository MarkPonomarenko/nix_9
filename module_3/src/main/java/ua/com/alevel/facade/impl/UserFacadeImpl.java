package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.UserResponseDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final AccountService accountService;

    public UserFacadeImpl(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void create(UserRequestDto userRequestDto) {
        User user = new User();
        user.setPassword(userRequestDto.getPassword());
        user.setName(userRequestDto.getName());
        user.setPhone(userRequestDto.getPhone());
        user.setAccounts(userRequestDto.getAccounts());
        userService.create(user);
    }

    @Override
    public void update(UserRequestDto userRequestDto, Long id) {
        User user = userService.findById(id);
        user.setName(userRequestDto.getName());
        user.setPassword(userRequestDto.getPassword());
        user.setPhone(userRequestDto.getPhone());
        user.setAccounts(userRequestDto.getAccounts());
        userService.update(user);
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

    @Override
    public UserResponseDto findById(Long id) {
        return new UserResponseDto(userService.findById(id));
    }

    @Override
    public PageData<UserResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<User> all = userService.findAll(dataTableRequest);

        List<UserResponseDto> list = all.getItems().
                stream().
                map(UserResponseDto::new).
                collect(Collectors.toList());

        PageData<UserResponseDto> pageData = new PageData<>();
        pageData.setItems(list);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(all.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());

        return pageData;
    }

    @Override
    public Set<AccountResponseDto> getAccounts(Long userId) {
        Set<Account> accounts = userService.getAccounts(userId);
        Set<AccountResponseDto> set = new HashSet<>();
        for (Account account : accounts) {
            AccountResponseDto accountResponseDto = new AccountResponseDto(account);
            set.add(accountResponseDto);
        }
        return set;
    }

    @Override
    public void addAccount(Long userId, Long accountId) {
        userService.addAccount(userId, accountId);
    }

    @Override
    public void removeAccount(Long userId, Long accountId) {
        userService.removeAccount(userId, accountId);
    }
}
