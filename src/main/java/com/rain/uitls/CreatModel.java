package com.rain.uitls;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Rain
 * 创建实体 并写入
 */
public   class CreatModel {

    static Logger logger = LoggerFactory.getLogger(CreatModel.class);




    public static void Creat(String name, String str,boolean bl){
        BufferedWriter bufferedWriter = null;
        try {

            createJava(name);
            File file = new File("src/main/java/com/rain/model/"+name+".java");
             System.out.println(file.toString());
            if(!file.exists()){
                File path = new File("src/main/java/com/rain/model");
                if(path.exists()){
                    /*if(bl){
                        delAllFile("src/main/java/com/rain/model");
                    }*/
                }else {
                    path.mkdirs();
                }
                boolean b = file.createNewFile();

                if(!b){
                    logger.error("创建实体{}失败",name);
                }
                if(!file.exists()){
                    logger.error("创建实体{} 但失败了",name);
                }
            }
            try {
                File resultModel = new File("src/main/java/com/rain/model/"+name+".java");
                bufferedWriter =  new BufferedWriter(new FileWriter(resultModel));
                bufferedWriter.write(str);

                bufferedWriter.flush();
                bufferedWriter.close();
                logger.info("Create Model {} Successful",name);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Dont find  Model {} ",name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static  void createJava(String name){
        try {
            System.out.println(name);
            TypeSpec helloWorld = TypeSpec.classBuilder(name)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .build();

            JavaFile javaFile = JavaFile.builder("com.rain.model", helloWorld)
                    .build();

            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
