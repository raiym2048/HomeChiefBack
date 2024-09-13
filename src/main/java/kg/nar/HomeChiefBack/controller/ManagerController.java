package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.RequestHistoryResponse;
import kg.nar.HomeChiefBack.dto.chief.ChiefInfoResponse;
import kg.nar.HomeChiefBack.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping("/reguests")
    List<ChiefInfoResponse> responses(@RequestParam String activated) {
        return managerService.requests(activated);
    }

    @PostMapping("/accept/{chiefId}")
    public void accept(@PathVariable UUID chiefId,
            @RequestParam String accepted, @RequestParam String comment,
            @RequestHeader("Authorization") String token) {
         managerService.accept(token, chiefId, accepted, comment);
    }





    @PostMapping("/add/request/status")
    public void addRequestStatus(@RequestParam String status){
        managerService.addRequestStatus(status);
    }
    @GetMapping("/request/statuses")
    public List<ObjectDto> getRequestStatuses(){
        return managerService.requestStatuses();
    }

    @GetMapping("/request/history")
    public List<RequestHistoryResponse> getRequestHistory(){
        return managerService.requestHistory();
    }

}
