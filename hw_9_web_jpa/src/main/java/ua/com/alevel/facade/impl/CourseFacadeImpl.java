package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.CourseFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.service.CourseService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.CourseRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public void update(CourseRequestDto courseRequestDto, long id) {
        Course course = courseService.findById(id);
        course.setTitle(courseRequestDto.getTitle());
        course.setTeacher(courseRequestDto.getTeacher());
        course.setCredits(courseRequestDto.getCredits());
        courseService.update(course);
    }

    @Override
    public void delete(long id) {
        courseService.delete(id);
    }

    @Override
    public CourseResponseDto findById(long id) {
        return new CourseResponseDto(courseService.findById(id));
    }

    @Override
    public List<CourseResponseDto> findAll() {
        List<Course> courses = courseService.findAll();
        List<CourseResponseDto> dto = new ArrayList<>();
        for (Course course : courses) {
            dto.add(new CourseResponseDto(course));
        }
        return dto;
    }

    @Override
    public PageData<CourseResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);

        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Course> all = courseService.findAll(dataTableRequest);
        List<CourseResponseDto> data = all.getItems().stream()
                .map(CourseResponseDto::new)
                .collect(Collectors.toList());

        PageData<CourseResponseDto> pageData = new PageData<>();
        pageData.setItems(data);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(all.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());

        return pageData;
    }

    @Override
    public List<StudentResponseDto> findCourseStudents(Long id) {
        Set<Student> students = courseService.findCourseStudents(id);
        List<StudentResponseDto> dto = new ArrayList<>();

        for (Student student : students) {
            StudentResponseDto studentResponseDto = new StudentResponseDto(student);
            dto.add(studentResponseDto);
        }
        return dto;
    }

    @Override
    public void assignStudent(Long courseId, Long studentId) {
        courseService.assignStudent(courseId, studentId);
    }

    @Override
    public void unassignStudent(Long courseId, Long studentId) {
        courseService.unassignStudent(courseId, studentId);
    }
}
