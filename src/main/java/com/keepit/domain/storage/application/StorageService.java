package com.keepit.domain.storage.application;

import com.keepit.domain.product.domain.Product;
import com.keepit.domain.product.exception.ProductException;
import com.keepit.domain.product.infrastructure.ProductRepository;
import com.keepit.domain.storage.domain.Storage;
import com.keepit.domain.storage.dto.request.ProductIdRequest;
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
    private final ProductRepository productRepository;

    @Transactional
    public StorageResponse createStorage(StorageRequest request) {
        Storage storage = Storage.builder()
                .name(request.name())
                .build();
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

    @Transactional
    public void updateStorage(long storageId, StorageRequest request) {
        Storage storage = storageRepository.findById(storageId)
                .orElseThrow(() -> new StorageException(ErrorCode.STORAGE_NOT_FOUND));
        storage.updateName(request.name());
    }

    @Transactional
    public void deleteStorage(long storageId) {
        if (!storageRepository.existsById(storageId)) {
            throw new StorageException(ErrorCode.STORAGE_NOT_FOUND);
        }
        storageRepository.deleteById(storageId);
    }

    @Transactional
    public void addProducts(long storageId, List<ProductIdRequest> requests) {
        Storage storage = storageRepository.findById(storageId)
                .orElseThrow(() -> new StorageException(ErrorCode.STORAGE_NOT_FOUND));

        List<Product> products = getProducts(requests);
        products.forEach(storage::addProduct);
    }

    private List<Product> getProducts(List<ProductIdRequest> requests) {
        List<Long> productIds = requests.stream()
                .map(ProductIdRequest::productId)
                .toList();

        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new ProductException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return products;
    }
}
