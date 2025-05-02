package com.Roo.demo.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RooPasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }
}
