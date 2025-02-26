package com.example.NetUp.community.repository;

import com.example.NetUp.community.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query(value = "SELECT COUNT(*) FROM users u WHERE u.community_id = :communityId", nativeQuery = true)
    Long countUsersInCommunity(Long communityId);
}
