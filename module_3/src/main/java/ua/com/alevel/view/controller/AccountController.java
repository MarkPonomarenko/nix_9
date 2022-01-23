package ua.com.alevel.view.controller;

import com.opencsv.CSVWriter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.facade.OperationFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.service.OperationService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.request.OperationRequestDto;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.OperationResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import static ua.com.alevel.util.WebRequestUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/accounts")
public class AccountController extends AbstractController {

    private Long fromId = 0L, toId = 0L;


    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String user;

    @Value("${spring.datasource.password}")
    String password;

    private final UserFacade userFacade;
    private final AccountFacade accountFacade;
    private final OperationFacade operationFacade;
    private final UserService userService;
    private final AccountService accountService;
    private final OperationService operationService;

    public AccountController(UserFacade userFacade, AccountFacade accountFacade, OperationFacade operationFacade, UserService userService, AccountService accountService, OperationService operationService) {
        this.userFacade = userFacade;
        this.accountFacade = accountFacade;
        this.operationFacade = operationFacade;
        this.userService = userService;
        this.accountService = accountService;
        this.operationService = operationService;
    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        HeaderName[] columnNames = new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("name", "name", "name"),
                new HeaderName("balance", "balance", "balance"),
                new HeaderName("created", "created", "created")
        };
        PageData<AccountResponseDto> response = accountFacade.findAll(webRequest);
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
        model.addAttribute("createUrl", "/accounts/all");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "All Accounts");
        return "pages/account/account_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest webRequest, ModelMap modelMap) {
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(modelMap::addAttribute);
        }
        return new ModelAndView("redirect:/accounts", modelMap);
    }

    @GetMapping("/new/{id}")
    public String createNewAccount(@PathVariable Long id, Model model) {
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setOwner(userService.findById(id));
        accountFacade.create(accountRequestDto);
        userFacade.addAccount(id, accountService.getLastRecord().getId());
        Set<AccountResponseDto> accounts = userFacade.getAccounts(id);
        model.addAttribute("user", userFacade.findById(id));
        model.addAttribute("accounts", accounts);
        return "redirect:/users/details/" + userFacade.findById(id).getId();
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Set<OperationResponseDto> operations = accountFacade.getOperations(id);
        model.addAttribute("account", accountFacade.findById(id));
        model.addAttribute("operations", operations);
        return "pages/account/account_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        Set<OperationResponseDto> operations = accountFacade.getOperations(id);
        for (OperationResponseDto operation : operations) {
            operationFacade.delete(operation.getId());
        }
        User owner = accountFacade.findById(id).getOwner();
        userFacade.removeAccount(owner.getId(), id);
        accountFacade.delete(id);
        model.addAttribute("user", owner);
        model.addAttribute("accounts", userFacade.getAccounts(owner.getId()));
        return "redirect:/users/details/" + owner.getId();
    }

    @GetMapping("/transfer/{id}")
    public String goToAllAccountsWithTransfer(@PathVariable Long id,WebRequest webRequest, Model model) {
        HeaderName[] columnNames = new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("id", "id", "id"),
                new HeaderName("name", "name", "name"),
                new HeaderName("balance", "balance", "balance"),
                new HeaderName("created", "created", "created")
        };
        PageData<AccountResponseDto> response = accountFacade.findAll(webRequest);
        response.getItems().removeIf(item -> Objects.equals(item.getId(), id));
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
        model.addAttribute("createUrl", "/accounts/all");
        model.addAttribute("pageData", response);
        model.addAttribute("accountCurrent", accountFacade.findById(id));
        model.addAttribute("cardHeader", "All Accounts");
        return "pages/account/account_transfer";
    }

    //не знаю почему но при передачи друг ид происходила ошибка парсинга стринг в лонг
    //передавалось: accId+id в одну переменную
    //решил просто засплитить строку раз так
    @GetMapping("/transfer/={accId}")
    public String redirectToNewOperation(@PathVariable String accId, Model model) {
        System.out.println(accId);
        String[] ids = accId.split("\\+");
        OperationRequestDto from = new OperationRequestDto();
        OperationRequestDto to = new OperationRequestDto();
        from.setAccount(accountService.findById(Long.parseLong(ids[0])));
        to.setAccount(accountService.findById(Long.parseLong(ids[1])));
        fromId = Long.parseLong(ids[0]);
        toId = Long.parseLong(ids[1]);
        model.addAttribute("accountFrom", accountService.findById(Long.parseLong(ids[0])));
        model.addAttribute("operationFrom", from);
        model.addAttribute("operationTo", to);
        return "pages/operation/operation_transfer";
    }

    @PostMapping("/transfer")
    public String newOperationsCreation(@ModelAttribute("operationFrom") OperationRequestDto from,
                                        @ModelAttribute("operationTo") OperationRequestDto to)  {

        System.out.println(accountFacade.findById(fromId));
        System.out.println(accountFacade.findById(toId));
        from.setAccount(accountService.findById(fromId));
        to.setAccount(accountService.findById(toId));

        Account fromAcc = accountService.findById(fromId);
        fromAcc.setBalance(fromAcc.getBalance() - from.getValue());
        accountService.update(fromAcc);
        Account toAcc = accountService.findById(toId);
        toAcc.setBalance(toAcc.getBalance() + to.getValue());
        accountService.update(toAcc);

        to.setValue(from.getValue());
        from.setValue(-to.getValue());
        operationFacade.create(from);
        accountFacade.addOperation(fromId, operationService.getLastRecord().getId());
        operationFacade.create(to);
        accountFacade.addOperation(toId, operationService.getLastRecord().getId());
        return "redirect:/accounts/details/" + fromId;
    }

    @GetMapping("/csv_get/{id}")
    public String getCsvFile(@PathVariable Long id) {
        String outfile = id + "_outcome.csv";
        try {
            File file = new File(outfile);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        //outcome csv
        Path myPath = Paths.get("module_3/" + outfile);
        System.out.println(myPath + "\n" + myPath.getFileName());
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM operations WHERE account_id =" + id + " AND value < 0");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(myPath,
                    StandardCharsets.UTF_8), CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {
                writer.writeAll(resultSet, true);
            }
        } catch (IOException | SQLException ex) {
            System.out.println("An error occurred by SQL or IO");
            ex.printStackTrace();
        }

        //income csv
        String infile = id + "_income.csv";
        try {
            File file = new File(infile);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        myPath = Paths.get("module_3/" + infile);
        System.out.println(myPath + "\n" + myPath.getFileName());
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM operations WHERE account_id =" + id + " AND value > 0");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(myPath,
                    StandardCharsets.UTF_8), CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {
                writer.writeAll(resultSet, true);
            }
        } catch (IOException | SQLException ex) {
            System.out.println("An error occurred by SQL or IO");
            ex.printStackTrace();
        }

        return  "redirect:/accounts/details/" + id;
    }
}
