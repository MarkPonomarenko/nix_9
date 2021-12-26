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
import ua.com.alevel.persistence.entity.Accounting;
import ua.com.alevel.view.dto.request.AccountingRequestDto;
import ua.com.alevel.view.dto.request.CourseRequestDto;
import ua.com.alevel.view.dto.response.CourseResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.com.alevel.util.WebRequestUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/courses")
public class CourseController extends AbstractController {

    private final CourseFacade courseFacade;
    private final StudentFacade studentFacade;
    private final AccountingFacade accountingFacade;

    public CourseController(CourseFacade courseFacade, StudentFacade studentFacade, AccountingFacade accountingFacade) {
        this.courseFacade = courseFacade;
        this.studentFacade = studentFacade;
        this.accountingFacade = accountingFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        HeaderName[] columnNames = new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("title", "title", "title"),
                new HeaderName("teacher", "teacher", "teacher"),
                new HeaderName("credits", "credits", "credits"),
                new HeaderName("student count", "studentCount", "studentCount"),
                new HeaderName("details", null, null),
                new HeaderName("add", null, null),
                new HeaderName("delete", null, null)
        };
        PageData<CourseResponseDto> response = courseFacade.findAll(webRequest);
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
    public String createNewCourse(@ModelAttribute("course") CourseRequestDto dto) {
        courseFacade.create(dto);
        return "redirect:/courses";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        List<StudentResponseDto> students = accountingFacade.findCourseStudents(id);
        model.addAttribute("course", courseFacade.findById(id));
        model.addAttribute("students", students);
        return "pages/course/course_details";
    }

    @GetMapping("/remove/{courseId}&{studentId}")
    public String removeStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId, Model model) {
        accountingFacade.delete(accountingFacade.findByBothId(courseId, studentId).getId());
        List<StudentResponseDto> students = accountingFacade.findCourseStudents(courseId);
        model.addAttribute("course", courseFacade.findById(courseId));
        model.addAttribute("students", students);
        return "pages/course/course_details";
    }

    @GetMapping("/update/{id}")
    public String redirectToUpdateCourse(@PathVariable Long id, Model model) {
        List<StudentResponseDto> students = studentFacade.findAll();
        List<StudentResponseDto> nonCourseStudents = new ArrayList<>();
        for (StudentResponseDto student : students) {
            if (accountingFacade.findByBothId(id, student.getId()).getId() == null) {
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
        List<StudentResponseDto> students = accountingFacade.findCourseStudents(id);
        for (StudentResponseDto student : students) {
            accountingFacade.delete(accountingFacade.findByBothId(id, student.getId()).getId());
        }
        courseFacade.delete(id);
        return "redirect:/courses";
    }

    @GetMapping("/add/{courseId}+{studentId}")
    public String createRelation(@PathVariable Long courseId, @PathVariable Long studentId, Model model) {
        AccountingRequestDto accounting = new AccountingRequestDto();
        accounting.setCourseId(courseId);
        accounting.setStudentId(studentId);
        accountingFacade.create(accounting);
        List<StudentResponseDto> students = studentFacade.findAll();
        List<StudentResponseDto> nonCourseStudents = new ArrayList<>();
        for (StudentResponseDto student : students) {
            if (accountingFacade.findByBothId(courseId, student.getId()).getId() == null) {
                nonCourseStudents.add(student);
            }
        }
        model.addAttribute("course", courseFacade.findById(courseId));
        model.addAttribute("students", nonCourseStudents);
        return "pages/course/course_update";
    }
}
