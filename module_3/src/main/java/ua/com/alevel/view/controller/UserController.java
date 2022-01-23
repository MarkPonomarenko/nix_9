package ua.com.alevel.view.controller;


import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.UserResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ua.com.alevel.util.WebRequestUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/users")
public class UserController extends AbstractController{

    private final UserFacade userFacade;
    private final AccountFacade accountFacade;

    public UserController(UserFacade userFacade, AccountFacade accountFacade) {
        this.userFacade = userFacade;
        this.accountFacade = accountFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        HeaderName[] columnNames = new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("name", "name", "name"),
                new HeaderName("phone", "phone", "phone"),
                new HeaderName("created", "created", "created"),
                new HeaderName("", null, null),
                new HeaderName("", null, null)
        };
        PageData<UserResponseDto> response = userFacade.findAll(webRequest);
        response.setItemsSize(response.getItems().size()); //в page data даю кол-во всех курсов
        response.initPaginationState(response.getCurrentPage());
        List<HeaderData> headerDataList = new ArrayList<>();
        for (HeaderName headerName : columnNames) {
            HeaderData data = new HeaderData();
            data.setHeaderName(headerName.getColumnName());
            if (StringUtils.isBlank(headerName.getTableName())) {
                data.setSortable(false);
            } else {
                data.setSortable(true);
                data.setSort(headerName.getDbName());
                if (response.getSort().equals(headerName.getTableName())) {
                    data.setActive(true);
                    data.setOrder(response.getOrder());
                } else {
                    data.setActive(false);
                    data.setOrder(DEFAULT_ORDER_PARAM_VALUE);
                }
            }
            headerDataList.add(data);
        }
        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/users/all");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "All Users");
        return "pages/user/user_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest webRequest, ModelMap modelMap) {
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(modelMap::addAttribute);
        }
        return new ModelAndView("redirect:/users", modelMap);
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "pages/user/user_new";
    }

    @PostMapping("/new")
    public String createNewUser(@ModelAttribute("user") UserRequestDto userRequestDto) {
        userFacade.create(userRequestDto);
        return "redirect:/users";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Set<AccountResponseDto> accounts = userFacade.getAccounts(id);
        model.addAttribute("user", userFacade.findById(id));
        model.addAttribute("accounts", accounts);
        return "pages/user/user_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        Set<AccountResponseDto> accounts = userFacade.getAccounts(id);
        for (AccountResponseDto account : accounts) {
            userFacade.removeAccount(id, account.getId());
        }
        userFacade.delete(id);
        return "redirect:/users";
    }
}
