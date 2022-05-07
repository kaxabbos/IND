package com.ind.cont;

import com.ind.models.Users;
import com.ind.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class ProfilesCont extends Global {
    @GetMapping("/profiles")
    public String profiles(Model model) {
        model.addAttribute("usersList", usersList());
        model.addAttribute("roles", Arrays.asList(Role.values()));
        attributes(model);
        return "profiles";
    }

    @PostMapping("/profiles/{id}/edit")
    public String profileEditRole(@PathVariable(value = "id") Long id, @RequestParam Role role) {
        Users user = repoUsers.getById(id);
        user.setRole(role);
        repoUsers.save(user);
        return "redirect:/profiles";
    }

    @PostMapping("/profiles/{id}/delete")
    public String profileDelete(Model model, @PathVariable(value = "id") Long id) {
        if (id == checkUser().getId()) {
            model.addAttribute("usersList", usersList());
            model.addAttribute("roles", Arrays.asList(Role.values()));
            model.addAttribute("message", "Вы не можете удалить самого себя");
            attributes(model);
            return "profiles";
        }

        repoUsers.deleteById(id);
        return "redirect:/profiles";
    }
}
