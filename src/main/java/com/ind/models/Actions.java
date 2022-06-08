package com.ind.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Actions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idUser;
    private String action;
    private String date;

    public Actions() {
    }

    public Actions(Long idUser, String action, String date) {
        this.idUser = idUser;
        this.action = action;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
