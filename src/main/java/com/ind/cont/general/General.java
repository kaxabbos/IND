package com.ind.cont.general;

import com.ind.models.Users;
import com.ind.models.enums.DeviceType;
import com.ind.repo.RepoDevices;
import com.ind.repo.RepoUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public class General {
    @Autowired
    protected RepoUsers repoUsers;

    @Autowired
    protected RepoDevices repoDevices;

    @Value("${upload.img}")
    protected String uploadImg;

    protected Map<DeviceType, String> defDevices = new HashMap<>();

    {
        defDevices.put(DeviceType.Ноутбук, "default/laptop.png");
        defDevices.put(DeviceType.МФУ, "default/MFPs.png");
        defDevices.put(DeviceType.Ксерокс, "default/xerox.png");
        defDevices.put(DeviceType.ПК, "default/pc.png");
        defDevices.put(DeviceType.Планшет, "default/tablet.png");
        defDevices.put(DeviceType.Принтер, "default/printer.png");
        defDevices.put(DeviceType.Сервер, "default/server.png");
        defDevices.put(DeviceType.Сканер, "default/scanner.png");
        defDevices.put(DeviceType.Шредер, "default/shredder.png");
    }

    protected String defAvatar = "default/avatar.png";

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
}
