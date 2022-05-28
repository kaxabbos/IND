package com.ind.cont;

import com.ind.cont.general.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceCont extends Attributes {
    @GetMapping("/service")
    public String service(Model model) {
        AddAttributesService(model);
        model.addAttribute("devices",repoDevices.findByServesId(getUserID()));
        return "service";
    }
}
