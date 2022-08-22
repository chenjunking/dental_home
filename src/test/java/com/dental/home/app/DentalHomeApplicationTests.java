package com.dental.home.app;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

@SpringBootTest
@Slf4j
class DentalHomeApplicationTests {

    String dbUrl = "jdbc:mysql://127.0.0.1:3306/dental_home?characterEncoding=utf8&useSSL=false&useTimezone=true&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true";
    String dbUserName = "root";
    String dbPassWord = "123456789";
    String dbType = DbType.MYSQL.getDb();

    String packageFix = "com.dental.home.app.";
    String javaFilePathFix = "java/com/dental/home/app";
    String resourceFilePathFix = "resources/com/dental/home/app";

    //流水信息
    @Test
    void couponGen() throws TemplateException, IOException {
        String model_name = "flow";
        String[] tables= {
//                "f_flow_tag",
//                "f_flow_record",
//                "f_flow_record_tag"
        };
        genCode(model_name,tables,false);
    }


    //系统表
    @Test
    void systemGen() throws TemplateException, IOException {
        String model_name = "system";
        String[] tables= {
                "t_institution",
                "t_role",
                "t_user",
                "t_user_role",
                "t_user_login_status",
                "t_menu",
        };
        genCode(model_name,tables,true);
    }

    //客户相关表
    @Test
    void customerGen() throws TemplateException, IOException {
        String model_name = "system";
        String[] tables= {
                "c_customer",
                "c_sick",
                "c_customer_sick",
        };
        genCode(model_name,tables,true);
    }


    /**
     * gen
     * @param model_name
     * @param tables
     * @throws IOException
     * @throws TemplateException
     */
    void genCode(String model_name,String[] tables,Boolean firstFlag) throws IOException, TemplateException {
        //获取项目目录
        String parent_dir = System.getProperty("user.dir");
        String packagePathFix = "/src/main/";

        //pojoAO路径前缀
        String pojoAODirFix = packagePathFix + javaFilePathFix + "/appObj/";
        //pojo路径前缀
        String pojoDirFix = packagePathFix + javaFilePathFix + "/entity/";
        //mapper路径前缀
        String mapperDirFix = packagePathFix + javaFilePathFix + "/dao/";
        //mapperXml路径前缀
        String mapperXmlDirFix = packagePathFix + resourceFilePathFix + "/dao/";
        //service路径前缀
        String serviceDirFix = packagePathFix + javaFilePathFix + "/service/";
        //serviceImpl路径前缀
        String serviceImplDirFix = packagePathFix + javaFilePathFix + "/service/impl/";
        //serviceImpl路径前缀
        String controllerDirFix = packagePathFix + javaFilePathFix + "/controller/";

        //获取pojo目录AO目录
        String pojoAODir = parent_dir + pojoAODirFix +model_name+"/";
        //获取pojo目录AO目录
        String pojoAOPackageName = packageFix+"appObj";
        //获取pojo目录
        String pojoDir = parent_dir + pojoDirFix +model_name+"/";
        //获取mapper目录
        String mapperDir = parent_dir + mapperDirFix + dbType + "/gen/" + model_name + "/";
        //获取mapperXml目录
        String mapperXmlDir = parent_dir + mapperXmlDirFix +dbType+"/gen/"+model_name+"/";
        //获取service目录
        String serviceDir = parent_dir + serviceDirFix +model_name+"/";
        String serviceImplDir = parent_dir + serviceImplDirFix + model_name+"/";
        //获取controller目录
//        String controllerDir = parent_dir + controllerDirFix + model_name+"/";

        //数据库配置
        DataSourceConfig dsc = new DataSourceConfig.Builder(dbUrl,dbUserName,dbPassWord)
                //	数据库查询
                .dbQuery(new MySqlQuery())
//				.dbQuery(new SqliteQuery())
//                .dbQuery(new OracleQuery())
                //数据库schema(部分数据库适用)
//                .schema("mybatis-plus")
                //数据库类型转换器
                .typeConvert(new MySqlTypeConvert())
//				.typeConvert(new SqliteTypeConvert())
//                .typeConvert(new OracleTypeConvert())
                //数据库关键字处理器
                .keyWordsHandler(new MySqlKeyWordsHandler())
                .build();

        // Step1：代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);

        // Step2：全局配置
        GlobalConfig gc = new GlobalConfig.Builder()
                //覆盖已生成文件
                .fileOverride()
                //禁止打开输出目录
                .disableOpenDir()
                //指定输出目录
//                .outputDir("/opt/baomidou")
                //作者名
                .author("cjj")
                //开启 kotlin 模式
//                .enableKotlin()
                //开启 swagger 模式
                .enableSwagger()
                //时间策略
                .dateType(DateType.ONLY_DATE)
                //注释日期
                .commentDate("yyyy-MM-dd")
                .build();
        mpg.global(gc);


        // Step:4：包配置
        //设置自定义输出目录（分布式项目使用）
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.entity, pojoDir);
        pathInfo.put(OutputFile.mapperXml, mapperXmlDir);
        if(firstFlag){
            pathInfo.put(OutputFile.mapper, mapperDir);
            pathInfo.put(OutputFile.service, serviceDir);
            pathInfo.put(OutputFile.serviceImpl, serviceImplDir);
//            pathInfo.put(OutputFile.controller, controllerDir);
        }

        PackageConfig pc = new PackageConfig.Builder()
                //父包名
                .parent(packageFix.substring(0,packageFix.length()-1))
                //父包模块名
                .moduleName("")
                //Entity 包名
                .entity("entity"+(StrUtil.isNotBlank(model_name)?"."+model_name:model_name))
                //Service 包名
                .service("service"+(StrUtil.isNotBlank(model_name)?"."+model_name:model_name))
                //ServiceImpl 包名
                .serviceImpl("service.impl"+(StrUtil.isNotBlank(model_name)?"."+model_name:model_name))
                //Mapper 包名
                .mapper("dao"+(StrUtil.isNotBlank(dbType)?"."+dbType:dbType)+".gen"+(StrUtil.isNotBlank(model_name)?"."+model_name:model_name))
                //Controller 包名
//                .controller("controller"+(StrUtil.isNotBlank(model_name)?"."+model_name:model_name))
                .other("appObj"+(StrUtil.isNotBlank(model_name)?"."+model_name:model_name))
                .pathInfo(pathInfo)
                .build();
        mpg.packageInfo(pc);

        //自定义模板
        TemplateConfig templateConfig = new TemplateConfig.Builder()
                .service("/genTemplates/service.java")
                .serviceImpl("/genTemplates/serviceImpl.java")
                .mapper("/genTemplates/mapper.java")
                .mapperXml("/genTemplates/mapper.xml")
                .controller("/genTemplates/controller.java")
                .build();
        mpg.template(templateConfig);


        // Step5：策略配置
        StrategyConfig strategy = new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                //增加表匹配(内存过滤)
                .addInclude(tables)
                .addTablePrefix("t_", "c_","f_")
//                .addFieldSuffix("")

                .entityBuilder()
                //设置父类
//                .superClass(BaseEntity.class)
                //禁用生成 serialVersionUID
//                .disableSerialVersionUID()
                //开启生成字段常量
                .enableColumnConstant()
                //开启链式模型
                .enableChainModel()
                //开启 lombok 模型
                .enableLombok()
                //开启 Boolean 类型字段移除 is 前缀
                .enableRemoveIsPrefix()
                //开启生成实体时生成字段注解
                .enableTableFieldAnnotation()
                //开启 ActiveRecord 模型
                .enableActiveRecord()
                //乐观锁字段名(数据库)
//                .versionColumnName("version")
                //乐观锁属性名(实体)
//                .versionPropertyName("version")
                //逻辑删除字段名(数据库)
                .logicDeleteColumnName("delete_flag")
                //逻辑删除属性名(实体)
                .logicDeletePropertyName("deleteFlag")
                //数据库表映射到实体的命名策略
                .naming(NamingStrategy.underline_to_camel)
                //数据库表字段映射到实体的命名策略
                .columnNaming(NamingStrategy.underline_to_camel)
                //添加父类公共字段
//                .addSuperEntityColumns("create_time","modify_time","version")
                //添加忽略字段
                .addIgnoreColumns("password")
                //添加表字段填充
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                //添加表字段填充
                .addTableFills(new Property("modify_time", FieldFill.INSERT_UPDATE))
                //全局主键类型
                .idType(IdType.ASSIGN_ID)
                //格式化文件名称
                .formatFileName("Entity%s")


                .controllerBuilder()
                //开启驼峰转连字符
                .enableHyphenStyle()
                //	开启生成@RestController 控制器
                .enableRestStyle()
                //	格式化文件名称
                .formatFileName("%sController")

                .serviceBuilder()
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImpl")

                .mapperBuilder()
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()
                //设置缓存实现类
//                .cache(MyMapperCache.class)
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sXml")
                .build();
        mpg.strategy(strategy);



        // Step6：执行代码生成操作
        FreemarkerTemplateEngine freemarkerTemplateEngine = new FreemarkerTemplateEngine();
        mpg.execute(freemarkerTemplateEngine);

        //生成AO类
        List<TableInfo> tableInfoList =  mpg.getConfig().getTableInfoList();
        if(null!=tableInfoList&&tableInfoList.size()>0){
            freemarker.template.Configuration TEMPLATE_CFG = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_0);
            TEMPLATE_CFG.setDefaultEncoding("UTF-8");
            TEMPLATE_CFG.setDirectoryForTemplateLoading(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "genTemplates"));
            Template template = TEMPLATE_CFG.getTemplate("appObj.java.ftl");
            template.setOutputEncoding("UTF-8");

            File appObjectPackageFile = new File(pojoAODir);
            if (!appObjectPackageFile.exists()) {
                appObjectPackageFile.mkdirs();
            }
            for (TableInfo tableInfo : tableInfoList) {
                String appObjectPackage = pojoAOPackageName.concat(".").concat(model_name);
                String entityName = tableInfo.getEntityName();
                String tableName = tableInfo.getName();
                String tableComment = tableInfo.getComment();

                String entityPackage = pc.getParent().concat(".").concat(pc.getEntity()).concat(".").concat(entityName);

                final File target = new File(pojoAODir + File.separator + entityName + "AO.java");
                if (target.exists()) {
                    log.info("应用对象[" + appObjectPackage + '.' + entityName + "AO" + "]已经存在，不重复生成");
                    continue;
                }

                Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), "UTF-8"));
                Map<String, String> dataModel = new HashMap<String, String>();

                Date date = new Date();
                dataModel.put("appObjectPackage", appObjectPackage);
                dataModel.put("entityName", entityName);
                dataModel.put("entityPackage", entityPackage);
                dataModel.put("tableName", tableName);
                dataModel.put("tableComment", tableComment);


                dataModel.put("date", String.format(Locale.US, "%1$tb %2$td, %3$tY", date, date, date));
                template.process(dataModel, out);
                out.flush();
                out.close();
                log.info("生成应用对象[" + appObjectPackage + '.' + entityName + "AO" + "]");
            }
        }

    }

}
