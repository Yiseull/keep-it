package com.keepit.global.storage.application;

import com.keepit.global.storage.domain.Storage;
import com.keepit.global.storage.dto.request.StorageRequest;
import com.keepit.global.storage.dto.response.StorageResponse;
import com.keepit.global.storage.infrastructure.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StorageService {

    private final StorageRepository storageRepository;

    @Transactional
    public StorageResponse createStorage(StorageRequest request) {
        Storage storage = new Storage(request.name());
        Storage savedStorage = storageRepository.save(storage);
        return new StorageResponse(savedStorage);
    }
}
