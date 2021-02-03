package com.lzj.autotestpc.generation;

import com.lzj.autotestpc.bean.ScriptStepBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 脚本代码生成器
 */
public class ScriptGenerationTool {


    //项目中代码根路径
    private static final String BASE_PACKAGE = "com";
    //项目根路径
    public static String rootSrcPath = ScriptGenerationTool.class.getResource("/").getPath();
    //.replace("/out/production/jdk-source/","/src/");
    //模板路径
    final String templateDir = rootSrcPath + "/com/lzj/autotestpc/generation/template/";

    public static void main(String age[]){
        System.out.println(rootSrcPath);
    }

    public  void generate(List<ScriptStepBean>  scriptLists) throws IOException {
        //freemark配置
        Configuration configuration=new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        System.out.println(rootSrcPath);
        //设置freemarker模板路径
        configuration.setDirectoryForTemplateLoading(new File(templateDir));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        //获取模板对象
        Template template = configuration.getTemplate("TestTemplate.ftl", "UTF-8");
        // 定义模板数据
//        Package sourcePackage=sourceGalss.getPackage();
        Map<String, Object> data = new HashMap<>();

        data.put("scriptList",scriptLists);

    }
}
