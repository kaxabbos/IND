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
public class StatsCont extends Attributes {

    @GetMapping("/stats")
    public String Stats(Model model) {
        AddAttributesStats(model, Status.Все, DeviceType.Все);
        return "stats";
    }

    @PostMapping("/stats/search/status_type")
    public String StatsSearch(Model model, @RequestParam Status status, @RequestParam DeviceType type) {
        AddAttributesStats(model, status, type);
        return "stats";
    }
}
