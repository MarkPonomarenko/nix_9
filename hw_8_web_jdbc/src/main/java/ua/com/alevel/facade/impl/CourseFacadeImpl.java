package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.CourseFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.service.CourseService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.CourseRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseFacadeImpl implements CourseFacade {

    private final CourseService courseService;

    public CourseFacadeImpl(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void create(CourseRequestDto courseRequestDto) {
        Course course = new Course();
        course.setTitle(courseRequestDto.getTitle());
        course.setTeacher(courseRequestDto.getTeacher());
        course.setCredits(courseRequestDto.getCredits());
        courseService.create(course);
    }

    @Override
    public void update(CourseRequestDto courseRequestDto, Long id) {
        Course course = courseService.findById(id);
        course.setTitle(courseRequestDto.getTitle());
        course.setTeacher(courseRequestDto.getTeacher());
        course.setCredits(courseRequestDto.getCredits());
        courseService.update(course);
    }

    @Override
    public void delete(Long id) {
        courseService.delete(id);
    }

    @Override
    public CourseResponseDto findById(Long id) {
        return new CourseResponseDto(courseService.findById(id));
    }

    @Override
    public PageData<CourseResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);

        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Course> all = courseService.findAll(dataTableRequest);
        List<CourseResponseDto> data = all.getItems().stream()
                .map(CourseResponseDto::new)
                .peek(dto -> dto.setStudentCount((Integer) all.getOtherParamMap().get(dto.getId())))
                .collect(Collectors.toList());

        PageData<CourseResponseDto> pageData = new PageData<>();
        pageData.setItems(data);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(all.getItemsSize());

        return pageData;
    }

    @Override
    public List<CourseResponseDto> findAll() {
        List<Course> all = courseService.findAll();
        List<CourseResponseDto> items = all.stream()
                .map(CourseResponseDto::new)
                .collect(Collectors.toList());

        return items;
    }
}
