package app.service;

import app.dto.LabelRequestDto;
import app.dto.LabelResponseDto;
import java.util.Set;

public interface LabelService {
    LabelResponseDto create(LabelRequestDto dto);

    Set<LabelResponseDto> getAllLabelsForProject(Long projectId);

    Set<LabelResponseDto> getAllByTaskId(Long taskId);

    LabelResponseDto update(Long id, LabelRequestDto dto);

    void deleteLabel(Long id);
}
