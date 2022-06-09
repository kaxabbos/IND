package com.ind.cont;

import com.ind.cont.general.Attributes;
import com.ind.models.Users;
import com.ind.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfilesCont extends Attributes {
    @GetMapping("/profiles")
    public String profiles(Model model) {
        AddAttributesProfiles(model);
        return "profiles";
    }

    @PostMapping("/profiles/{id}/edit")
    public String profileEditRole(@PathVariable(value = "id") Long id, @RequestParam Role role) {
        Users user = repoUsers.getById(id);
        user.setRole(role);
        repoUsers.save(user);
        AddAction("Отредактирован пользователь: " + user.getFirstname() + " " + user.getLastname());
        return "redirect:/profiles";
    }

    @PostMapping("/profiles/{id}/delete")
    public String profileDelete(Model model, @PathVariable(value = "id") Long id) {
        if (id == getUser().getId()) {
            model.addAttribute("message", "Вы не можете удалить самого себя");
            AddAttributesProfiles(model);
            return "profiles";
        }

        Users user = repoUsers.getById(id);
        repoUsers.delete(user);

        AddAction("Пользователь удален: " + user.getFirstname() + " " + user.getLastname());
        return "redirect:/profiles";
    }
}
