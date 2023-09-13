package io.lazyegg.sdk.utils;

import static com.aliyun.oss.internal.OSSConstants.LOGGER_PACKAGE_NAME;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.ServiceException;

public class LogUtils {

    private static final Log log = LogFactory.getLog("io.lazyegg.sdk");

    // Set logger level to INFO specially if reponse error code is 404 in order
    // to
    // prevent from dumping a flood of logs when trying access to none-existent
    // resources.
    private static List<String> errorCodeFilterList = new ArrayList<String>();
    static {
//        errorCodeFilterList.add(ErrorCode.NO_SUCH_BUCKET);
    }

    public static Log getLog() {
        return log;
    }

    public static <ExType> void logException(String messagePrefix, ExType ex) {
        logException(messagePrefix, ex, true);
    }

    public static <ExType> void logException(String messagePrefix, ExType ex, boolean logEnabled) {

        assert (ex instanceof Exception);

        String detailMessage = messagePrefix + ((Exception) ex).getMessage();
        if (ex instanceof ServiceException && errorCodeFilterList.contains(((ServiceException) ex).getErrorCode())) {
            if (logEnabled) {
                log.debug(detailMessage);
            }
        } else {
            if (logEnabled) {
                log.warn(detailMessage);
            }
        }
    }
}
