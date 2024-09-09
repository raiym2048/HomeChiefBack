package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.dto.chief.ChiefInfoResponse;
import kg.nar.HomeChiefBack.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping("/reguests")
    List<ChiefInfoResponse> responses(){
        return managerService.requests();
    }
}
