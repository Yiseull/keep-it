package com.keepit.domain.storage.presentation;

import com.keepit.domain.storage.application.StorageService;
import com.keepit.domain.storage.dto.request.StorageRequest;
import com.keepit.domain.storage.dto.response.StorageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
