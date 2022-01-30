package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.PLPFacade;

@Controller
@RequestMapping("/servers")
public class OpenServerController {

    private final PLPFacade plpFacade;

    public OpenServerController(PLPFacade plpFacade) {
        this.plpFacade = plpFacade;
    }

    @GetMapping
    private String allServers(Model model, WebRequest webRequest) {
        model.addAttribute("serverList", plpFacade.search(webRequest));
        return "pages/open/plp";
    }
}
