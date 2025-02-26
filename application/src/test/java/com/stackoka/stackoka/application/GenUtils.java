package com.stackoka.stackoka.application;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GenUtils {
    public static void main(String[] args) throws Exception {
        controller();
      // genFrontApi();
    }

    private static void controller() throws IOException, TemplateException {
        String out = "/Users/liuxin/Desktop/xilio-server/gen/";
        String templateName = "/Users/liuxin/Desktop/xilio-server/src/test/resources/template/";
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDirectoryForTemplateLoading(new File(templateName));
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate("/Controller.ftl");
        Map<String, Object> dataModel = new HashMap<>();
        String  moduleName="post";
        dataModel.put("moduleName", moduleName);

        dataModel.put("apiNames", Arrays.asList("list","add","detail"));
        template.process(dataModel, new BufferedWriter(new FileWriter(out + moduleName+"Controller.java")));
    }

    private static void genFrontApi() throws IOException, TemplateException {
        String out = "/Users/liuxin/Desktop/xilio-server/gen/";
        String templateName = "/Users/liuxin/Desktop/xilio-server/src/test/resources/template/";
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDirectoryForTemplateLoading(new File(templateName));
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate("/api.ftl");
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("moduleName", "post");


        dataModel.put("apiNames", Arrays.asList("postList","postAdd","postDetail"));
        template.process(dataModel, new BufferedWriter(new FileWriter(out + "api.ts")));
    }
}
