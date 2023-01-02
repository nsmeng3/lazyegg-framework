package io.lazyegg.boot.plugins.generator.infrastructure.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LeggJavaCodeUtil
 *
 * @author DifferentW  nsmeng3@163.com
 */

public abstract class LeggJavaCodeUtil {
    private static final Logger log = LoggerFactory.getLogger(LeggJavaCodeUtil.class);


    /**
     * 解析类名
     *
     * @return
     */
    public static String parseClassName(String codeTxt) {
        String pattern = "(class|enum|interface|@interface)\\s+[\\w$]+";
        return parse(pattern, codeTxt).replaceAll("(class|enum|interface|@interface)", "").replaceAll(" ", "");
    }

    /**
     * 解析包名
     *
     * @return
     */
    public static String parsePackageName(String codeTxt) {
        String pattern = "package\\s+[\\w$.]+;";
        return parse(pattern, codeTxt)
                .replaceAll("package", "")
                .replaceAll(";", "")
                .replaceAll(" ", "");
    }

    public static String parse(String pattern, String codeTxt) {
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(codeTxt);
        if (m.find()) {
            return m.group();
        }
        String format = String.format("\n%s", codeTxt);
        throw new RuntimeException("解析代码内容为找匹配内容" + format);
    }
}

