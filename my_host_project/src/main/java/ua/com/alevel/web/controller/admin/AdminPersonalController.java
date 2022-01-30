package ua.com.alevel.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.web.controller.AbstractController;

@Controller
@RequestMapping("/admin/personals")
public class AdminPersonalController extends AbstractController {


    private final HeaderName[] columnNames = new HeaderName[] {
            new HeaderName("#", null, null),
            new HeaderName("first name", "firstName", "firstName"),
            new HeaderName("last name", "lastName", "country"),
            new HeaderName("created", "created", "created"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };
}
