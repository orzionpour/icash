package com.icash.purchase.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.icash.purchase.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("""
    SELECT u
    FROM User u
    JOIN Purchase p ON u.id = p.userId
    GROUP BY u.id
    HAVING COUNT(p) >= :minPurchases
    """)
    List<User> findUsersWithAtLeastNPurchases(@Param("minPurchases") int minPurchases);
}
