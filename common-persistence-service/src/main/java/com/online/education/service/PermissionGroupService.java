package com.online.education.service;

import com.online.education.entity.PermissionGroup;
import com.online.education.response.PermissionGroupDto;

import java.util.Collection;

public interface PermissionGroupService {

    PermissionGroupDto getAllBusinessPermissions();

    Collection<PermissionGroup> getPermissionsByIds(Collection<Long> permissionGroups);


}
