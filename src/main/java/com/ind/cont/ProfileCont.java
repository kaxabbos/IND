package com.ind.cont;

import com.ind.cont.general.Attributes;
import com.ind.models.Users;
import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ProfileCont extends Attributes {
    @GetMapping("/profile")
    public String profile(Model model) {
        AddAttributes(model);
        model.addAttribute("user", getUser());
        int allDevice = repoDevices.findByUserId(getUserID()).size();
        int right = (repoDevices.findByUserIdAndStatus(getUserID(), Status.Исправен)).size();
        model.addAttribute("allDevice", allDevice);
        model.addAttribute("right", right);
        model.addAttribute("left", allDevice - right);
        return "profile";
    }

    @PostMapping("/profile/edit")
    String profileEdit(Model model, @RequestParam String username, @RequestParam String lastname, @RequestParam String email, @RequestParam String passwordOld, @RequestParam String password, @RequestParam String passwordRepeat) {
        Users user = getUser();

        if (!passwordOld.equals(user.getPassword())) {
            model.addAttribute("message", "Некорректный ввод текущего пароля");
            AddAttributes(model);
            model.addAttribute("user", getUser());
            return "profile";
        }

        if (!password.equals("") && !passwordRepeat.equals("")) {
            if (!password.equals(passwordRepeat)) {
                model.addAttribute("message", "Новые пароли не совпадают");
                AddAttributes(model);
                model.addAttribute("user", getUser());
                return "profile";
            }
            user.setPassword(password);
        }

        if (!username.equals("")) user.setUsername(username);
        if (!lastname.equals("")) user.setLastname(lastname);
        if (!email.equals("")) user.setEmail(email);

        repoUsers.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/profile/changeAvatar")
    String changeAvatar(Model model, @RequestParam("avatar") MultipartFile avatar) {
        if (avatar != null && !Objects.requireNonNull(avatar.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "avatars/" + uuidFile + "_" + avatar.getOriginalFilename();
                    avatar.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось изменить аватарку");
                AddAttributesIndex(model);
                return "profile";
            }
            Users user = getUser();

            if (!user.getAvatar().equals(defAvatar)) {
                try {
                    Files.delete(Paths.get(uploadImg + "/" + user.getAvatar()));
                } catch (IOException e) {
                    model.addAttribute("message", "Не удалось изменить аватарку");
                    AddAttributesIndex(model);
                    return "profile";
                }
            }
            user.setAvatar(res);
            repoUsers.save(user);
        }

        return "redirect:/profile";
    }

}
