package cn.llman.bean;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author
 * @date 2018/12/28
 */
public class MyDeferredResult {

    private DeferredResult<Object> deferredResult;
    private Long flag;

    public MyDeferredResult(DeferredResult deferredResult, Long flag) {
        this.deferredResult = deferredResult;
        this.flag = flag;
    }

    public DeferredResult getDeferredResult() {
        return deferredResult;
    }

    public void setDeferredResult(DeferredResult deferredResult) {
        this.deferredResult = deferredResult;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

}
