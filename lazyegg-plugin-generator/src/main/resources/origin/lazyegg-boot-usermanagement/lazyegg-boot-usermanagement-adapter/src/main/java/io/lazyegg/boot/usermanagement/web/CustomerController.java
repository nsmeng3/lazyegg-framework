package io.lazyegg.boot.customermanage.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import io.lazyegg.boot.customermanage.api.CustomerServiceI;
import io.lazyegg.boot.customermanage.dto.*;
import io.lazyegg.boot.customermanage.dto.data.CustomerDTO;
import io.lazyegg.core.page.PageLongResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/customermanage")
@RestController
@Slf4j
public class CustomerController {

    @Resource
    private CustomerServiceI customerService;

    /**
     * 新增customer
     *
     * @param cmd
     * @return
     */
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
        customerUpdateCmd.setId(id);
        Response customer = customerService.updateCustomer(customerUpdateCmd);
        return new ResponseEntity<>(customer, HttpStatus.OK);
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
    public ResponseEntity<MultiResponse<CustomerDTO>> listCustomer(@RequestParam(required = false) String id) {
        CustomerListQry.CustomerListQryBuilder builder = CustomerListQry.builder();
        builder.id(id);
        CustomerListQry qry = builder.build();
        return new ResponseEntity<>(customerService.listCustomer(qry), HttpStatus.OK);
    }


    /**
     * 分页查询customer
     *
     * @return
     */
    @GetMapping(value = "/customers/actions/page", params = {"page", "size"})
    public ResponseEntity<PageLongResponse<CustomerDTO>> pageCustomer(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String id) {
        CustomerPageQry.CustomerPageQryBuilder builder = CustomerPageQry.builder();
        builder.id(id);
        CustomerPageQry pageQry = builder.build();
        pageQry.setPageIndex(page);
        pageQry.setPageSize(size);
        return new ResponseEntity<>(customerService.pageCustomer(pageQry), HttpStatus.OK);
    }

}
