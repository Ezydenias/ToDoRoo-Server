package com.Roo.demo.service;

import com.Roo.demo.models.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

class AuthentificationService {
    public int getCurrentUserId() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
