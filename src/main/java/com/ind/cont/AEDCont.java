package com.ind.cont;

import com.ind.models.Devices;
import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Controller
public class AEDCont extends Global {

    @GetMapping("/add")
    public String add(Model model) {
        attributes(model);
        model.addAttribute("types", Arrays.asList(DeviceType.values()));
        model.addAttribute("DeviceTypeAll", DeviceType.Все);
        return "add";
    }

    @PostMapping("/device/add")
    public String addDevice(Model model, @RequestParam String name, @RequestParam DeviceType type, @RequestParam String description, @RequestParam MultipartFile file) {
        Devices device = new Devices(name, type, checkUsernameLastname(), checkUserID(), Status.Исправен);

        if (description == null || description.equals("")) device.setDescription(null);
        else device.setDescription(description);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String result_poster = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    result_poster = "devices/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + result_poster));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Слишком большой размер аватарки");
                attributes(model);
                model.addAttribute("types", Arrays.asList(DeviceType.values()));
                model.addAttribute("DeviceTypeAll", DeviceType.Все);
                return "add";
            }
            device.setFile(result_poster);
        } else {
            switch (type) {
                case ПК -> device.setFile(defPc);
                case МФУ -> device.setFile(defMFPs);
                case Ксерокс -> device.setFile(defXerox);
                case Ноутбук -> device.setFile(defLaptop);
                case Планшет -> device.setFile(defTablet);
                case Принтер -> device.setFile(defPrinter);
                case Сервер -> device.setFile(defServer);
                case Сканер -> device.setFile(defScanner);
                case Шредер -> device.setFile(defShredder);
            }
        }

        repoDevices.save(device);

        return "redirect:/myDevices";
    }

    @GetMapping("/device/{id}/edit")
    public String edit(Model model, @PathVariable(value = "id") Long id) {
        attributes(model);
        model.addAttribute("types", Arrays.asList(DeviceType.values()));
        model.addAttribute("device", repoDevices.findById(id).orElseThrow());
        model.addAttribute("DeviceTypeAll", DeviceType.Все);
        return "edit";
    }

    @PostMapping("/device/{id}/edit")
    public String editDevice(Model model, @PathVariable(value = "id") Long id, @RequestParam String name, @RequestParam DeviceType type, @RequestParam String description, @RequestParam MultipartFile file) {
        Devices device = repoDevices.findById(id).orElseThrow();

        device.setName(name);
        device.setDeviceType(type);
        if (description == null || description.equals("")) device.setDescription(null);
        else device.setDescription(description);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String result_poster = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    result_poster = "devices/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + result_poster));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Слишком большой размер аватарки");
                attributes(model);
                model.addAttribute("types", Arrays.asList(DeviceType.values()));
                model.addAttribute("device", repoDevices.findById(id).orElseThrow());
                model.addAttribute("DeviceTypeAll", DeviceType.Все);
                return "edit";
            }
            device.setFile(result_poster);
        }

        repoDevices.save(device);

        return "redirect:/myDevices";
    }

    @GetMapping("/device/{id}/serviceable")
    public String serviceableDevice(Model model, @PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Исправен);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/service";
    }

    @GetMapping("/device/{id}/faulty")
    public String faultyDevice(Model model, @PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Неисправен);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/delete")
    public String deleteDevice(Model model, @PathVariable(value = "id") Long id) {
        repoDevices.deleteById(id);
        return "redirect:/myDevices";
    }

    @GetMapping("/device/{id}/repair")
    public String repairDevice(Model model, @PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Ремонтируется);
        devices.setServes(checkUsernameLastname());
        devices.setServesId(checkUserID());
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/test")
    public String testDevice(Model model, @PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Протестировать);
        devices.setServes(null);
        devices.setServesId(0);
        repoDevices.save(devices);
        return "redirect:/index";
    }

    @GetMapping("/device/{id}/testing")
    public String testingDevice(Model model, @PathVariable(value = "id") Long id) {
        Devices devices = repoDevices.findById(id).orElseThrow();
        devices.setStatus(Status.Тестируется);
        devices.setServes(checkUsernameLastname());
        devices.setServesId(checkUserID());
        repoDevices.save(devices);
        return "redirect:/index";
    }

}
