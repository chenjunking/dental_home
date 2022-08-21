package com.dental.home.app.common;

public enum DBTypeEnum {
    master("master"), cluster("cluster");
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
