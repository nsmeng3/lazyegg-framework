package io.lazyegg.auth.core.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * OutputStreamResponseUtil
 *
 * @author DifferentW  nsmeng3@163.com
 */

public abstract class LeggResponsePrintUtil {
    private static final Logger log = LoggerFactory.getLogger(LeggResponsePrintUtil.class);

    public static void writeJson(HttpServletResponse response, Map<String, Object> result) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(500);
        ServletOutputStream outputStream = response.getOutputStream();
        if (result == null) {
            result = new HashMap<>();
            result.put("code", 500);
            result.put("message", "未知异常");
        }
        outputStream.write(JSONObject.toJSONString(result).getBytes(Charset.defaultCharset()));
        outputStream.flush();
        outputStream.close();
    }

    public static void writeJson(HttpServletResponse response, Map<String, Object> result, int code) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(code);
        ServletOutputStream outputStream = response.getOutputStream();
        if (result == null) {
            result = new HashMap<>();
            result.put("code", code);
            result.put("message", "未知异常");
        }
        outputStream.write(JSONObject.toJSONString(result).getBytes(Charset.defaultCharset()));
        outputStream.flush();
        outputStream.close();
    }
}

