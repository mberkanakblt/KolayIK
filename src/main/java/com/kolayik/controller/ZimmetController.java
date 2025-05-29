package com.kolayik.controller;

import com.kolayik.dto.request.*;
import com.kolayik.dto.response.ZimmetDto;
import com.kolayik.service.ZimmetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/zimmet")
@RequiredArgsConstructor
public class ZimmetController {
    private final ZimmetService service;

    @GetMapping("/all")
    public ResponseEntity<List<ZimmetDto>> getAll() {
        List<ZimmetDto> list = service.getAllAssignmentsForCurrentUser();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body(list);
    }

    @PostMapping("/")
    public ResponseEntity<ZimmetDto> create(@RequestBody ZimmetCreateRequestDto req) {
        return ResponseEntity.ok(service.createZimmet(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZimmetDto> update(@PathVariable Long id,
                                            @RequestBody ZimmetUpdateRequestDto req) {
        return ResponseEntity.ok(service.updateZimmet(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteZimmet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<ZimmetDto> confirm(@RequestBody ZimmetConfirmRequestDto req) {
        return ResponseEntity.ok(service.confirmAssignment(req));
    }

    @PostMapping("/reject")
    public ResponseEntity<ZimmetDto> reject(@RequestBody ZimmetConfirmRequestDto req) {
        return ResponseEntity.ok(service.rejectAssignment(req));
    }
}
