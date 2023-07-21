package com.keepit.domain.storage.application;

import com.keepit.domain.storage.infrastructure.StorageRepository;
import com.keepit.domain.storage.domain.Storage;
import com.keepit.domain.storage.dto.request.StorageRequest;
import com.keepit.domain.storage.dto.response.StorageResponse;
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
