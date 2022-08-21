package com.dental.home.app.utils;/*
 *                        _oo0oo_
 *                       o8888888o
 *                       88" . "88
 *                       (| -_- |)
 *                       0\  =  /0
 *                     ___/`---'\___
 *                   .' \\|     |// '.
 *                  / \\|||  :  |||// \
 *                 / _||||| -:- |||||- \
 *                |   | \\\  - /// |   |
 *                | \_|  ''\---/''  |_/ |
 *                \  .-\__  '-'  ___/-. /
 *              ___'. .'  /--.--\  `. .'___
 *           ."" '<  `.___\_<|>_/___.' >' "".
 *          | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *          \  \ `_.   \_ __\ /__ _/   .-` /  /
 *      =====`-.____`.___ \_____/___.-`___.-'=====
 *                        `=---='
 *
 *
 *      ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 *            佛祖保佑     永不宕机     永无BUG
 *
 * @ClassName: FileUtil
 * @Author: junking
 * @LastEditTime: 2022/4/26 11:57
 * @Version: 1.0
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *@ClassName: FileUtil
 *@Description:  I/O 操作工具类
 *@Params:
 *@Return:
 *@Author xxw
 *@Date 2021/3/28
 */

public class FileUtil {

    /*
     * 文件写入
     * */
    public static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.getParentFile().exists()) {
            // 没有目录先创建目录
            dest.getParentFile().mkdir();
        }
        // 判断文件是否存在
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }

    /*
     * 文件读取
     * */
    public static String readFile(String fileName) throws Exception {
        return new String(Files.readAllBytes(new File(fileName).toPath()));
    }
}
