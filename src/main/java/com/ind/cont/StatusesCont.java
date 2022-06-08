package com.ind.cont;

import com.ind.cont.general.Attributes;
import com.ind.models.Devices;
import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StatusesCont extends Attributes {

    @GetMapping("/device/{id}/serviceable")
    public String ServiceableDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        if (devices.getStatus() == Status.Ремонтируется) {
            devices.setRepaired(devices.getRepaired() + 1);
        } else {
            devices.setTesting(devices.getTesting() + 1);
        }
        devices.setStatus(Status.Исправен);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/service";
    }

    @GetMapping("/device/{id}/faulty")
    public String FaultyDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Неисправен);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/repair")
    public String RepairDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Ремонтируется);
        devices.setServes(getFirstnameLastname());
        devices.setServesId(getUserID());
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/test")
    public String TestDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Протестировать);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/tested")
    public String TestedDevice(@PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Тестируется);
        devices.setServes(getFirstnameLastname());
        devices.setServesId(getUserID());
        repoDevices.save(devices);
        return "redirect:/index";
    }

}
