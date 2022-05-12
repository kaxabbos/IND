package com.ind.cont;

import com.ind.models.Devices;
import com.ind.models.Users;
import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Role;
import com.ind.models.enums.Status;
import com.ind.repo.RepoDevices;
import com.ind.repo.RepoUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Global {

    @Autowired
    protected RepoUsers repoUsers;

    @Autowired
    protected RepoDevices repoDevices;

    @Value("${upload.img}")
    protected String uploadImg;

    protected String defAvatar = "default/avatar.jpeg";
    protected String defLaptop = "default/laptop.png";
    protected String defMFPs = "default/MFPs.png";
    protected String defPc = "default/pc.png";
    protected String defPrinter = "default/printer.png";
    protected String defScanner = "default/scanner.png";
    protected String defServer = "default/server.png";
    protected String defShredder = "default/shredder.png";
    protected String defTablet = "default/tablet.png";
    protected String defXerox = "default/xerox.png";

    protected void attributes(Model model) {
        model.addAttribute("avatar", getAvatar());
        model.addAttribute("usernameLastname", getUsernameLastname());
        model.addAttribute("avatar", getAvatar());
        model.addAttribute("role", getUserRole());
        if (Objects.equals(getUserRole(), String.valueOf(Role.Техник))) {
            model.addAttribute("def", (repoDevices.findByStatus(Status.Неисправен).size()));
        } else if (Objects.equals(getUserRole(), String.valueOf(Role.Тестировщик))) {
            model.addAttribute("def", (repoDevices.findByStatus(Status.Протестировать).size()));
        }
    }

    protected void attributesService(Model model) {
        attributes(model);

        model.addAttribute("devices", repoDevices.findAll());
    }

    protected void attributesSearch(Model model, Status status, DeviceType type) {
        attributes(model);

        List<Devices> temp;
        if (status == Status.Все && type == DeviceType.Все) temp = repoDevices.findAll();
        else if (status == Status.Все) temp = repoDevices.findByDeviceType(type);
        else if (type == DeviceType.Все) temp = repoDevices.findByStatus(status);
        else temp = repoDevices.findByStatusAndDeviceType(status, type);
        model.addAttribute("devices", temp);
        model.addAttribute("test", Status.Протестировать);
        model.addAttribute("unserviceable", Status.Неисправен);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("deviceStatusSelected", status);
        model.addAttribute("types", DeviceType.values());
        model.addAttribute("deviceTypeSelected", type);
    }

    protected void attributesSearch(Model model, String search) {
        attributes(model);

        List<Devices> temp = new ArrayList<>();
        for (Devices i : repoDevices.findAll()) if (i.getName().contains(search)) temp.add(i);

        model.addAttribute("devices", temp);
        model.addAttribute("test", Status.Протестировать);
        model.addAttribute("unserviceable", Status.Неисправен);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("deviceStatusSelected", Status.Все);
        model.addAttribute("types", DeviceType.values());
        model.addAttribute("deviceTypeSelected", DeviceType.Все);
    }

    protected String getUserRole() {
        Users user = getUser();
        if (user != null) return String.valueOf(user.getRole());
        return "NOT";
    }

    protected Long getUserID() {
        Users user = getUser();
        if (user != null) return user.getId();
        return 0L;
    }

    protected String getAvatar() {
        Users user = getUser();
        if (user != null) return user.getAvatar();
        return defAvatar;
    }

    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return repoUsers.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getUsernameLastname() {
        Users user = getUser();
        if (user != null) return user.getUsername() + " " + user.getLastname();
        return "Добро пожаловать";
    }

    protected List<Users> usersList() {
        List<Users> temp = repoUsers.findByRole(Role.Администратор);
        temp.addAll(repoUsers.findByRole(Role.Техник));
        temp.addAll(repoUsers.findByRole(Role.Тестировщик));
        temp.addAll(repoUsers.findByRole(Role.Пользователь));
        return temp;
    }
}
