package com.keepit.domain.storage.presentation;

import com.keepit.domain.storage.application.StorageService;
import com.keepit.domain.storage.dto.request.ProductIdRequest;
import com.keepit.domain.storage.dto.request.StorageRequest;
import com.keepit.domain.storage.dto.response.StorageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/storages")
public class StorageController {

    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<StorageResponse> createStorage(@RequestBody StorageRequest request) {
        log.info("request={}", request);
        StorageResponse response = storageService.createStorage(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StorageResponse>> getStorages() {
        List<StorageResponse> responses = storageService.getStorages();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{storageId}")
    public ResponseEntity<StorageResponse> getStorage(@PathVariable long storageId) {
        log.info("storageId={}", storageId);
        StorageResponse response = storageService.getStorage(storageId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{storageId}")
    public ResponseEntity<Void> updateStorage(@PathVariable long storageId,
                                              @RequestBody StorageRequest request) {
        log.info("storageId={}, request={}", storageId, request);
        storageService.updateStorage(storageId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{storageId}")
    public ResponseEntity<Void> deleteStorage(@PathVariable long storageId) {
        log.info("storageId={}", storageId);
        storageService.deleteStorage(storageId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{storageId}/products")
    public ResponseEntity<Void> addProducts(@PathVariable long storageId,
                                            @RequestBody List<ProductIdRequest> requests) {
        log.info("storageId={}, requests={}", storageId, requests);
        storageService.addProducts(storageId, requests);
        return ResponseEntity.noContent().build();
    }
}
