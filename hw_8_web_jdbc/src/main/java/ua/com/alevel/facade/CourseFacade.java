package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.CourseRequestDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;

import java.util.List;

public interface CourseFacade extends BaseFacade<CourseRequestDto, CourseResponseDto> {

    List<CourseResponseDto> findAll();
}
