package workforge.task;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/api/organizations/{organizationId}/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable Long organizationId,
            @PathVariable Long projectId,
            @Valid @RequestBody CreateTaskRequest request) {

        return ResponseEntity.ok(
                taskService.createTask(
                        organizationId,
                        projectId,
                        request.getTitle(),
                        request.getDescription(),
                        request.getStatus(),
                        request.getPriority()));
    }

    @GetMapping("/api/organizations/{organizationId}/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(
            @PathVariable Long organizationId,
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                taskService.getTasksByProject(
                        organizationId,
                        projectId));
    }

    @GetMapping("/api/organizations/{organizationId}/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long organizationId,
            @PathVariable Long projectId,
            @PathVariable Long taskId) {

        return ResponseEntity.ok(
                taskService.getTaskById(
                        organizationId,
                        projectId,
                        taskId));
    }

    @PutMapping("/api/organizations/{organizationId}/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long organizationId,
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskRequest request) {

        return ResponseEntity.ok(
                taskService.updateTask(
                        organizationId,
                        projectId,
                        taskId,
                        request.getTitle(),
                        request.getDescription(),
                        request.getStatus(),
                        request.getPriority()));
    }

    @DeleteMapping("/api/organizations/{organizationId}/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long organizationId,
            @PathVariable Long projectId,
            @PathVariable Long taskId) {

        taskService.deleteTask(
                organizationId,
                projectId,
                taskId);

        return ResponseEntity.noContent().build();
    }
}