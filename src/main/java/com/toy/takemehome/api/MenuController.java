package com.toy.takemehome.api;

import com.toy.takemehome.dto.menu.MenuRegisterRequest;
import com.toy.takemehome.service.MenuService;
import com.toy.takemehome.utils.DefaultRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.toy.takemehome.utils.ResponseMessage.*;
import static com.toy.takemehome.utils.StatusCode.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public DefaultRes<Long> register(@RequestBody MenuRegisterRequest registerRequest){
        try {
            final Long id = menuService.register(registerRequest);
            return DefaultRes.res(OK, CREATE_MENU, id);
        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultRes.res(BAD_REQUEST, CREATE_MENU_FAIL);
        }
    }
}
