package app.service.label;

import app.dto.label.LabelRequestDto;
import app.dto.label.LabelResponseDto;
import app.exception.EntityNotFoundException;
import app.mapper.LabelMapper;
import app.model.Label;
import app.model.Task;
import app.repository.LabelRepository;
import app.repository.TaskRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LabelServiceImpl implements LabelService {
    private final LabelRepository repository;
    private final TaskRepository taskRepository;
    private final LabelMapper mapper;

    @Transactional
    @Override
    public LabelResponseDto create(LabelRequestDto dto, Long taskId) {
        Label newLabel = mapper.toModel(dto);

        Optional<Task> taskOptional = Optional.ofNullable(taskRepository.findById(taskId)
                        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: "
                        + taskId)));

        Task task = taskOptional.get();
        newLabel.setTasks(Collections.singletonList(task));
        task.getLabels().add(newLabel);

        return mapper.toDto(repository.save(newLabel));
    }

    @Override
    public Set<LabelResponseDto> getAllForProject(Long projectId) {
        return mapper.toDtoSet(repository.findAllByProjectId(projectId));
    }

    @Override
    public Set<LabelResponseDto> getAllByTaskId(Long taskId) {
        return mapper.toDtoSet(repository.findAllByTaskId(taskId));
    }

    @Override
    public LabelResponseDto update(Long id, LabelRequestDto dto) {
        Optional<Label> optionalLabel = repository.findById(id);
        if (optionalLabel.isPresent()) {
            Label existingLabel = optionalLabel.get();

            existingLabel.setName(dto.getName());
            existingLabel.setColor(dto.getColor());

            Label updatedLabel = repository.save(existingLabel);
            return mapper.toDto(updatedLabel);
        }
        throw new EntityNotFoundException("Failed to find Label by id=" + id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Label label = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Failed to find Label by id=" + id));

        for (Task task : label.getTasks()) {
            task.getLabels().remove(label);
        }

        repository.delete(label);
    }
}
