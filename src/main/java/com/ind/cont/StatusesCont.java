package com.ind.cont;

import com.ind.cont.global.Attributes;
import com.ind.models.Devices;
import com.ind.models.Users;
import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StatusesCont extends Attributes {

    @GetMapping("/device/{id}/faulty")
    public String FaultyDevice(@PathVariable(value = "id") Long id) {
        Devices device = repoDevices.findById(id).orElseThrow();
        device.setStatus(Status.Неисправен);
        if (device.getServesId() != 0L) {
            Users user = getUser();
            user.setQuantityWorks(user.getQuantityWorks() + 1);
            repoUsers.save(user);
        }
        device.setServes(null);
        device.setServesId(0);
        repoDevices.save(device);
        AddAction("Изменение статуса устройства \"" + device.getName() + "\" на: " + device.getStatus());
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/serviceable")
    public String ServiceableDevice(@PathVariable(value = "id") Long id) {
        Devices device = repoDevices.findById(id).orElseThrow();
        if (device.getStatus() == Status.Ремонтируется) {
            device.setRepaired(device.getRepaired() + 1);
        } else {
            device.setTesting(device.getTesting() + 1);
        }

        Users user = getUser();
        user.setQuantityWorks(user.getQuantityWorks() + 1);
        repoUsers.save(user);

        device.setStatus(Status.Исправен);
        device.setServes(null);
        device.setServesId(0);
        repoDevices.save(device);
        AddAction("Изменение статуса устройства \"" + device.getName() + "\" на: " + device.getStatus());
        return "redirect:/service";
    }

    @GetMapping("/device/{id}/repair")
    public String RepairDevice(@PathVariable(value = "id") Long id) {
        Devices device = repoDevices.findById(id).orElseThrow();
        device.setStatus(Status.Ремонтируется);
        device.setServes(getFirstnameLastname());
        device.setServesId(getUserID());
        repoDevices.save(device);
        AddAction("Изменение статуса устройства \"" + device.getName() + "\" на: " + device.getStatus());
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/test")
    public String TestDevice(@PathVariable(value = "id") Long id) {
        Devices device = repoDevices.findById(id).orElseThrow();
        if (device.getServesId() != 0L) {
            Users user = getUser();
            user.setQuantityWorks(user.getQuantityWorks() + 1);
            repoUsers.save(user);
        }
        device.setStatus(Status.Протестировать);
        device.setServes(null);
        device.setServesId(0);
        repoDevices.save(device);
        AddAction("Изменение статуса устройства \"" + device.getName() + "\" на: " + device.getStatus());
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/tested")
    public String TestedDevice(@PathVariable(value = "id") Long id) {
        Devices device = repoDevices.findById(id).orElseThrow();
        device.setStatus(Status.Тестируется);
        device.setServes(getFirstnameLastname());
        device.setServesId(getUserID());
        repoDevices.save(device);
        AddAction("Изменение статуса устройства \"" + device.getName() + "\" на: " + device.getStatus());
        return "redirect:/index";
    }
}
