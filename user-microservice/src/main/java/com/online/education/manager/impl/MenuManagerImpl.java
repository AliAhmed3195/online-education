package com.online.education.manager.impl;

import com.online.education.Repository.MenuRepository;
import com.online.education.manager.MenuManager;
import com.online.education.projection.MenuView;
import com.online.education.response.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuManagerImpl implements MenuManager {

    @Autowired
    private MenuRepository menuRepository;


    public MenuDTO fetchBusinessMenu(Long roleId) {
        List<MenuView> parentMenu = menuRepository.findByParentMenuIsNull(Sort.by(Sort.Direction.ASC, "orderId"));
        List<MenuView> childMenu = menuRepository.fetchChildMenu(roleId);
        return new MenuDTO(convert(parentMenu, childMenu));
    }


    private List<MenuDTO.Menu> convert(List<MenuView> parentMenu, List<MenuView> childMenu) {
        List<MenuDTO.Menu> menus = parentMenu.stream()
                .map(menuView -> new MenuDTO.Menu(menuView.getId(), menuView.getDisplayName(), new ArrayList()))
                .collect(Collectors.toList());
        menus.stream().forEach(menu -> {
            List<MenuView> childOfThisMenu = childMenu.stream().filter(menuView -> menuView.getParentMenuId()==menu.getId()).collect(Collectors.toList());
            if( !childOfThisMenu.isEmpty() ) {
                menu.setMenus( childOfThisMenu.stream()
                        .map(menuView -> new MenuDTO.Menu(menuView.getId(), menuView.getDisplayName(),null))
                        .collect(Collectors.toList()) );
                childMenu.removeAll(childOfThisMenu);
            }
        });
        List<MenuDTO.Menu> menuList =  menus.stream().filter(menu -> !menu.getMenus().isEmpty()).collect(Collectors.toList());
        return menuList;
    }
}
