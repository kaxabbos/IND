package com.ind.cont;

import com.ind.cont.general.Attributes;
import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexCont extends Attributes {
    @GetMapping("/index")
    public String index1(Model model) {
        AddAttributesSearch(model, Status.Все, DeviceType.Все);
        return "index";
    }

    @GetMapping("/")
    public String index2(Model model) {
        AddAttributesSearch(model, Status.Все, DeviceType.Все);
        return "index";
    }

    @PostMapping("/search/status_type")
    String searchStatusType(Model model, @RequestParam Status status, @RequestParam DeviceType type) {
        AddAttributesSearch(model, status, type);
        return "index";
    }

    @PostMapping("/index/search")
    String searchStatusType(Model model, @RequestParam String search) {
        AddAttributesSearch(model, search);
        return "index";
    }
}
