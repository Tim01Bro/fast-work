package io.project.fastwork.controller;

import io.project.fastwork.controller.exception.RestWorkNotFoundException;
import io.project.fastwork.controller.exception.RestWorkerNotFoundException;
import io.project.fastwork.dto.response.WorkApplicationResponse;
import io.project.fastwork.dto.util.WorkApplicationDtoUtil;
import io.project.fastwork.services.api.WorkApplicationServiceApi;
import io.project.fastwork.services.exception.UserNotFound;
import io.project.fastwork.services.exception.WorkNotFound;
import io.project.fastwork.services.exception.WorkerNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workapplication")
@RequiredArgsConstructor
@Slf4j
public class WorkApplicationController {
    private final WorkApplicationServiceApi workApplicationService;

    @GetMapping("/all")
    public ResponseEntity<?>getAllWorkApplication(){
        List<WorkApplicationResponse>workApplicationResponses= workApplicationService.findAllWorkApplication().stream()
                .map(WorkApplicationDtoUtil::getWorkApplicationRepsonse).toList();
        return ResponseEntity.ok().body(workApplicationResponses);
    }

    @GetMapping("/findbywork/{id_work}")
    public ResponseEntity<?>getAllWorkApplicationByWorkId(@PathVariable("id_work")Long id_work){
        List<WorkApplicationResponse>workApplicationResponsesByWorkId;
        try {
            workApplicationResponsesByWorkId=workApplicationService.findByWorkId(id_work).stream()
                    .map(WorkApplicationDtoUtil::getWorkApplicationRepsonse).toList();
        } catch (WorkNotFound e) {
            throw new RestWorkNotFoundException(String.format("Work with id - %s not found",id_work));
        }
        return ResponseEntity.ok().body(workApplicationResponsesByWorkId);
    }

    @GetMapping("/findbyworker/{id_worker}")
    public ResponseEntity<?>getAllWorkApplicationByWorkerId(@PathVariable("id_worker")Long id_worker){
        List<WorkApplicationResponse>workApplicationResponseList;
        try {
            workApplicationResponseList = workApplicationService.findByWorkerid(id_worker).stream().map(WorkApplicationDtoUtil::getWorkApplicationRepsonse).toList();
        } catch (WorkerNotFound | UserNotFound e) {
            throw new RestWorkerNotFoundException(e.getMessage());
        }
        return ResponseEntity.ok().body(workApplicationResponseList);
    }

}
