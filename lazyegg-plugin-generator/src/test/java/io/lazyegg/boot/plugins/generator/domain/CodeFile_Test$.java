package io.lazyegg.boot.plugins.generator.domain;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CodeFile_Test$ {

    @Test
    void className() {

        String a = "public abstract class CodeFile1_$ implements class aaa  Serializable {";
        String s = parseClassName(a);
        System.out.println(s);
    }

    private static String parseClassName(String a) {
        String pattern = "class\\s+[\\w$]+";
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(a);
        if (m.find()) {
            String group = m.group();
            return group.replaceAll("class", "").replaceAll(" ", "");
        }
        throw new RuntimeException("类名解析失败");
    }
}
