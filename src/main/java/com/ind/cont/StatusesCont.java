package com.ind.cont;

import com.ind.models.Devices;
import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StatusesCont extends Global {

    @GetMapping("/device/{id}/serviceable")
    public String serviceableDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Исправен);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/service";
    }

    @GetMapping("/device/{id}/faulty")
    public String faultyDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Неисправен);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/repair")
    public String repairDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Ремонтируется);
        devices.setServes(getUsernameLastname());
        devices.setServesId(getUserID());
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/test")
    public String testDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Протестировать);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/testing")
    public String testingDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Тестируется);
        devices.setServes(getUsernameLastname());
        devices.setServesId(getUserID());
        repoDevices.save(devices);
        return "redirect:/index";
    }

}
