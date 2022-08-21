package com.dental.home.app.common.define;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowTagDefine {

    /**
     * 标签类型
     */
    public enum FlowTagType {
        project("project", "项目名称"),
        remark("remark", "备注");

        private String code;
        private String name;

        // 普通方法
        public static String getName(String code) {
            for (FlowTagType c : FlowTagType.values()) {
                if (c.getCode().equals(code)) {
                    return c.name;
                }
            }
            return "";
        }

        public static List<Map<String, String>> toList() {
            List<Map<String, String>> list = new ArrayList();
            for (FlowTagType item : FlowTagType.values()) {
                Map<String, String> map = new HashMap<>();
                map.put("code", item.getCode());
                map.put("name", item.getName());
                list.add(map);
            }
            return list;
        }


        private FlowTagType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
