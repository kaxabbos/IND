package com.ind.cont;

import com.ind.cont.general.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatsCont extends Attributes {

    @GetMapping("/stats")
    public String Stats(Model model){
        AddAttributesStats(model);
        return "stats";
    }


}
