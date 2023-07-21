package com.keepit.domain.storage.application;

import com.keepit.domain.storage.domain.Storage;
import com.keepit.domain.storage.dto.request.StorageRequest;
import com.keepit.domain.storage.dto.response.StorageResponse;
import com.keepit.domain.storage.exception.StorageException;
import com.keepit.domain.storage.infrastructure.StorageRepository;
import com.keepit.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<StorageResponse> getStorages() {
        List<Storage> storages = storageRepository.findAll();
        return StorageResponse.toResponses(storages);
    }

    public StorageResponse getStorage(long storageId) {
        Storage storage = storageRepository.findById(storageId)
                .orElseThrow(() -> new StorageException(ErrorCode.STORAGE_NOT_FOUND));
        return new StorageResponse(storage);
    }
}
