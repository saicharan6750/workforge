package workforge.task;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import workforge.exception.OrganizationNotFoundException;
import workforge.exception.ProjectNotFoundException;
import workforge.exception.TaskNotFoundException;
import workforge.organization.Organization;
import workforge.organization.OrganizationRepository;
import workforge.project.Project;
import workforge.project.ProjectRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            OrganizationRepository organizationRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public TaskResponse createTask(
            Long organizationId,
            Long projectId,
            String title,
            String description,
            TaskStatus status,
            TaskPriority priority) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with id: " + projectId));

        if (!project.getOrganization().getId().equals(organization.getId())) {
            throw new ProjectNotFoundException(
                    "Project not found in organization with id: " + organizationId);
        }

        title = title.trim();

        if (description != null) {
            description = description.trim();
        }

        Task task = new Task(
                title,
                description,
                status,
                priority,
                project);

        Task savedTask = taskRepository.save(task);

        return new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getPriority());
    }

    public List<TaskResponse> getTasksByProject(
            Long organizationId,
            Long projectId) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with id: " + projectId));

        if (!project.getOrganization().getId().equals(organization.getId())) {
            throw new ProjectNotFoundException(
                    "Project not found in organization with id: " + organizationId);
        }

        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority()))
                .toList();
    }

    public TaskResponse getTaskById(
            Long organizationId,
            Long projectId,
            Long taskId) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with id: " + projectId));

        if (!project.getOrganization().getId().equals(organization.getId())) {
            throw new ProjectNotFoundException(
                    "Project not found in organization with id: " + organizationId);
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(
                        "Task not found with id: " + taskId));

        if (!task.getProject().getId().equals(project.getId())) {
            throw new TaskNotFoundException(
                    "Task not found in project with id: " + projectId);
        }

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority());
    }

    @Transactional
    public TaskResponse updateTask(
            Long organizationId,
            Long projectId,
            Long taskId,
            String title,
            String description,
            TaskStatus status,
            TaskPriority priority) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with id: " + projectId));

        if (!project.getOrganization().getId().equals(organization.getId())) {
            throw new ProjectNotFoundException(
                    "Project not found in organization with id: " + organizationId);
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(
                        "Task not found with id: " + taskId));

        if (!task.getProject().getId().equals(project.getId())) {
            throw new TaskNotFoundException(
                    "Task not found in project with id: " + projectId);
        }

        title = title.trim();

        if (description != null) {
            description = description.trim();
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setUpdatedAt(java.time.LocalDateTime.now());



        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority());
    }

    @Transactional
    public void deleteTask(
            Long organizationId,
            Long projectId,
            Long taskId) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(
                        "Organization not found with id: " + organizationId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with id: " + projectId));

        if (!project.getOrganization().getId().equals(organization.getId())) {
            throw new ProjectNotFoundException(
                    "Project not found in organization with id: " + organizationId);
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(
                        "Task not found with id: " + taskId));

        if (!task.getProject().getId().equals(project.getId())) {
            throw new TaskNotFoundException(
                    "Task not found in project with id: " + projectId);
        }

        taskRepository.delete(task);
    }
}