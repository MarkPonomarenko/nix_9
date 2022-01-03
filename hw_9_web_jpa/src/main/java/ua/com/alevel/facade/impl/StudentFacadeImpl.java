package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.StudentFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Course;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.service.StudentService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.request.StudentRequestDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentFacadeImpl implements StudentFacade {

    private final StudentService studentService;

    public StudentFacadeImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void create(StudentRequestDto studentRequestDto) {
        Student student = new Student();
        student.setAge(studentRequestDto.getAge());
        student.setFirstName(studentRequestDto.getFirstName());
        student.setLastName(studentRequestDto.getLastName());
        studentService.create(student);
    }

    @Override
    public void update(StudentRequestDto studentRequestDto, long id) {
        Student student = studentService.findById(id);
        student.setAge(studentRequestDto.getAge());
        student.setFirstName(studentRequestDto.getFirstName());
        student.setLastName(studentRequestDto.getLastName());
        studentService.update(student);
    }

    @Override
    public void delete(long id) {
        studentService.delete(id);
    }

    @Override
    public StudentResponseDto findById(long id) {
        return new StudentResponseDto(studentService.findById(id));
    }

    @Override
    public List<StudentResponseDto> findAll() {
        List<Student> students = studentService.findAll();
        List<StudentResponseDto> dto = new ArrayList<>();
        for (Student student : students) {
            dto.add(new StudentResponseDto(student));
        }
        return dto;
    }

    @Override
    public PageData<StudentResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);

        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Student> all = studentService.findAll(dataTableRequest);
        List<StudentResponseDto> data = all.getItems().stream()
                .map(StudentResponseDto::new)
                .collect(Collectors.toList());

        PageData<StudentResponseDto> pageData = new PageData<>();
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
    public List<CourseResponseDto> findStudentCourses(Long id) {
        Set<Course> courses = studentService.findCourseStudents(id);
        List<CourseResponseDto> dto = new ArrayList<>();

        for (Course course : courses) {
            CourseResponseDto courseResponseDto = new CourseResponseDto(course);
            dto.add(courseResponseDto);
        }
        return dto;
    }

    @Override
    public void assignCourse(Long courseId, Long studentId) {
        studentService.assignCourse(courseId, studentId);
    }

    @Override
    public void unassignCourse(Long courseId, Long studentId) {
        studentService.unassignCourse(courseId, studentId);
    }
}
