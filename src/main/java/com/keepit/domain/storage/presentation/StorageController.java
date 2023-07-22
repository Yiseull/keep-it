package com.keepit.domain.storage.presentation;

import com.keepit.domain.storage.application.StorageService;
import com.keepit.domain.storage.dto.request.ProductIdRequest;
import com.keepit.domain.storage.dto.request.StorageRequest;
import com.keepit.domain.storage.dto.response.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/storages")
public class StorageController {

    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<StorageResponse> createStorage(@RequestBody StorageRequest request) {
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
        StorageResponse response = storageService.getStorage(storageId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{storageId}")
    public ResponseEntity<Void> updateStorage(@PathVariable long storageId,
                                              @RequestBody StorageRequest request) {
        storageService.updateStorage(storageId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{storageId}")
    public ResponseEntity<Void> deleteStorage(@PathVariable long storageId) {
        storageService.deleteStorage(storageId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{storageId}")
    public ResponseEntity<Void> addProducts(@PathVariable long storageId,
                                            @RequestBody List<ProductIdRequest> requests) {
        storageService.addProducts(storageId, requests);
        return ResponseEntity.noContent().build();
    }
}
