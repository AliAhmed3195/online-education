package com.online.education.resource;

import com.online.education.entity.TradeFlowUser;
import com.online.education.exception.UserServiceException;
import com.online.education.filter.TradeFlowAuthentication;
import com.online.education.manager.MenuManager;
import com.online.education.manager.UserManager;
import com.online.education.request.*;
import com.online.education.response.GenericResponse;
import com.online.education.response.MenuDTO;
import com.online.education.service.PermissionGroupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class UserResource {

    private static final String USER_CREATED_SUCCESFULLY = "user.created.success";

    @Autowired
    private Environment environment;

    @Autowired
    private PermissionGroupService permissionGroupService;

//    @Autowired
//    private UserMana businessRoleService;

    @Autowired
    @Qualifier("userManagerUserServiceImpl")
    private UserManager userManager;

    @Autowired
    private MenuManager menuManager;

    @PostMapping("/password/change")
    public GenericResponse changePassword(HttpServletRequest request, @Valid @RequestBody ChangePasswordRequestDTO requestDTO) throws UserServiceException {
        TradeFlowAuthentication authentication = (TradeFlowAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return GenericResponse.createSuccessResponse(environment.getProperty("message.changed.user-password", "Successfully changed user password"),
                "result", userManager.changePassword(request, requestDTO, authentication.getUsername()));
    }
    @PostMapping("/user/create")
    public GenericResponse createUser(@RequestBody TradeFlowUserRequestDto user){
        return userManager.createUser( user );
    }

    @PostMapping("/users/list")
    public GenericResponse itemList( @RequestBody UserSearchRequest userSearchRequest ){
        return userManager.userList( userSearchRequest );
    }

    @PostMapping("/users/view-details")
    public GenericResponse getDetails(@RequestBody UserIdRequest request) {
        return userManager.findByUserId(request);
    }

    @PostMapping("/users/update")
    public GenericResponse updateDetails(@RequestBody TradeFlowUser request) {
        return userManager.updateUserDetails(request);
    }

    @PostMapping("/users/business-roles")
    public GenericResponse businessRoleList( @RequestBody BusinessRoleSearchRequest roleSearchRequest ){
        return userManager.listBusinessRole( roleSearchRequest );
    }

    @PostMapping("/users/menus")
    public GenericResponse menusForAllUser() {
        TradeFlowAuthentication tradeFlowAuthentication = (TradeFlowAuthentication) SecurityContextHolder.getContext().getAuthentication();
        MenuDTO menuDto = menuManager.fetchBusinessMenu(tradeFlowAuthentication.getUserRoleId());
        return  GenericResponse.createSuccessResponse(
                environment.getProperty("message.fetched.menu-list", "Fetched Menu List"),
                "menus",
                menuDto!=null ? menuDto.getMenus() : new ArrayList());
    }

//    @PostMapping("/permission-groups/list")
//    public GenericResponse listBusinessRolesWithPermissionGroups(@RequestBody RoleSearchRequest roleSearchRequest) {
//        return roleService.listBusinessRolesWithPermissionGroups(roleSearchRequest);
//    }

    @PostMapping("/business-role-permission-groups/list")
    public GenericResponse getAllPermissions() {
        return GenericResponse.createSuccessResponse("message",
                "permissions", Objects.isNull(permissionGroupService.getAllBusinessPermissions())? new ArrayList<>() : permissionGroupService.getAllBusinessPermissions().getPermissionGroupDetails());
    }

    @PostMapping("/business-role-permission-groups/create")
    public GenericResponse createBusinessRole(@RequestBody BusinessRoleRequestDto requestDTO) {
        return userManager.addBusinessRole( requestDTO );
    }

    @PostMapping("/business-role-permission-groups/update")
    public void updateBusinessRole(@RequestBody BusinessRoleRequestDto requestDTO) {
//        return businessRoleService.addBusinessRole(requestDTO);
    }

}
