package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.StudentRequestDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.List;

public interface StudentFacade extends BaseFacade<StudentRequestDto, StudentResponseDto>{

    List<CourseResponseDto> findStudentCourses(Long id);

    void assignCourse(Long courseId, Long studentId);

    void unassignCourse(Long courseId, Long studentId);
}
