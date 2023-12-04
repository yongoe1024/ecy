package com.yongoe.ecy.exception;

import com.yongoe.ecy.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.yongoe.ecy")
public class GlobalException {

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public R sql(Throwable throwable) {
        log.error("错误:", throwable);
        return R.error("主键错误");
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public R valueException(Throwable throwable) {
        log.error("错误:", throwable);
        return R.error("数据格式有误");
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public R vdataLongException(Throwable throwable) {
        log.error("错误:", throwable);
        return R.error("数据完整性违规");
    }

    @ExceptionHandler(value = {BadSqlGrammarException.class})
    public R sqlException(Throwable throwable) {
        log.error("错误:", throwable);
        return R.error("SQL执行错误");
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("错误:", throwable);
        return R.error("未知错误，" + throwable.getMessage());
    }
}
