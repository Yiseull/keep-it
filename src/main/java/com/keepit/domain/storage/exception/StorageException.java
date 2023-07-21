package com.keepit.domain.storage.exception;

import com.keepit.global.error.exception.BaseException;
import com.keepit.global.error.exception.ErrorCode;

public class StorageException extends BaseException {
    public StorageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
