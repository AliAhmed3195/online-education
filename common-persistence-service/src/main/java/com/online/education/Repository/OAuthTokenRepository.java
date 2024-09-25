package com.online.education.Repository;

import com.online.education.entity.OAuthAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OAuthTokenRepository extends JpaRepository<OAuthAccessToken,Long> {

    @Transactional
    @Modifying
    @Query("update OAuthAccessToken set isActive = false where username=:username")
    void inActiveOAuthToken(@Param("username") String username);

    OAuthAccessToken findTopByUsernameAndIsActiveTrueOrderByCreatedOnDesc(String username);

}
