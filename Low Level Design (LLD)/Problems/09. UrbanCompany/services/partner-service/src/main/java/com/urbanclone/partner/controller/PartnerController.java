package com.urbanclone.partner.controller;

import com.urbanclone.partner.dto.PartnerDto;
import com.urbanclone.partner.dto.PartnerRegistrationRequest;
import com.urbanclone.partner.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partners")
@RequiredArgsConstructor
public class PartnerController {
    
    private final PartnerService partnerService;
    
    @PostMapping
    public ResponseEntity<PartnerDto> registerPartner(@Valid @RequestBody PartnerRegistrationRequest request) {
        PartnerDto partner = partnerService.registerPartner(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(partner);
    }
    
    @GetMapping("/{partnerId}")
    public ResponseEntity<PartnerDto> getPartner(@PathVariable Long partnerId) {
        PartnerDto partner = partnerService.getPartner(partnerId);
        return ResponseEntity.ok(partner);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<PartnerDto>> getAvailablePartners(
            @RequestParam Long serviceId,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        List<PartnerDto> partners = partnerService.getAvailablePartners(serviceId, latitude, longitude);
        return ResponseEntity.ok(partners);
    }
}
