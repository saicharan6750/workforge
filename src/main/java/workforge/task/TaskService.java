package workforge.task;

import org.springframework.stereotype.Service;
import workforge.exception.OrganizationNotFoundException;
import workforge.exception.ProjectNotFoundException;
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

    public Task createTask(
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

        return taskRepository.save(task);
    }

    public List<Task> getTasksByProject(
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

        return taskRepository.findByProjectId(projectId);
    }
}