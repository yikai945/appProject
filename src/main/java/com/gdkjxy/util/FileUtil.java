package com.gdkjxy.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * @Description 文件处理类
 * @Author wuyikai
 * @Date 2020/11/29
 * @Version 1.0
 */
public class FileUtil {

    /**
     * 上传文件
     * @param originFile 源文件
     * @param path 保存路径
     * @param newName 保存名
     */
    public static void saveFile(MultipartFile originFile, String path, String newName){
        if(originFile==null){
            throw new RuntimeException("fileUtil:源文件不存在!!");
        }
        if(path==null||"".equals(path)){
            throw new RuntimeException("fileUtil:路径为空!!");
        }
        if(newName==null||"".equals(newName)){
            throw new RuntimeException("fileUtil:文件名为空!!");
        }
        //获取保存文件
        File file = new File(path);
        //是否有该目录,没有则创建
        if (!file.exists()){
            System.out.println("fileUtil:创建文件..........");
            file.mkdirs();
        }
        //创建新文件(保存文件)
        File saveFile = new File(path+File.separator+newName);
        if(saveFile.exists()){
            throw new RuntimeException("fileUtil:文件已存在");
        }
        try {
            saveFile.createNewFile();
            //将文件保存至指定路径
            originFile.transferTo(saveFile);
        } catch (IOException e) {
            System.out.println("fileUtil:文件存储失败");
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param path 文件路径
     * @param fileName 文件名
     * @return
     */
    public static boolean delFile(String path,String fileName){
        if(path==null||"".equals(path)){
            throw new RuntimeException("fileUtil:文件路径为空!!");
        }
        if(fileName==null||"".equals(fileName)){
            throw new RuntimeException("fileUtil:文件名为空!!");
        }
        File file = new File(path+File.separator+fileName);
        if(!file.exists()){
            throw new RuntimeException("fileUtil:该文件不存在!!");
        }
        return file.delete();
    }

    /**
     * 删除文件
     * @param filePath 文件全限定路径
     * @return
     */
    public static boolean delFile(String filePath){
        if(filePath==null||"".equals(filePath)){
            throw new RuntimeException("fileUtil:文件路径为空!!");
        }
        File file = new File(filePath);
        if(!file.exists()){
            throw new RuntimeException("fileUtil:该文件不存在!!");
        }
        return file.delete();
    }

    /**
     * 修改文件名
     * @param oldPathName 源文件全限定文件名
     * @param newPathName 新文件全限定文件名
     * @return
     */
    public static boolean renameFile(String oldPathName,String newPathName){
        if(oldPathName==null||"".equals(oldPathName)){
            throw new RuntimeException("fileUtil:源文件路径为空!!");
        }
        if(newPathName==null||"".equals(newPathName)){
            throw new RuntimeException("fileUtil:新文件名为空!!");
        }
        File oldFile = new File(oldPathName);
        File newFile = new File(newPathName);
        if(oldFile.renameTo(newFile)){
            return true;
        }else {
            return false;
        }
    }

}
