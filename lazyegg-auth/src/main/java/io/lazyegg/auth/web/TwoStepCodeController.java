package io.lazyegg.auth.web;

import com.alibaba.cola.dto.SingleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @PostMapping("/2step-code")
    public SingleResponse twoStepCode() {
        HashMap data = new HashMap();
        data.put("stepCode", "1");
        return SingleResponse.of(data);
    }

}

