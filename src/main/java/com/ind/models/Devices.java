package com.ind.models;

import com.ind.models.enums.DeviceType;
import com.ind.models.enums.Status;

import javax.persistence.*;

@Entity
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    private String description;
    private String user;
    private long userId;
    private String file;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String serves;
    private long servesId;

    public Devices() {
    }

    public Devices(String name, DeviceType deviceType, String user, long userId, Status status) {
        this.name = name;
        this.deviceType = deviceType;
        this.user = user;
        this.userId = userId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getServes() {
        return serves;
    }

    public void setServes(String serves) {
        this.serves = serves;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getServesId() {
        return servesId;
    }

    public void setServesId(long servesId) {
        this.servesId = servesId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
