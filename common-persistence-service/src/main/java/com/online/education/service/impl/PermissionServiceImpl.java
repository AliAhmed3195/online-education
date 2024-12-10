package com.online.education.service.impl;

import com.online.education.Repository.PermissionRepository;
import com.online.education.projection.PermissionUriResponseView;
import com.online.education.response.PermissionUriResponse;
import com.online.education.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<PermissionUriResponseView> getPermissionUriList(long roleId) {
        return permissionRepository.findByRoleIdAndIsActiveTrue(roleId);
    }
}
