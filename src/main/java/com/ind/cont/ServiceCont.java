package com.ind.cont;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceCont extends Global {
    @GetMapping("/service")
    public String service(Model model) {
        AddAttributesService(model);
        model.addAttribute("devices",repoDevices.findByServesId(getUserID()));
        return "service";
    }
}
