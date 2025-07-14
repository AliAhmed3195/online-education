package com.online.education.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroupDto {
    private List<PermissionParentGroup> permissionGroupDetails;

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PermissionParentGroup {
        long id;
        String name;
        Integer orderId;
        private List<PermissionGroup> permissionGroups;

        public PermissionParentGroup(long id, String name,Integer orderId,List<PermissionGroup> permissionGroups) {
            this.id = id;
            this.name = name;
            this.orderId = orderId;
            this.permissionGroups = permissionGroups;
        }

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PermissionGroup {
        private long id;
        private String name;
    }

}
