package com.keepit.product.exception;

import com.keepit.global.error.exception.BaseException;
import com.keepit.global.error.exception.ErrorCode;

public class ProductException extends BaseException {
    public ProductException(ErrorCode errorCode) {
        super(errorCode);
    }
}
