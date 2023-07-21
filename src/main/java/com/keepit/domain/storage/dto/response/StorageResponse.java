package com.keepit.domain.storage.dto.response;

import com.keepit.domain.storage.domain.Storage;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StorageResponse {

    private final long id;
    private final String name;

    public StorageResponse(Storage storage) {
        this.id = storage.getId();
        this.name = storage.getName();
    }

    public static List<StorageResponse> toResponses(List<Storage> storages) {
        return storages.stream()
                .map(StorageResponse::new)
                .collect(Collectors.toList());
    }
}
