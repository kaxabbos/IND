package com.ind.cont.general;

import com.ind.models.Devices;
import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Role;
import com.ind.models.enums.Status;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Attributes extends General {
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

    protected void AddAttributesService(Model model) {
        AddAttributes(model);
        model.addAttribute("devices", repoDevices.findAll());
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

    protected void AddAttributesSearch(Model model, Status status, DeviceType type) {
        AddAttributes(model);
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
