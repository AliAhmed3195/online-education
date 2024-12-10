package com.online.education.service.impl;

import com.online.education.Repository.PermissionGroupRepository;
import com.online.education.entity.PermissionGroup;
import com.online.education.entity.PermissionGroupParent;
import com.online.education.response.PermissionGroupDto;
import com.online.education.service.PermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermissionGroupServiceImpl implements PermissionGroupService  {

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;
    @Override
    public PermissionGroupDto getAllBusinessPermissions() {
        List<PermissionGroup> res = permissionGroupRepository.findAll();
        return convert(res);
    }

    @Override
    public Collection<PermissionGroup> getPermissionsByIds(Collection<Long> permissionGroups){
        return permissionGroupRepository.findAllById(permissionGroups);
    }

    private PermissionGroupDto convert( List<PermissionGroup> permissionGroups ){
        // Group PermissionGroups by their parent
        Map<PermissionGroupParent, List<PermissionGroup>> groupedByParent = permissionGroups.stream()
                .filter(pg -> pg.getParent() != null) // Ensure parent is not null
                .collect(Collectors.groupingBy(PermissionGroup::getParent));

        // Transform the grouped map into PermissionParentGroup list
        List<PermissionGroupDto.PermissionParentGroup> permissionParentGroups = groupedByParent.entrySet().stream()
                .map(entry -> {
                    PermissionGroupParent parent = entry.getKey();
                    List<PermissionGroupDto.PermissionGroup> childGroups = entry.getValue().stream()
                            .map(child -> new PermissionGroupDto.PermissionGroup(child.getId(), child.getName()))
                            .toList();

                    return new PermissionGroupDto.PermissionParentGroup(
                            parent.getId(),
                            parent.getName(),
                            parent.getOrderId(),
                            childGroups
                    );
                })
                .toList();

        // Wrap the result in PermissionGroupDto
        return new PermissionGroupDto(permissionParentGroups);
    }

}
