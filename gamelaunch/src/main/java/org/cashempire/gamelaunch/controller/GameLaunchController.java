package org.cashempire.gamelaunch.controller;

import lombok.AllArgsConstructor;
import org.cashempire.gamelaunch.dto.GameLaunchRequest;
import org.cashempire.gamelaunch.exception.GameLaunchException;
import org.cashempire.gamelaunch.service.GameLaunchService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import static org.cashempire.gamelaunch.util.Static.*;

@Controller
@AllArgsConstructor
@RequestMapping(HOMEPATH)
public class GameLaunchController {

    public GameLaunchService gameLaunchService;

    @RequestMapping(GAMELAUNCHPATH)
    public ResponseEntity<String> gameLaunch(@Validated GameLaunchRequest gameLaunchRequest, HttpServletRequest httpServletRequest) throws GameLaunchException {
       String gameLaunchUrl = gameLaunchService.gameLaunch(gameLaunchRequest,httpServletRequest);
       return ResponseEntity.ok(gameLaunchUrl);
    }



}
