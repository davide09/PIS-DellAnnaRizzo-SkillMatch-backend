
package com.skillmatch.project_service.listeners;

import com.skillmatch.project_service.config.RabbitConfig;
import com.skillmatch.project_service.events.ContractEvent;
import com.skillmatch.project_service.model.Project;
import com.skillmatch.project_service.model.ProjectStatus;
import com.skillmatch.project_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractEventListener {

    private final ProjectRepository projectRepository;

    @RabbitListener(queues = RabbitConfig.CONTRACT_EVENT_QUEUE)
    public void handleContractEvent(ContractEvent event) {
        log.info("ProjectService - ricevuto ContractEvent: {}", event);

        if (event.getProjectId() == null) {
            log.warn("ContractEvent senza projectId, ignoro");
            return;
        }

        Optional<Project> opt = projectRepository.findById(event.getProjectId());
        if (opt.isEmpty()) {
            log.warn("Project non trovato per id {}", event.getProjectId());
            return;
        }

        Project project = opt.get();

        switch (event.getEventType()) {
            case "CONTRACT_CREATED" -> {
                // non cambiamo stato qui, rimane OPEN
            }
            case "CONTRACT_STARTED" -> project.setStatus(ProjectStatus.IN_PROGRESS);
            case "CONTRACT_COMPLETED" -> project.setStatus(ProjectStatus.COMPLETED);
            case "CONTRACT_CANCELLED" -> project.setStatus(ProjectStatus.CANCELLED);
            default -> log.info("Tipo evento non gestito: {}", event.getEventType());
        }

        projectRepository.save(project);
        log.info("Project {} aggiornato allo stato {}", project.getId(), project.getStatus());
    }
}