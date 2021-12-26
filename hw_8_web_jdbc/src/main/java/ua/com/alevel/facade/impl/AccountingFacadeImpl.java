package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.AccountingFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Accounting;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.service.AccountingService;
import ua.com.alevel.service.CourseService;
import ua.com.alevel.service.StudentService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.AccountingRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.AccountingResponseDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountingFacadeImpl implements AccountingFacade {

    private final AccountingService accountingService;
    private final StudentService studentService;
    private final CourseService courseService;

    public AccountingFacadeImpl(AccountingService accountingService, StudentService studentService, CourseService courseService) {
        this.accountingService = accountingService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    public List<StudentResponseDto> findCourseStudents(Long courseId) {
        List<Accounting> accountings;
        List<StudentResponseDto> students = new ArrayList<>();
        accountings = accountingService.findCourseStudents(courseId);
        Student student;
        StudentResponseDto studentResponseDto;
        for (Accounting accounting : accountings) {
            if (accounting.getCourseId().equals(courseId)) {
                student = studentService.findById(accounting.getStudentId());
                studentResponseDto = new StudentResponseDto(student);
                students.add(studentResponseDto);
            }
        }
        return students;
    }

    @Override
    public List<CourseResponseDto> findStudentCourses(Long studentId) {
        List<Accounting> accountings;
        List<CourseResponseDto> courses = new ArrayList<>();
        accountings = accountingService.findStudentCourses(studentId);
        Course course;
        CourseResponseDto courseResponseDto;
        for (Accounting accounting : accountings) {
            if (accounting.getStudentId().equals(studentId)) {
                course = courseService.findById(accounting.getCourseId());
                courseResponseDto = new CourseResponseDto(course);
                courses.add(courseResponseDto);
            }
        }
        return courses;
    }

    @Override
    public AccountingResponseDto findByBothId(Long courseId, Long studentId) {
        return new AccountingResponseDto(accountingService.findByBothId(courseId, studentId));
    }

    @Override
    public void create(AccountingRequestDto accountingRequestDto) {
        Accounting accounting = new Accounting();
        accounting.setCourseId(accountingRequestDto.getCourseId());
        accounting.setStudentId(accountingRequestDto.getStudentId());
        accountingService.create(accounting);
    }

    @Override
    public void update(AccountingRequestDto accountingRequestDto, Long id) {
        Accounting accounting = accountingService.findById(id);
        accounting.setCourseId(accountingRequestDto.getCourseId());
        accounting.setStudentId(accountingRequestDto.getStudentId());
        accountingService.update(accounting);
    }

    @Override
    public void delete(Long id) {
        accountingService.delete(id);
    }

    @Override
    public AccountingResponseDto findById(Long id) {
        return new AccountingResponseDto(accountingService.findById(id));
    }

    @Override
    public PageData<AccountingResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);

        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Accounting> all = accountingService.findAll(dataTableRequest);
        List<AccountingResponseDto> data = all.getItems().stream()
                .map(AccountingResponseDto::new)
                .collect(Collectors.toList());

        PageData<AccountingResponseDto> pageData = new PageData<>();
        pageData.setItems(data);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(all.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());

        return pageData;
    }

}
