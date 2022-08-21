package com.dental.home.app.vo;

public class DicVO {

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 字典枚举
     */
    private String enumValueListStr;

    public DicVO(String type, String name) {
        this.setType(type);
        this.setName(name);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnumValueListStr() {
        return enumValueListStr;
    }

    public void setEnumValueListStr(String enumValueListStr) {
        this.enumValueListStr = enumValueListStr;
    }
}
