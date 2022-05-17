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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Controller
public class AEDCont extends Global {

    @GetMapping("/add")
    public String add(Model model) {
        AddAttributesAdd(model);
        return "add";
    }

    @PostMapping("/device/add")
    public String addDevice(Model model, @RequestParam String name, @RequestParam DeviceType type, @RequestParam String description, @RequestParam MultipartFile file) {
        Devices device = new Devices(name, type, getUsernameLastname(), getUserID(), Status.Исправен);

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
                AddAttributesAdd(model);
                return "add";
            }
            device.setFile(result_poster);
        } else {
            device.setFile(defDevices.get(type));
        }

        repoDevices.save(device);

        return "redirect:/myDevices";
    }

    @GetMapping("/device/{id}/edit")
    public String edit(Model model, @PathVariable(value = "id") Long id) {
        AddAttributesEdit(model, id);
        return "edit";
    }

    @GetMapping("/device/{id}/edit/default/file")
    public String EditDefaultFile(@PathVariable Long id, Model model) {
        Devices device = repoDevices.getById(id);

        boolean flag = true;

        for (String i : defDevices.values()) {
            if (device.getFile().equals(i)) {
                flag = false;
                break;
            }
        }

        if (flag) {
            try {
                Files.delete(Paths.get(uploadImg + "/" + device.getFile()));
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось изменить фотографию");
                AddAttributesIndex(model);
                return "add";
            }
        }

        device.setFile(defDevices.get(device.getDeviceType()));

        repoDevices.save(device);

        return "redirect:/myDevices";
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
                AddAttributesEdit(model, id);
                return "edit";
            }

            boolean flag = true;

            for (String i : defDevices.values()) {
                if (device.getFile().equals(i)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                try {
                    Files.delete(Paths.get(uploadImg + "/" + device.getFile()));
                } catch (IOException e) {
                    model.addAttribute("message", "Не удалось изменить фотографию");
                    AddAttributesIndex(model);
                    return "add";
                }
            }

            device.setFile(result_poster);
        }

        repoDevices.save(device);

        return "redirect:/myDevices";
    }

    @GetMapping("/device/{id}/delete")
    public String deleteDevice(@PathVariable(value = "id") Long id) {
        repoDevices.deleteById(id);
        return "redirect:/myDevices";
    }
}
