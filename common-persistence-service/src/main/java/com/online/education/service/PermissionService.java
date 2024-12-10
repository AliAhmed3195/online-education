package com.online.education.service;

import com.online.education.projection.PermissionUriResponseView;
import com.online.education.response.PermissionUriResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionUriResponseView> getPermissionUriList(long roleId);
}
