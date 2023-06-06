package com.alancss.devmoneyapi.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class AppUtils {

    public static URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
    }
}
