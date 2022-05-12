package com.ind.cont;

import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexCont extends Global {
    @GetMapping("/index")
    public String index1(Model model) {
        attributesSearch(model, Status.Все, DeviceType.Все);
        return "index";
    }

    @GetMapping("/")
    public String index2(Model model) {
        attributesSearch(model, Status.Все, DeviceType.Все);
        return "index";
    }

    @PostMapping("/search/status_type")
    String searchStatusType(Model model, @RequestParam Status status, @RequestParam DeviceType type) {
        attributesSearch(model, status, type);
        return "index";
    }

    @PostMapping("/index/search")
    String searchStatusType(Model model, @RequestParam String search) {
        attributesSearch(model, search);
        return "index";
    }
}
