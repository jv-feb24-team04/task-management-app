package app.service.label;

import app.dto.LabelRequestDto;
import app.dto.LabelResponseDto;
import app.exception.EntityNotFoundException;
import app.mapper.LabelMapper;
import app.model.Label;
import app.repository.LabelRepository;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LabelServiceImpl implements LabelService {
    private final LabelRepository repository;
    private final LabelMapper mapper;

    @Override
    public LabelResponseDto create(LabelRequestDto dto) {
        Label newLabel = mapper.toModel(dto);
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
        throw new EntityNotFoundException("Failed to find entity by id=" + id);
    }

    @Override
    public void delete(Long id) {
        Label label = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Failed to find entity by id=" + id));
        repository.delete(label);
    }
}
