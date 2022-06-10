package com.ind.cont.global;

import com.ind.models.Devices;
import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Role;
import com.ind.models.enums.Select;
import com.ind.models.enums.Status;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Attributes extends Global {
    protected void AddAttributes(Model model) {
        model.addAttribute("avatar", getAvatar());
        model.addAttribute("firstnameLastname", getFirstnameLastname());
        model.addAttribute("avatar", getAvatar());
        model.addAttribute("role", getUserRole());
        if (getUserRole().equals(String.valueOf(Role.Техник))) {
            model.addAttribute("def", (repoDevices.findByStatus(Status.Неисправен).size()));
        } else if (getUserRole().equals(String.valueOf(Role.Тестировщик))) {
            model.addAttribute("def", (repoDevices.findByStatus(Status.Протестировать).size()));
        }
    }

    protected void AddAttributesProfile(Model model) {
        AddAttributes(model);
        model.addAttribute("user", getUser());
        int allDevice = repoDevices.findByUserId(getUserID()).size();
        int right = (repoDevices.findByUserIdAndStatus(getUserID(), Status.Исправен)).size();
        model.addAttribute("allDevice", allDevice);
        model.addAttribute("right", right);
        model.addAttribute("left", allDevice - right);
    }

    protected void AddAttributesProfiles(Model model) {
        AddAttributes(model);
        model.addAttribute("usersList", repoUsers.findAllByOrderByRole());
        model.addAttribute("roles", Arrays.asList(Role.values()));
    }

    protected void AddAttributesService(Model model) {
        AddAttributes(model);
        model.addAttribute("devices", repoDevices.findAll());
    }

    protected void AddAttributesStats(Model model, Select select, Status status, DeviceType type, Role role) {
        AddAttributes(model);
        if (select == Select.Оргтехника) {
            List<Devices> devices;
            if (status == Status.Все && type == DeviceType.Все) devices = repoDevices.findAll();
            else if (status == Status.Все) devices = repoDevices.findByDeviceType(type);
            else if (type == DeviceType.Все) devices = repoDevices.findByStatus(status);
            else devices = repoDevices.findByStatusAndDeviceType(status, type);
            model.addAttribute("devices", devices);
        } else {
            model.addAttribute("users", repoUsers.findByRole(role));
        }
        model.addAttribute("roles", Role.values());
        model.addAttribute("selects", Select.values());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", DeviceType.values());
        model.addAttribute("roleSelected", role);
        model.addAttribute("deviceStatusSelected", status);
        model.addAttribute("deviceTypeSelected", type);
//        model.addAttribute("selectSelected", select);
    }

    protected void AddAttributesActionsList(Model model) {
        AddAttributes(model);
        model.addAttribute("users", repoUsers.findAllByOrderByRole());
    }

    protected void AddAttributesActions(Model model, Long idUser) {
        AddAttributes(model);
        model.addAttribute("user", repoUsers.getById(idUser));
        model.addAttribute("actions", repoActions.findByIdUser(idUser));
    }

    protected void AddAttributesAdd(Model model) {
        AddAttributes(model);
        model.addAttribute("types", Arrays.asList(DeviceType.values()));
        model.addAttribute("DeviceTypeAll", DeviceType.Все);
    }

    protected void AddAttributesEdit(Model model, Long idDevice) {
        AddAttributes(model);
        model.addAttribute("types", Arrays.asList(DeviceType.values()));
        model.addAttribute("device", repoDevices.getById(idDevice));
        model.addAttribute("DeviceTypeAll", DeviceType.Все);
    }

    protected void AddAttributesIndex(Model model) {
        AddAttributes(model);
        model.addAttribute("user", getUser());
        model.addAttribute("devices", repoDevices.findAll());
    }

    protected void AddAttributesMyDevices(Model model) {
        AddAttributes(model);
        model.addAttribute("devices", repoDevices.findByUserId(getUserID()));
        model.addAttribute("serviceable", Status.Исправен);
    }

    protected void AddAttributesSearch(Model model, Status status, DeviceType type) {
        AddAttributes(model);
        List<Devices> devices;
        if (status == Status.Все && type == DeviceType.Все) devices = repoDevices.findAll();
        else if (status == Status.Все) devices = repoDevices.findByDeviceType(type);
        else if (type == DeviceType.Все) devices = repoDevices.findByStatus(status);
        else devices = repoDevices.findByStatusAndDeviceType(status, type);
        model.addAttribute("devices", devices);
        model.addAttribute("test", Status.Протестировать);
        model.addAttribute("unserviceable", Status.Неисправен);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", DeviceType.values());
        model.addAttribute("deviceStatusSelected", status);
        model.addAttribute("deviceTypeSelected", type);
    }

    protected void AddAttributesSearch(Model model, String search) {
        AddAttributes(model);
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
}
