package com.keepit.global.storage.dto.response;

import com.keepit.global.storage.domain.Storage;
import lombok.Getter;

@Getter
public class StorageResponse {

    private final long id;
    private final String name;

    public StorageResponse(Storage storage) {
        this.id = storage.getId();
        this.name = storage.getName();
    }
}
