package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.AccountingRequestDto;
import ua.com.alevel.view.dto.response.AccountingResponseDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.List;

public interface AccountingFacade extends BaseFacade<AccountingRequestDto, AccountingResponseDto> {

    List<StudentResponseDto> findCourseStudents(Long courseId);

    List<CourseResponseDto> findStudentCourses(Long studentId);

    AccountingResponseDto findByBothId(Long courseId, Long studentId);
}
