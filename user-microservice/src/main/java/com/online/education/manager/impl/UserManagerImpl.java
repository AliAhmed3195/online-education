package com.online.education.manager.impl;

import com.online.education.Repository.TradeFlowUserRepository;
import com.online.education.entity.TradeFlowUser;
import com.online.education.exception.UserServiceException;
import com.online.education.manager.UserManager;
import com.online.education.request.ChangePasswordRequestDTO;
import com.online.education.response.ChangePasswordResponseDTO;
import com.online.education.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Component("userManagerUserServiceImpl")
public class UserManagerImpl implements UserManager {

    @Autowired
    private TradeFlowUserRepository tradeFlowUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${general.invalid.user}")
    private String invalidUserError;


    @Override
    public ChangePasswordResponseDTO changePassword(HttpServletRequest request, @Valid ChangePasswordRequestDTO requestDTO, String username) throws UserServiceException {
        Optional<TradeFlowUser> tradeFlowUser = tradeFlowUserRepository.findByUsernameAndIsActiveTrue( username );
        if ( tradeFlowUser.isPresent() ) {
            TradeFlowUser tradeFlowUserObject = tradeFlowUser.get();
            tradeFlowUserObject.setPassword( passwordEncoder.encode( requestDTO.getNewPassword() ) );
            tradeFlowUserObject.setLastPasswordResetDate( new Date() );
            tradeFlowUserRepository.save( tradeFlowUserObject );
            return new ChangePasswordResponseDTO(username, requestDTO.getNewPassword());

        } else {
            log.error("changePassword user not found: {}", username);
            throw new IllegalArgumentException(invalidUserError);
        }
    }
    @Override
    public GenericResponse createUser(TradeFlowUserRequestDto user ){
        TradeFlowUser tradeFlowUser = createTradeFlowUserObj(user);
        tradeFlowUserRepository.save(  tradeFlowUser );
        return GenericResponse.createSuccessResponse(environment.getProperty(USER_CREATED_SUCCESSFULLY));
    }

    @Override
    public GenericResponse userList( UserSearchRequest userSearchRequest ){
        Specification<TradeFlowUser> specification = commonSearchTradeFlowUserSpecification(userSearchRequest);

        Page<TradeFlowUser> page = tradeFlowUserRepository.findAll(specification, PageRequest.of(userSearchRequest.getPageNumber()<=0 ? 0 : userSearchRequest.getPageNumber()-1,
                userSearchRequest.getPageSize()<=0 ? 10 : userSearchRequest.getPageSize(),
                Sort.Direction.DESC, "id"));
        return GenericResponse.createSuccessResponse(
                environment.getProperty(USER_SUCCESSFULLY_FETCH), "users",
                new PaginatedResponseDTO(page.getContent(), page.getTotalElements()));
    }

    @Override
    public GenericResponse findByUserId(UserIdRequest userIdRequest){
        Optional<TradeFlowUser> user = tradeFlowUserRepository.findById( userIdRequest.getUserId() );
        if( user.isPresent() ){
            return GenericResponse.createSuccessResponse(
                    environment.getProperty(USER_SUCCESSFULLY_FETCH),USER,user);
        } else {
            return GenericResponse.createSuccessResponse(environment.getProperty(USER_SUCCESSFULLY_FETCH));
        }
    }

    @Override
    public GenericResponse updateUserDetails( TradeFlowUser user){
        tradeFlowUserRepository.save(user);
        return GenericResponse.createSuccessResponse(environment.getProperty(USER_CREATED_SUCCESSFULLY));
    }


    @Override
    public GenericResponse listBusinessRole(BusinessRoleSearchRequest roleSearchRequest) {

        Specification<Role> specification = commonSearchRoleSpecification( roleSearchRequest );
                Page<Role> roles = roleRepository.findAll( specification,
                PageRequest.of(roleSearchRequest.getPageNumber() <= 0 ? 0 : roleSearchRequest.getPageNumber() - 1,
                        roleSearchRequest.getPageSize() <= 0 ? 10 : roleSearchRequest.getPageSize(),
                        Sort.Direction.DESC, CREATED_ON_ATTR));

        return GenericResponse.createSuccessResponse(
                environment.getProperty(ADD_BUSINESS_ROLE_SUCCESS), "businessRoles",
                new PaginatedResponseDTO(roles.getContent(), roles.getTotalElements()));
    }


    private static Specification<Role> commonSearchRoleSpecification(BusinessRoleSearchRequest searchDTO) {
        Specification<Role> specification =
                SpecificationUtility.equalsValue("isActive", true);
        if( searchDTO.getRoleId() != null ){
            specification = specification.and(SpecificationUtility.equalsValue("id", searchDTO.getRoleId()));
        }
        if (searchDTO.getRoleName() != null) {
            specification = specification.and(SpecificationUtility.equalsValue("name", searchDTO.getRoleName()));
        }
        return specification;
    }

    private static Specification<TradeFlowUser> commonSearchTradeFlowUserSpecification( UserSearchRequest userSearchRequest) {
        Specification<TradeFlowUser> specification =
                SpecificationUtility.equalsValue("isActive", true);
        if( userSearchRequest.getCompanyId() != null ) {
            specification = specification.and(SpecificationUtility.equalsValue("companyId", userSearchRequest.getCompanyId()));
        }
        if( userSearchRequest.getEmployeeId() != null ) {
            specification = specification.and(SpecificationUtility.containsValue("employeeId", userSearchRequest.getEmployeeId()));
        }
        if( userSearchRequest.getUsername() != null ) {
            specification = specification.and(SpecificationUtility.equalsValue("username", userSearchRequest.getUsername()));
        }
        if( userSearchRequest.getUserTypeId() != null ) {
            specification = specification.and(SpecificationUtility.equalsValue("userType","id", userSearchRequest.getUserTypeId()));
        }
        return specification;
    }


    private TradeFlowUser createTradeFlowUserObj( TradeFlowUserRequestDto user ){
        TradeFlowUser tradeFlowUser = new TradeFlowUser();
        tradeFlowUser.setFirstName( user.getFirstName() );
        tradeFlowUser.setLastName( user.getLastName());
        tradeFlowUser.setEmail( user.getEmail());
        tradeFlowUser.setUsername( user.getUsername().toLowerCase());
        tradeFlowUser.setEmployeeId(user.getEmployeeId());
        tradeFlowUser.setMobileNo( user.getMobileNo());
        tradeFlowUser.setCompanyId( user.getCompanyId() );
        tradeFlowUser.setUserType( userTypeRepository.findByName( user.getUserType() ) );
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(user.getUserRoleId()));
        tradeFlowUser.setRoles(roles);
        return tradeFlowUser;
    }


    @Override
    @Transactional
    public GenericResponse addBusinessRole(BusinessRoleRequestDto requestDTO) {
        saveBusinessRole(requestDTO);
        return GenericResponse.createSuccessResponse(environment.getProperty(ADD_BUSINESS_ROLE_SUCCESS_MESSAGE));
    }


    private GenericResponse saveBusinessRole(BusinessRoleRequestDto roleRequestDTO) {
        Role newRole = new Role();
        newRole.setName(roleRequestDTO.getRoleName());
        newRole.setDescription(roleRequestDTO.getDescription());
        newRole.setIsActive(true);
        newRole.setUserType( userTypeRepository.findByName( roleRequestDTO.getUserTypeName()));
        newRole.setCreatedBy(getPrincipal().getUsername());
//        newRole.getModifiedBy( getPrincipal().getUsername());
        Collection<PermissionGroup> permissionGroups = permissionGroupService.getPermissionsByIds(roleRequestDTO.getPermissionGroups().stream().toList());
        newRole.setPermissionGroups(permissionGroups.stream().map(pg -> new PermissionGroup(pg.getId())).collect(Collectors.toSet()));
        roleRepository.save(newRole);
        return new GenericResponse("Business Role created successfully!", null, 1);
    }
}
