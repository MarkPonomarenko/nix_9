package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.UserResponseDto;

import java.util.Set;

public interface UserFacade extends BaseFacade<UserRequestDto, UserResponseDto>{

    Set<AccountResponseDto> getAccounts(Long userId);

    void addAccount(Long userId, Long accountId);

    void removeAccount(Long userId, Long accountId);

}
