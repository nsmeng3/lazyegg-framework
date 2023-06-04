package io.lazyegg.auth.web;

import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.core.annotation.LeggAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * TwoStepCodeController
 * 二次认证码
 *
 * @author DifferentW  nsmeng3@163.com
 */

@RestController
@RequestMapping("/auth")
public class TwoStepCodeController {
    private static final Logger log = LoggerFactory.getLogger(TwoStepCodeController.class);

    /**
     * TODO 作用未知
     *
     * @return
     */
    @LeggAnno
    @PostMapping("/2step-code")
    public SingleResponse twoStepCode() {
        if (true) {
            throw new RuntimeException("sdfsdff");
        }
        HashMap data = new HashMap();
        data.put("stepCode", "1");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Object credentials = authentication.getCredentials();
        data.put("principal", principal);
        data.put("credentials", credentials);
        return SingleResponse.of(data);
    }

}
