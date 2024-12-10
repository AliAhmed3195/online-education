INSERT INTO learning_ds.trade_flow_user ( EMAIL, EMPLOYEE_ID, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, COMPANY_ID, MOBILE_NO, USER_TYPE_ID, CREATED_BY, CREATED_ON, IS_ACTIVE, MODIFIED_BY,MODIFIED_ON ) VALUES ( 'aliahmed@3195gmail.com', '1', 'Ali', 'ali', '$argon2id$v=19$m=4096,t=3,p=1$xYCd5+2xAt22ts43218ioA$MVoN2g8+WXgImVVizx7jJXrs5sFdJh1dC/iMJDgFAHw', 'admin', 1, '03343071241', 1, 'admin', CURRENT_TIMESTAMP, 1, 'admin', CURRENT_TIMESTAMP);

INSERT INTO learning_ds.USER_TYPE ( NAME, DESCRIPTION, CREATED_BY, CREATED_ON, IS_ACTIVE, MODIFIED_BY,MODIFIED_ON) VALUES( 'admin', 'admin', 'admin', CURRENT_TIMESTAMP, 1 , 'admin', CURRENT_TIMESTAMP );
INSERT INTO learning_ds.USER_TYPE ( NAME, DESCRIPTION, CREATED_BY, CREATED_ON, IS_ACTIVE, MODIFIED_BY,MODIFIED_ON) VALUES( 'supplier', 'supplier', 'admin', CURRENT_TIMESTAMP, 1 , 'admin', CURRENT_TIMESTAMP );
INSERT INTO learning_ds.USER_TYPE ( NAME, DESCRIPTION, CREATED_BY, CREATED_ON, IS_ACTIVE, MODIFIED_BY,MODIFIED_ON) VALUES( 'vendor', 'vendor', 'admin', CURRENT_TIMESTAMP, 1 , 'admin', CURRENT_TIMESTAMP );


insert into learning_ds.role (created_on, created_by, is_active, modified_by, modified_on, description, name, purpose_type_id)
values(CURRENT_TIMESTAMP, 'avanza', 1, 'avanza', CURRENT_TIMESTAMP, 'Admin role', 'Admin role', 1);
INSERT INTO learning_ds.user_role (ROLE_ID, USER_ID) VALUES (1, 1);


--Permission queries ////////////////////////////////////////////////////////////////////

INSERT INTO learning_ds.permission_group_parent (ID, NAME, DESCRIPTION, ORDER_ID, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON)
VALUES(1, 'User Management', 'User Management', 1, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO learning_ds.permission_group_parent (ID, NAME, DESCRIPTION, ORDER_ID, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON)
VALUES(2, 'Inventory Management', 'Inventory Management', 2, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);
INSERT INTO learning_ds.permission_group_parent (ID, NAME, DESCRIPTION, ORDER_ID, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON)
VALUES(3, 'Order Management', 'Order Management', 3, 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(101, 'User Management - User List and View Details', 'User Management User List and View Details', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(102, 'User Management - User Create and Update', 'User Management - User Create and Update', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(103, 'Business Role And Permission - Role List and View', 'Business Role And Permission - Role List and View', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(104, 'Business Role And Permission - Role Create and Update', 'Business Role And Permission - Role Create and Update', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(105, 'Get All Permission Group - All Permission Group and Permission Group Parent', 'Get All Permission Group - All Permission Group and Permission Group Parent', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);


INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(101, 'All User List', 'All User List', '/user-microservice/api/v1/users/list', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(102, 'User - User Create','User - User Create', '/user-microservice/api/v1/user/create', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(103, 'User View Detail','User View Detail', '/user-microservice/api/v1/users/view-details', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(104, 'User Update','User Update', '/user-microservice/api/v1/user/update', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(105, 'All Role List','All Role List', '/user-microservice/api/v1/users/business-roles', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(106, 'Business Role Permission Group List','Business Role Permission Group List', '/user-microservice/api/v1/business-role-permission-groups/list', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(107, 'Business Role Permission Create','Business Role Permissions - Create', '/user-microservice/api/v1/business-role-permission-groups/create', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(108, 'Business Role Permission - Update Role Permissions','Business Role Permission - Update Role Permission', '/user-microservice/api/v1/business-role-permission-groups/update', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(109, 'Business Role Permission - View Detail','Business Role Permission - View Detail', '/user-microservice/api/v1/business-permission-groups/create', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);

INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (101, 101);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (103, 101);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (102, 102);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (104, 102);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (106, 105);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (105, 103);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (109, 103);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (107, 104);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (108, 104);

insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 101);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 102);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 103);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 104);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 105);


-- Inventory Management Permission Queries

INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(106, 'Inventory Management - Admin Item List and View Details', 'Inventory Management - Admin Item List and View Details', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 2);
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(107, 'Inventory Management - Admin Item Create and Update', 'Inventory Management - Admin Item Create and Update', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 2);

INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(108, 'Inventory Management - Supplier Item List and View Details', 'Inventory Management - Supplier Item List and View Details', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 2);
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(109, 'Inventory Management - Supplier Item Create and Update', 'Inventory Management - Supplier Item Create and Update', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 2);

INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(110, 'Inventory Management - Vendor Item List and View Details', 'Inventory Management - Vendor Item List and View Details', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 2);
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(111, 'Inventory Management - Vendor Item Create and Update', 'Inventory Management - Vendor Item Create and Update', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 2);

INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(110, 'Admin - All Item List' , 'Admin - All Item List', '/inventory-microservice/api/v1/admin/item/list', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(111, 'Admin - Item Create' , 'Admin - Item Create', '/inventory-microservice/api/v1/admin/item/create', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(112, 'Admin - Item View Details' , 'Admin - Item View Details', '/inventory-microservice/api/v1/admin/item/view-details', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(113, 'Admin - Item Update' , 'Admin - Item Update', '/inventory-microservice/api/v1/admin/item/update', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);

INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(114, 'Supplier - All Item List' , 'Supplier - All Item List', '/inventory-microservice/api/v1/supplier/item/list', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(115, 'Supplier - Item Create' , 'Supplier - Item Create', '/inventory-microservice/api/v1/supplier/item/create', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(116, 'Supplier - Item View Details' , 'Supplier - Item View Details', '/inventory-microservice/api/v1/supplier/item/view-details', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(117, 'Supplier - Item Update' , 'Supplier - Item Update', '/inventory-microservice/api/v1/supplier/item/update', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);

INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(118, 'Vendor - All Item List' , 'Vendor - All Item List', '/inventory-microservice/api/v1/vendor/item/list', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(119, 'Vendor - Item Create' , 'Vendor - Item Create', '/inventory-microservice/api/v1/vendor/item/create', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(120, 'Vendor - Item View Details' , 'Vendor - Item View Details', '/inventory-microservice/api/v1/vendor/item/view-details', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(121, 'Vendor - Item Update' , 'Vendor - Item Update', '/inventory-microservice/api/v1/vendor/item/update', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);

INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (110, 106);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (112, 106);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (111, 107);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (113, 107);

INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (114, 108);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (116, 108);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (115, 109);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (117, 109);

INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (118, 110);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (120, 110);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (119, 111);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (121, 111);

-- Setting For Admin Role

insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 106);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 107);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 108);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 109);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 110);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 111);



-- order Permission queries
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(112, 'Order Management - Order List and View', 'Order Management - Order List and View', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 3);
INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(113, 'Order Management - Order Create and Update', 'Order Management - Order Create and Update', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 3);

INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(122, 'Order - All Orders List' , 'Order - All Orders List', '/order-processing-microservice/api/v1/orders/list', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(123, 'Order - Order Create' , 'Order - Order Create', '/order-processing-microservice/api/v1/orders/create', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(124, 'Order - Order View Details' , 'Order - Order View Details', '/order-processing-microservice/api/v1/orders/view-details', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(125, 'Order - Get Order By Customer Id' , 'Order - Get Order By Customer Id', '/order-processing-microservice/api/v1/orders/update', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);

INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (122, 112);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (124, 112);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (123, 113);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (125, 113);


-- Setting For Admin Role Just

insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 112);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 113);



INSERT INTO learning_ds.PERMISSION_GROUP (ID, NAME, DESCRIPTION, IS_ACTIVE, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, PARENT_ID)
VALUES(114, 'Get Menus - Get All Menus', 'Get Menus - Get All Menus', 1, 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT INTO learning_ds.PERMISSION (ID, NAME, DESCRIPTION, URI, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, IS_ACTIVE)
VALUES(126, 'Get Menus - Get All Menus','Get Menus - Get All Menus', '/user-microservice/api/v1/users/menus', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 1);
INSERT into learning_ds.permission_group_permission(permission_id, permission_group_id) values (126, 114);
insert into learning_ds.role_permission_group (role_id, permission_group_id) values(1, 114);



INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (100, 'User Management', 'User Management', null, 1, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (101, 'Users', 'Users', 100, 1, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (102, 'Business Roles And permission', 'Business Roles And permission', 100, 1, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');

INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (103, 'Inventory Management', 'Inventory Management', null, 2, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (104, 'Admin Inventory', 'Admin Inventory', 103, 1, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (105, 'Supplier Inventory', 'Supplier Inventory', 103, 3, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (106, 'Vendor Inventory', 'Vendor Inventory', 103, 4, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (107, 'Order Management', 'Order Management', null, 3, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (108, 'Order Processing', 'Order Processing', 107, 1, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (109, 'Payment Processing', 'Payment Processing', 107, 2, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');
INSERT INTO learning_ds.MENU (ID, NAME, DISPLAY_NAME, PARENT_MENU_ID, ORDER_ID, IS_ACTIVE, CREATED_ON, CREATED_BY, MODIFIED_ON, MODIFIED_BY)
VALUES (110, 'Shipment Processing', 'Shipment Processing', 107, 3, 1, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 'admin');


-- MENU PERMISSION GROUP MAPPING
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (101, 101);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (101, 102);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (102, 103);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (102, 104);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (102, 105);

INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (104, 106);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (104, 107);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (105, 108);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (105, 109);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (106, 110);
INSERT INTO learning_ds.MENU_PERMISSION_GROUP (MENU_ID, PERMISSION_GROUP_ID) VALUES (106, 111);