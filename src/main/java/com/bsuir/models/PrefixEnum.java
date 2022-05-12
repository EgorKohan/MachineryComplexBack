package com.bsuir.models;

import lombok.Getter;

@Getter
public enum PrefixEnum {

    OPERATION(AgriculturalOperation.class, "r"),
    TRAILER(Trailer.class, "m"),
    MACHINE(SelfPropelledMachine.class, "t");

    private final Class<?> cl;
    private final String prefixLowRegister;

    PrefixEnum(Class<?> cl, String prefixLowRegister) {
        this.cl = cl;
        this.prefixLowRegister = prefixLowRegister;
    }
}
