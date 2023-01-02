package io.lazyegg.boot.plugins.generator.app.excutor;

import io.lazyegg.boot.plugins.generator.client.JavaCodeGenCmd;
import io.lazyegg.boot.plugins.generator.client.JavaCodeGenContext;
import io.lazyegg.boot.plugins.generator.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

/**
 * JavaCodeResetCmdExe
 * 代码撤销命令
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Component
public class JavaCodeResetCmdExe {
    private static final Logger log = LoggerFactory.getLogger(JavaCodeResetCmdExe.class);

    public void execute(String entityName) {
        reset(entityName);
    }

    private void reset(String entityName) {

        String logFile = String.join(File.separator, "log", entityName + ".rl");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(logFile));
            List<File> o = (List<File>) objectInputStream.readObject();
            for (File file : o) {

                if (!file.exists()) {
                    log.warn("{}文件不存在", file);
                    continue;
                }
                try {
                    boolean delete = file.delete();
                } catch (Exception e) {
                    log.warn("{}删除异常", file.getAbsoluteFile());
                }
            }
            File rlFile = new File(logFile);
            if (rlFile.exists()) {
                rlFile.delete();
            }
            objectInputStream.close();
        } catch (Exception e) {
            log.warn("记录读取异常，撤销已生成代码操作无法进行", e);
        }
    }

}

