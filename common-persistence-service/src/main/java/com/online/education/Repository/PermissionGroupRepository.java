package com.online.education.Repository;

import com.online.education.entity.PermissionGroup;

import com.online.education.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long>, JpaSpecificationExecutor<PermissionGroup>  {
}
