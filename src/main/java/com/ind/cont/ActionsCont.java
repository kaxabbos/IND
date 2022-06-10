package com.ind.cont;

import com.ind.cont.global.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ActionsCont extends Attributes {

    @GetMapping("/actions")
    public String ActionsList(Model model) {
        AddAttributesActionsList(model);
        return "actionsList";
    }

    @GetMapping("/actions/{idUser}")
    public String Actions(Model model, @PathVariable Long idUser) {
        AddAttributesActions(model, idUser);
        return "actions";
    }

}
