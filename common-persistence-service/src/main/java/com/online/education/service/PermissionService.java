package com.online.education.service;

import com.online.education.projection.PermissionUriResponseView;

import java.util.List;

public interface PermissionService {
    List<PermissionUriResponseView> getPermissionUriList(long roleId);
}
