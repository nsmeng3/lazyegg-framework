package io.lazyegg.boot.usermanagement.web.open;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.boot.usermanagement.api.CustomerServiceI;
import io.lazyegg.boot.usermanagement.dto.*;
import io.lazyegg.boot.usermanagement.dto.data.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * UserController
 *
 * @author lazyegg  nsmeng3@163.com
 */

@RestController
@RequestMapping("/api/open/usermanagement")
public class CustomerOpenApiAdaptor {
    private static final Logger log = LoggerFactory.getLogger(CustomerOpenApiAdaptor.class);

    @Resource
    private CustomerServiceI customerService;

    @PostMapping("/customers")
    public ResponseEntity<Response> addCustomer(@RequestBody CustomerAddCmd cmd) {
        return new ResponseEntity<>(customerService.addCustomer(cmd), HttpStatus.CREATED);
    }

    /**
     * 删除customer
     *
     * @param id customerId
     * @return
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Response> removeCustomer(@PathVariable(name = "id") String id) {
        CustomerDelCmd customerDelCmd = new CustomerDelCmd(id);
        customerService.removeCustomer(customerDelCmd);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 更新customer
     *
     * @param id                customerId
     * @param customerUpdateCmd 更新实体
     * @return
     */
    @PutMapping("/customers/{id}")
    public ResponseEntity<Response> updateCustomer(@PathVariable(name = "id") String id, @RequestBody CustomerUpdateCmd customerUpdateCmd) {
        return new ResponseEntity<>(customerService.updateCustomer(customerUpdateCmd), HttpStatus.OK);
    }

    /**
     * 查询customer（单个结果）
     *
     * @param id customerId
     * @return
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<SingleResponse<CustomerDTO>> getCustomer(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(customerService.getCustomer(new CustomerGetQry(id)), HttpStatus.OK);
    }

    /**
     * 查询customer（多个结果）
     *
     * @return
     */
    @GetMapping("/customers")
    public ResponseEntity<MultiResponse<CustomerDTO>> listCustomer() {
        return new ResponseEntity<>(customerService.listCustomer(new CustomerListQry()), HttpStatus.OK);
    }


    /**
     * 分页查询customer
     *
     * @return
     */
    @GetMapping(value = "/customers", params = {"page", "size"})
    public ResponseEntity<MultiResponse<CustomerDTO>> pageCustomer(@RequestParam int page, @RequestParam int size) {
        CustomerPageQry pageQry = new CustomerPageQry();
        pageQry.setPageIndex(page);
        pageQry.setPageSize(size);
        return new ResponseEntity<>(customerService.pageCustomer(pageQry), HttpStatus.OK);
    }

    /**
     * 统计customer
     *
     * @return
     */
    @GetMapping(value = "/customers/actions/count")
    public ResponseEntity<SingleResponse<CustomerDTO>> countCustomer() {
        return new ResponseEntity<>(customerService.countCustomer(new CustomerCountQry()), HttpStatus.OK);
    }


}

