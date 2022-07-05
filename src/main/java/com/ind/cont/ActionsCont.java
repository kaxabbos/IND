package com.ind.cont;

import com.ind.cont.global.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ActionsCont extends Attributes {

    @GetMapping("/actions")
    public String ActionsList(Model model) {
        AddAttributesActionsList(model);
        return "actionsList";
    }

    @GetMapping("/actions/{idUser}")
    public String Actions(Model model, @PathVariable Long idUser) {
        AddAttributesActions(model, idUser, null);
        return "actions";
    }

    @PostMapping("/search/{idUser}/actions")
    public String SearchActions(Model model, @RequestParam String date, @PathVariable String idUser) {
        AddAttributesActions(model, Long.parseLong(idUser), date);
        return "actions";
    }

}
