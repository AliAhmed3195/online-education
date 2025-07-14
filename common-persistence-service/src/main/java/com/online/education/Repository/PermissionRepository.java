package com.online.education.Repository;

import com.online.education.entity.Permission;
import com.online.education.projection.PermissionUriResponseView;

import com.online.education.response.PermissionUriResponse;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer> {

    @Query("select distinct p.uri as uri from Permission p join p.permissionGroups pg " +
            "join pg.roles r where r.id=:id and p.isActive=true")
    List<PermissionUriResponseView> findByRoleIdAndIsActiveTrue(long id);
}
