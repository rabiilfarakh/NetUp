package com.example.NetUp.community.web;

import com.example.NetUp.community.dtos.CommunityDTOReq;
import com.example.NetUp.community.dtos.CommunityDTORes;
import com.example.NetUp.community.service.CommunityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping
    public ResponseEntity<CommunityDTORes> createCommunity(@Valid @RequestBody CommunityDTOReq communityDTOReq) {
        CommunityDTORes createdCommunity = communityService.createCommunity(communityDTOReq);
        return new ResponseEntity<>(createdCommunity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityDTORes> getCommunityById(@PathVariable Long id) {
        CommunityDTORes community = communityService.getCommunityById(id);
        return ResponseEntity.ok(community);
    }

    @GetMapping
    public ResponseEntity<List<CommunityDTORes>> getAllCommunities() {
        List<CommunityDTORes> communities = communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommunityDTORes> patchCommunity(
            @PathVariable Long id,
            @RequestBody CommunityDTOReq communityDTOReq
    ) {
        CommunityDTORes updatedCommunity = communityService.updateCommunity(id, communityDTOReq);
        return ResponseEntity.ok(updatedCommunity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.noContent().build();
    }

}