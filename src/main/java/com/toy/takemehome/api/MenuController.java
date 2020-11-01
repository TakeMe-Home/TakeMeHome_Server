package com.toy.takemehome.api;

import com.toy.takemehome.dto.menu.MenuDetail;
import com.toy.takemehome.dto.menu.MenuRegisterRequest;
import com.toy.takemehome.dto.menu.MenuUpdateRequest;
import com.toy.takemehome.entity.menu.Menu;
import com.toy.takemehome.service.MenuService;
import com.toy.takemehome.utils.DefaultRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.toy.takemehome.utils.ResponseMessage.*;
import static com.toy.takemehome.utils.StatusCode.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public DefaultRes<Long> register(@RequestBody MenuRegisterRequest registerRequest) {
        try {
            final Long id = menuService.register(registerRequest);
            return DefaultRes.res(OK, CREATE_MENU, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_MENU_FAIL);
        }
    }

    @GetMapping("/menu/{id}")
    public DefaultRes<MenuDetail> findOneById(@PathVariable("id") Long id) {
        try {
            final Menu menu = menuService.findOneById(id);
            final MenuDetail menuDetail = new MenuDetail(menu);
            return DefaultRes.res(OK, FIND_MENU, menuDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(NOT_FOUND, NOT_FOUND_MENU);
        }
    }

    @PutMapping("/menu/{id}")
    public DefaultRes<Long> update(@PathVariable("id") Long id,
                                   @RequestBody MenuUpdateRequest updateRequest) {
        try {
            menuService.update(id, updateRequest);
            return DefaultRes.res(OK, UPDATE_MENU, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, UPDATE_MENU_FAIL);
        }
    }

    @DeleteMapping("/menu/{id}")
    public DefaultRes<Long> delete(@PathVariable("id") Long id) {
        try {
            menuService.delete(id);
            return DefaultRes.res(OK, DELETE_MENU, id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, DELETE_MENU_FAIL);
        }
    }
}
