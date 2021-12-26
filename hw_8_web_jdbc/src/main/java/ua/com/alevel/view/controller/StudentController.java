package ua.com.alevel.view.controller;


import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.AccountingFacade;
import ua.com.alevel.facade.CourseFacade;
import ua.com.alevel.facade.StudentFacade;
import ua.com.alevel.view.dto.request.AccountingRequestDto;
import ua.com.alevel.view.dto.request.CourseRequestDto;
import ua.com.alevel.view.dto.request.StudentRequestDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.com.alevel.util.WebRequestUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/students")
public class StudentController extends AbstractController {

    private final CourseFacade courseFacade;
    private final StudentFacade studentFacade;
    private final AccountingFacade accountingFacade;

    public StudentController(CourseFacade courseFacade, StudentFacade studentFacade, AccountingFacade accountingFacade) {
        this.courseFacade = courseFacade;
        this.studentFacade = studentFacade;
        this.accountingFacade = accountingFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        HeaderName[] columnNames = new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("last name", "lastName", "last_name"),
                new HeaderName("first name", "firstName", "first_name"),
                new HeaderName("age", "age", "age"),
                new HeaderName("updated", "updated", "updated"),
                new HeaderName("details", null, null),
                new HeaderName("add", null, null),
                new HeaderName("delete", null, null)
        };
        PageData<StudentResponseDto> response = studentFacade.findAll(webRequest);
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
    public String createNewStudent(@ModelAttribute("student") StudentRequestDto dto) {
        studentFacade.create(dto);
        return "redirect:/students";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        List<CourseResponseDto> courses = accountingFacade.findStudentCourses(id);
        model.addAttribute("student", studentFacade.findById(id));
        model.addAttribute("courses", courses);
        return "pages/student/student_details";
    }

    @GetMapping("/remove/{courseId}&{studentId}")
    public String removeCourseFromStudent(@PathVariable Long courseId, @PathVariable Long studentId, Model model) {
        accountingFacade.delete(accountingFacade.findByBothId(courseId, studentId).getId());
        List<CourseResponseDto> courses = accountingFacade.findStudentCourses(studentId);
        model.addAttribute("student", studentFacade.findById(studentId));
        model.addAttribute("courses", courses);
        return "pages/student/student_details";
    }

    @GetMapping("/update/{id}")
    public String redirectToUpdateStudent(@PathVariable Long id, Model model) {
        List<CourseResponseDto> courses = courseFacade.findAll();
        List<CourseResponseDto> nonStudentCourses = new ArrayList<>();
        for (CourseResponseDto course : courses) {
            if (accountingFacade.findByBothId(course.getId(), id).getId() == null) {
                nonStudentCourses.add(course);
            }
        }
        model.addAttribute("student", studentFacade.findById(id));
        model.addAttribute("courses", nonStudentCourses);
        return "pages/student/student_update";
    }

    @PostMapping("update/{id}")
    public String updateCourse(@ModelAttribute("student") StudentRequestDto dto, @PathVariable Long id) {
        studentFacade.update(dto, id);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        List<CourseResponseDto> courses = accountingFacade.findStudentCourses(id);
        for (CourseResponseDto course : courses) {
            accountingFacade.delete(accountingFacade.findByBothId(id, course.getId()).getId());
        }
        studentFacade.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/add/{courseId}+{studentId}")
    public String createRelation(@PathVariable Long courseId, @PathVariable Long studentId, Model model) {
        AccountingRequestDto accounting = new AccountingRequestDto();
        accounting.setCourseId(courseId);
        accounting.setStudentId(studentId);
        accountingFacade.create(accounting);
        List<CourseResponseDto> courses = courseFacade.findAll();
        List<CourseResponseDto> nonStudentCourse = new ArrayList<>();
        for (CourseResponseDto course : courses) {
            if (accountingFacade.findByBothId(course.getId(), studentId).getId() == null) {
                nonStudentCourse.add(course);
            }
        }
        model.addAttribute("student", studentFacade.findById(studentId));
        model.addAttribute("courses", nonStudentCourse);
        return "pages/student/student_update";
    }
}
