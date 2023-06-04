package io.lazyegg.core.page;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * PageLongResponse
 *
 * @author DifferentW  nsmeng3@163.com
 */

public class PageLongResponse<T> extends Response {
    private static final long serialVersionUID = 1L;
    private long total = 0;
    private long size = 1;
    private long current = 1;
    private Collection<T> data;

    public PageLongResponse() {
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return this.size < 1 ? 1 : this.size;
    }

    public void setSize(long size) {
        if (size < 1) {
            this.size = 1;
        } else {
            this.size = size;
        }

    }

    public long getCurrent() {
        return this.current < 1 ? 1 : this.current;
    }

    public void setCurrent(long current) {
        if (current < 1) {
            this.current = 1;
        } else {
            this.current = current;
        }

    }

    public List<T> getData() {
        if (null == this.data) {
            return Collections.emptyList();
        } else {
            return this.data instanceof List ? (List)this.data : new ArrayList(this.data);
        }
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public long getTotalPages() {
        return this.total % this.size == 0 ? this.total / this.size : this.total / this.size + 1;
    }

    public boolean isEmpty() {
        return this.data == null || this.data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public static PageLongResponse buildSuccess() {
        PageLongResponse response = new PageLongResponse();
        response.setSuccess(true);
        return response;
    }

    public static PageLongResponse buildFailure(String errCode, String errMessage) {
        PageLongResponse response = new PageLongResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> PageLongResponse<T> of(long pageSize, long pageIndex) {
        PageLongResponse<T> response = new PageLongResponse();
        response.setSuccess(true);
        response.setData(Collections.emptyList());
        response.setTotal(0);
        response.setSize(pageSize);
        response.setCurrent(pageIndex);
        return response;
    }

    public static <T> PageLongResponse<T> of(Collection<T> data, long totalCount, long pageSize, long pageIndex) {
        PageLongResponse<T> response = new PageLongResponse();
        response.setSuccess(true);
        response.setData(data);
        response.setTotal(totalCount);
        response.setSize(pageSize);
        response.setCurrent(pageIndex);
        return response;
    }

    public static <T> PageLongResponse<T> of(PageDTO pageDTO) {
        PageLongResponse<T> response = new PageLongResponse();
        response.setSuccess(true);
        response.setData(pageDTO.getRecords());
        response.setTotal(pageDTO.getTotal());
        response.setSize(pageDTO.getSize());
        response.setCurrent(pageDTO.getCurrent());
        return response;
    }
}
