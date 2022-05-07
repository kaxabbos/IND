package com.ind.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    Пользователь,
    Администратор,
    Техник,
    Тестировщик;

    @Override
    public String getAuthority() {
        return name();
    }
}
