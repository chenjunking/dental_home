package com.dental.home.app.common.define;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MpCustomerDefine {

    /**
     * 小程序用户角色
     */
    public enum MpCustomerRole {
        customer("customer", "普通用户"),
        admin("admin", "管理员"),
        verifier("verifier", "核销员");

        private String code;
        private String name;

        // 普通方法
        public static String getName(String code) {
            for (MpCustomerRole c : MpCustomerRole.values()) {
                if (c.getCode().equals(code)) {
                    return c.name;
                }
            }
            return null;
        }

        public static List<Map<String, String>> toList() {
            List<Map<String, String>> list = new ArrayList();
            for (MpCustomerRole item : MpCustomerRole.values()) {
                Map<String, String> map = new HashMap<>();
                map.put("code", item.getCode());
                map.put("name", item.getName());
                list.add(map);
            }
            return list;
        }


        private MpCustomerRole(String code, String name) {
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
