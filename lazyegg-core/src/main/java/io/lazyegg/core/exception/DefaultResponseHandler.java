package io.lazyegg.core.exception;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * ResponseHandler
 *
 * @author Frank Zhang
 * @date 2020-11-10 3:18 PM
 */
@Slf4j
public class DefaultResponseHandler implements ResponseHandlerI {

    @Override
    public ResponseEntity<Object> handle(Class returnType, String errCode, String errMsg) {
        if (isColaResponse(returnType)) {
            return handleColaResponse(returnType, errCode, errMsg);
        }
        return null;
    }

    @Override
    public ResponseEntity<Object> handle(Class returnType, BaseException e) {
        return handle(returnType, e.getErrCode(), e.getMessage());
    }


    private static ResponseEntity<Object> handleColaResponse(Class returnType, String errCode, String errMsg) {
        try {
            Response response = (Response) returnType.newInstance();
            response.setSuccess(false);
            response.setErrCode(errCode);
            response.setErrMessage(errMsg);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    private static boolean isColaResponse(Class returnType) {
        return returnType == Response.class || returnType.getGenericSuperclass() == Response.class;
    }
}
