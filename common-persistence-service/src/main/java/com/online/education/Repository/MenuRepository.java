package com.online.education.Repository;

import com.online.education.entity.Menu;
import com.online.education.projection.MenuView;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<MenuView> findByParentMenuIsNull(Sort sort);

    @Query("select distinct m.id as id, m.displayName as displayName, m.parentMenu.id as parentMenuId, m.orderId as orderId from Menu m " +
            "join m.permissionGroups pg join pg.roles r where r.id=:roleId " +
            "and m.isActive=true order by m.orderId asc")
    List<MenuView> fetchChildMenu(long roleId);

}
