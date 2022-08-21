package com.dental.home.app.utils;

import java.io.*;

public class SerializeUtil {

    public SerializeUtil() {
    }

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception var4) {
            throw new RuntimeException("序列化错误", var4);
        }
    }

    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException();
        } else {
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;

            Object var5;
            try {
                bais = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bais);
                var5 = ois.readObject();
            } catch (Exception var12) {
                throw new RuntimeException("反序列化错误", var12);
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }

                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException var11) {
                    ;
                }

            }

            return var5;
        }
    }

}
