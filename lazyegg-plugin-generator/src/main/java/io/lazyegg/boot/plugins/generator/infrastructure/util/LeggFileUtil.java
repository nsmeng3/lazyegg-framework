package io.lazyegg.boot.plugins.generator.infrastructure.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * FileUtil
 *
 * @author DifferentW  nsmeng3@163.com
 */

public class LeggFileUtil {
    private static final Logger log = LoggerFactory.getLogger(LeggFileUtil.class);

    /**
     * 获取目录及目录下文件和目录
     *
     * @param dir
     * @param isIncludeDir
     * @return
     */
    public static void listFile(File dir, List<File> fileList, boolean isIncludeDir) {
        for (File fileItem : dir.listFiles()) {
            if (fileItem.isDirectory()) {
                if (isIncludeDir) {
                    fileList.add(fileItem);
                }
                listFile(fileItem, fileList, isIncludeDir);
            } else {
                fileList.add(fileItem);
            }
        }
    }

    /**
     * 替换文件内容
     *
     * @param replaceMap
     * @param file
     * @return
     */
    public static StringWriter replaceFileContent(Map<String, String> replaceMap, File file) {
        StringWriter sw = new StringWriter();


        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> strings = new ArrayList<String>();
            String s;//读取的每一行数据
            while ((s = br.readLine()) != null) {
                for (Map.Entry<String, String> replaceEntry : replaceMap.entrySet()) {
                    String oldString = replaceEntry.getKey();
                    String newString = replaceEntry.getValue();
                    if (s.startsWith("package ") || s.startsWith("import ")) {
                        s = s.replace("customer", "${entityNameLowercase}");
                    }
                    if (s.contains(oldString)) {
                        s = s.replace(oldString, newString);
                    }
                }
                strings.add(s);//将数据存入集合
            }
            BufferedWriter bw = new BufferedWriter(sw);
            for (String string : strings) {
                bw.write(string);//一行一行写入数据
                bw.newLine();//换行
            }
            sw.close();
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException("文件内容替换异常", e);
        }
        return sw;
    }

    /**
     * 将内容写入文件
     *
     * @param stringWriter
     * @param filePath
     * @param fileName
     */
    public static void writeFile(StringWriter stringWriter, String filePath, String fileName) {

        if (StringUtils.isAnyBlank(filePath, fileName) || stringWriter == null) {
            throw new RuntimeException("参数不能为空: [stringWriter][filePath][fileName]");
        }
        File dir = new File(filePath);
        dir.mkdirs();
        String fileFullName = String.join(File.separator, filePath, fileName);
        try {
            FileWriter fileWriter = new FileWriter(fileFullName);
            fileWriter.write(stringWriter.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getResourceDir(String path) {
        File resourceDir;
        try {
            String join = String.join(File.separator, "src", "main", "resources", path);
            resourceDir = ResourceUtils.getFile(join);
            resourceDir.mkdirs();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("目录未找到", e);
        }
        return resourceDir;
    }

    public static File getClasspathDir(String path) {
        File classpathDir = null;
        try {
            String join = String.join(File.separator, path);
            classpathDir = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + join);
            classpathDir.mkdirs();
        } catch (FileNotFoundException e) {
            return readJarFile(path);
        }
        return classpathDir;

    }

    public static void asFile(InputStream is, File file) {
        Assert.notNull(file, "文件不能为空");
        String parent = file.getParent();
        new File(parent).mkdirs();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = is.read(b)) > 0) {
                fos.write(b, 0, length);
            }
            is.close();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static File readJarFile(String path) {
        //通过当前线程得到类加载器从而得到URL的枚举
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        String protocol = url.getProtocol();//大概是jar
        if ("jar".equalsIgnoreCase(protocol)) {
            try {
                //转换为JarURLConnection
                JarURLConnection connection = (JarURLConnection) url.openConnection();
                if (connection != null) {
                    JarFile jarFile = connection.getJarFile();
                    if (jarFile != null) {
                        //得到该jar文件下面的类实体
                        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                        while (jarEntryEnumeration.hasMoreElements()) {
                            JarEntry entry = jarEntryEnumeration.nextElement();
                            String jarEntryName = entry.getName();
                            //这里我们需要过滤不是class文件和不在basePack包名下的类
                            if (jarEntryName.startsWith(path) && !entry.isDirectory()) {
                                // 开始读取文件内容
                                InputStream is = LeggFileUtil.class.getClassLoader().getResourceAsStream(jarEntryName);
                                File template = getResourceDir("/");
                                File file1 = new File(String.join(File.separator, template.getAbsolutePath(), jarEntryName));
                                asFile(is, file1);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return getResourceDir(path);
    }

    public static void delFile(File index) {
        if (index.isDirectory()) {
            File[] files = index.listFiles();
            for (File in : files) {
                delFile(in);
            }
        }
        index.delete();
        //出现几次删除成功代表有几个文件和文本文件
        System.out.println("删除成功");
    }

}
