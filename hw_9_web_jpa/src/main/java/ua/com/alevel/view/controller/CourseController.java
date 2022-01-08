package ua.com.alevel.view.controller;


import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.CourseFacade;
import ua.com.alevel.facade.StudentFacade;
import ua.com.alevel.view.dto.request.CourseRequestDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ua.com.alevel.util.WebRequestUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/courses")
public class CourseController extends AbstractController {

    private final CourseFacade courseFacade;
    private final StudentFacade studentFacade;

    public CourseController(CourseFacade courseFacade, StudentFacade studentFacade) {
        this.courseFacade = courseFacade;
        this.studentFacade = studentFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        AbstractController.HeaderName[] columnNames = new AbstractController.HeaderName[]{
                new AbstractController.HeaderName("#", null, null),
                new AbstractController.HeaderName("title", "title", "title"),
                new AbstractController.HeaderName("teacher", "teacher", "teacher"),
                new AbstractController.HeaderName("credits", "credits", "credits"),
                new AbstractController.HeaderName("updated", "updated", "updated"),
                new AbstractController.HeaderName("details", null, null),
                new AbstractController.HeaderName("add", null, null),
                new AbstractController.HeaderName("delete", null, null)
        };
        PageData<CourseResponseDto> response = courseFacade.findAll(webRequest);
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
        model.addAttribute("createUrl", "/courses/all");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "All Courses");
        return "pages/course/course_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest webRequest, ModelMap modelMap) {
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(modelMap::addAttribute);
        }
        return new ModelAndView("redirect:/courses", modelMap);
    }

    @GetMapping("/new")
    public String redirectToNewCoursePage(Model model) {
        model.addAttribute("course", new CourseRequestDto());
        return "pages/course/course_new";
    }

    @PostMapping("/new")
    public String createNewOrder(@ModelAttribute("course") CourseRequestDto courseRequestDto) {
        courseFacade.create(courseRequestDto);
        return "redirect:/courses";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        List<StudentResponseDto> students = courseFacade.findCourseStudents(id);
        model.addAttribute("course", courseFacade.findById(id));
        model.addAttribute("students", students);
        return "pages/course/course_details";
    }

    @GetMapping("/remove/{courseId}&{studentId}")
    public String removeStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId, Model model) {
        courseFacade.unassignStudent(courseId, studentId);
        List<StudentResponseDto> students = courseFacade.findCourseStudents(courseId);
        model.addAttribute("course", courseFacade.findById(courseId));
        model.addAttribute("students", students);
        return "pages/course/course_details";
    }

    @GetMapping("/update/{id}")
    public String redirectToUpdateCourse(@PathVariable Long id, Model model) {
        List<StudentResponseDto> students = studentFacade.findAll();
        List<StudentResponseDto> nonCourseStudents = new ArrayList<>();
        List<StudentResponseDto> courseStudents = courseFacade.findCourseStudents(id);
        boolean contains;
        for (StudentResponseDto student : students) {
            contains = true;
            for (StudentResponseDto courseStudent : courseStudents) {
                if (Objects.equals(student.getId(), courseStudent.getId())) {
                    contains = false;
                    break;
                }
            }
            if (contains) {
                nonCourseStudents.add(student);
            }
        }
        model.addAttribute("course", courseFacade.findById(id));
        model.addAttribute("students", nonCourseStudents);
        return "pages/course/course_update";
    }

    @PostMapping("update/{id}")
    public String updateCourse(@ModelAttribute("course") CourseRequestDto dto, @PathVariable Long id) {
        courseFacade.update(dto, id);
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        List<StudentResponseDto> students = courseFacade.findCourseStudents(id);
        for (StudentResponseDto student : students) {
            courseFacade.unassignStudent(id, student.getId());
        }
        courseFacade.delete(id);
        return "redirect:/courses";
    }

    @GetMapping("/add/{courseId}+{studentId}")
    public String createRelation(@PathVariable Long courseId, @PathVariable Long studentId, Model model) {
        courseFacade.assignStudent(courseId, studentId);
        List<StudentResponseDto> students = studentFacade.findAll();
        List<StudentResponseDto> nonCourseStudents = new ArrayList<>();
        List<StudentResponseDto> courseStudents = courseFacade.findCourseStudents(studentId);
        boolean contains;
        for (StudentResponseDto student : students) {
            contains = true;
            for (StudentResponseDto courseStudent : courseStudents) {
                if (Objects.equals(student.getId(), courseStudent.getId())) {
                    contains = false;
                    break;
                }
            }
            if (contains) {
                nonCourseStudents.add(student);
            }
        }
        model.addAttribute("course", courseFacade.findById(courseId));
        model.addAttribute("students", nonCourseStudents);
        return "pages/course/course_update";
    }
}
