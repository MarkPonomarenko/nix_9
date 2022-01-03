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
import ua.com.alevel.view.dto.request.StudentRequestDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ua.com.alevel.util.WebRequestUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/students")
public class StudentController extends AbstractController {

    private final CourseFacade courseFacade;
    private final StudentFacade studentFacade;

    public StudentController(CourseFacade courseFacade, StudentFacade studentFacade) {
        this.courseFacade = courseFacade;
        this.studentFacade = studentFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        AbstractController.HeaderName[] columnNames = new AbstractController.HeaderName[]{
                new AbstractController.HeaderName("#", null, null),
                //lastName&firstName вместо first_name&last_name из-за того, что так jpa (DAO) не находит их в БД
                new AbstractController.HeaderName("last name", "lastName", "lastName"),
                new AbstractController.HeaderName("first name", "firstName", "firstName"),
                new AbstractController.HeaderName("age", "age", "age"),
                new AbstractController.HeaderName("updated", "updated", "updated"),
                new AbstractController.HeaderName("details", null, null),
                new AbstractController.HeaderName("add", null, null),
                new AbstractController.HeaderName("delete", null, null)
        };
        PageData<StudentResponseDto> response = studentFacade.findAll(webRequest);
        response.setItemsSize(studentFacade.findAll().size()); //в page data даю кол-во всех студентовwwd
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
        model.addAttribute("createUrl", "/students/all");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "All Students");
        return "pages/student/student_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest webRequest, ModelMap modelMap) {
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(modelMap::addAttribute);
        }
        return new ModelAndView("redirect:/students", modelMap);
    }

    @GetMapping("/new")
    public String redirectToNewStudentPage(Model model) {
        model.addAttribute("student", new StudentRequestDto());
        return "pages/student/student_new";
    }

    @PostMapping("/new")
    public String createNewStudent(@ModelAttribute("student") StudentRequestDto studentRequestDto) {
        studentFacade.create(studentRequestDto);
        return "redirect:/students";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        List<CourseResponseDto> courses = studentFacade.findStudentCourses(id);
        model.addAttribute("student", studentFacade.findById(id));
        model.addAttribute("courses", courses);
        return "pages/student/student_details";
    }

    @GetMapping("/remove/{courseId}&{studentId}")
    public String removeCourseFromStudent(@PathVariable Long courseId, @PathVariable Long studentId, Model model) {
        studentFacade.unassignCourse(courseId, studentId);
        List<CourseResponseDto> courses = studentFacade.findStudentCourses(studentId);
        model.addAttribute("student", studentFacade.findById(studentId));
        model.addAttribute("courses", courses);
        return "pages/student/student_details";
    }

    @GetMapping("/update/{id}")
    public String redirectToUpdateStudent(@PathVariable Long id, Model model) {
        List<CourseResponseDto> courses = courseFacade.findAll();
        List<CourseResponseDto> nonStudentCourses = new ArrayList<>();
        List<CourseResponseDto> studentCourses = studentFacade.findStudentCourses(id);
        boolean contains;
        for (CourseResponseDto course : courses) {
            contains = true;
            for (CourseResponseDto studentCourse : studentCourses) {
                if (Objects.equals(course.getId(), studentCourse.getId())) {
                    contains = false;
                    break;
                }
            }
            if (contains) {
                nonStudentCourses.add(course);
            }
        }
        model.addAttribute("student", studentFacade.findById(id));
        model.addAttribute("courses", nonStudentCourses);
        return "pages/student/student_update";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@ModelAttribute("student") StudentRequestDto dto, @PathVariable Long id) {
        studentFacade.update(dto, id);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        List<CourseResponseDto> courses = studentFacade.findStudentCourses(id);
        for (CourseResponseDto course : courses) {
            studentFacade.unassignCourse(course.getId(), id);
        }
        studentFacade.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/add/{courseId}+{studentId}")
    public String createRelation(@PathVariable Long courseId, @PathVariable Long studentId, Model model) {
        studentFacade.assignCourse(courseId, studentId);
        List<CourseResponseDto> courses = courseFacade.findAll();
        List<CourseResponseDto> nonStudentCourses = new ArrayList<>();
        List<CourseResponseDto> studentCourses = studentFacade.findStudentCourses(studentId);
        boolean contains;
        for (CourseResponseDto course : courses) {
            contains = true;
            for (CourseResponseDto studentCourse : studentCourses) {
                if (Objects.equals(course.getId(), studentCourse.getId())) {
                    contains = false;
                    break;
                }
            }
            if (contains) {
                nonStudentCourses.add(course);
            }
        }
        model.addAttribute("student", studentFacade.findById(studentId));
        model.addAttribute("courses", nonStudentCourses);
        return "pages/student/student_update";
    }
}
