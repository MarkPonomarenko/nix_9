package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.PersonalFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.user.Personal;
import ua.com.alevel.service.PersonalCrudService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.register.AuthDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.PersonalResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalFacadeImpl implements PersonalFacade {

    private final PersonalCrudService personalService;

    public PersonalFacadeImpl(PersonalCrudService personalService) {
        this.personalService = personalService;
    }

    @Override
    public void create(AuthDto authDto) {
        Personal personal = new Personal();
        personal.setPassword(authDto.getPassword());
        personal.setEmail(authDto.getEmail());
        personal.setFirstName(authDto.getFirstName());
        personal.setLastName(authDto.getLastName());
        personalService.create(personal);
    }

    @Override
    public void update(AuthDto authDto, Long id) {
        Personal personal = personalService.findById(id).get();
        personal.setPassword(authDto.getPassword());
        personal.setEmail(authDto.getEmail());
        personal.setFirstName(authDto.getFirstName());
        personal.setLastName(authDto.getLastName());
        personalService.create(personal);
    }

    @Override
    public void delete(Long id) {
        personalService.delete(id);
    }

    @Override
    public PersonalResponseDto findById(Long id) {
        return new PersonalResponseDto(personalService.findById(id).get());
    }

    @Override
    public PageData<PersonalResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebUtil.generateDataTableRequestByWebRequest(request);
        DataTableResponse<Personal> tableResponse = personalService.findAll(dataTableRequest);
        List<PersonalResponseDto> personals = tableResponse.getItems().stream().
                map(PersonalResponseDto::new).
                collect(Collectors.toList());

        PageData<PersonalResponseDto> pageData = (PageData<PersonalResponseDto>) WebUtil.initPageData(tableResponse);
        pageData.setItems(personals);
        return pageData;
    }
}
