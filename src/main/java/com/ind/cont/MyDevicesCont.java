package com.ind.cont;

import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyDevicesCont extends Global {
    @GetMapping("/myDevices")
    public String allDevice(Model model) {
        attributes(model);
        model.addAttribute("devices", repoDevices.findByUserId(checkUserID()));
        model.addAttribute("serviceable", Status.Исправен);
        return "myDevices";
    }
}
