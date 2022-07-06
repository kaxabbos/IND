package com.ind.cont;

import com.ind.cont.global.Attributes;
import com.ind.models.Users;
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
        AddAttributesProfile(model);
        return "profile";
    }

    @PostMapping("/profile/edit")
    String ProfileEdit(Model model, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam String passwordOld, @RequestParam String password, @RequestParam String passwordRepeat) {
        Users user = getUser();

        if (!passwordOld.equals(user.getPassword())) {
            AddAttributesProfile(model);
            model.addAttribute("message", "Некорректный ввод текущего пароля");
            return "profile";
        }

        if (!password.equals("") && !passwordRepeat.equals("")) {
            if (!password.equals(passwordRepeat)) {
                AddAttributesProfile(model);
                model.addAttribute("message", "Новые пароли не совпадают");
                return "profile";
            }
            user.setPassword(password);
        }

        if (!firstname.equals("")) user.setFirstname(firstname);
        if (!lastname.equals("")) user.setLastname(lastname);
        if (!email.equals("")) user.setEmail(email);

        repoUsers.save(user);
        AddAction("Изменение профиля");
        return "redirect:/profile";
    }

    @PostMapping("/profile/changeAvatar")
    String ChangeAvatar(Model model, @RequestParam MultipartFile avatar) {
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
                AddAttributesProfile(model);
                model.addAttribute("message", "Не удалось изменить аватарку");
                return "profile";
            }
            Users user = getUser();

            if (!user.getAvatar().equals(defAvatar)) {
                try {
                    Files.delete(Paths.get(uploadImg + "/" + user.getAvatar()));
                } catch (IOException e) {
                    AddAttributesProfile(model);
                    model.addAttribute("message", "Не удалось изменить аватарку");
                    return "profile";
                }
            }
            user.setAvatar(res);
            repoUsers.save(user);
        }

        AddAction("Изменение фотографии профиля");

        return "redirect:/profile";
    }
}
