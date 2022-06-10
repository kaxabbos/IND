package com.ind.cont;

import com.ind.cont.global.Attributes;
import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Role;
import com.ind.models.enums.Select;
import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatsCont extends Attributes {

    @GetMapping("/stats")
    public String Stats(Model model) {
        AddAttributesStats(model, Select.Оргтехника, Status.Все, DeviceType.Все, Role.Техник);
        return "stats";
    }

    @PostMapping("/stats/search/status_type")
    public String StatsSearch(Model model, @RequestParam Select select, @RequestParam Status status, @RequestParam DeviceType type, @RequestParam Role role) {
        AddAttributesStats(model, select, status, type, role);
        return "stats";
    }
}
