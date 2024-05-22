package com.techsoft.springsecurity.role_and_permission;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Authority {

    ADMIN_READ("read"),
    ADMIN_DELETE("delete");

    public final String permission;

//    private Authority(String label) {
//        this.label = label;
//    }
}
