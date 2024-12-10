package com.online.education.Repository;

import com.online.education.entity.TradeFlowUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeFlowUserRepository extends JpaRepository<TradeFlowUser, Long>, JpaSpecificationExecutor<TradeFlowUser> {

    Optional<TradeFlowUser> findByUsernameAndIsActiveTrue( String userName);
}
