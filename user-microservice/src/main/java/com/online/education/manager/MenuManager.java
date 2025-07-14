package com.online.education.manager;

import com.online.education.response.MenuDTO;

public interface MenuManager {

    MenuDTO fetchBusinessMenu(Long userRoleId);
}
