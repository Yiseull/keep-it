package com.keepit.domain.storage.presentation;

import com.keepit.domain.storage.application.StorageService;
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
}
