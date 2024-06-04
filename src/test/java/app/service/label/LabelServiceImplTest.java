package app.service.label;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import app.dto.label.LabelRequestDto;
import app.dto.label.LabelResponseDto;
import app.mapper.LabelMapper;
import app.model.Label;
import app.repository.LabelRepository;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LabelServiceImplTest {

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private LabelMapper labelMapper;

    @InjectMocks
    private LabelServiceImpl labelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllForProject_ValidProjectId_ReturnsSetOfLabelResponseDto() {
        // Arrange
        Label label = new Label();
        label.setName("Label Name");
        Set<Label> labels = Set.of(label);

        LabelResponseDto responseDto = new LabelResponseDto();
        responseDto.setName("Label Name");
        Set<LabelResponseDto> responseDtos = Set.of(responseDto);

        when(labelRepository.findAllByProjectId(1L)).thenReturn(labels);
        when(labelMapper.toDtoSet(labels)).thenReturn(responseDtos);

        // Act
        Set<LabelResponseDto> result = labelService.getAllForProject(1L);

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

    @Test
    public void getAllByTaskId_ValidTaskId_ReturnsSetOfLabelResponseDto() {
        // Arrange
        Label label = new Label();
        label.setName("Label Name");
        Set<Label> labels = Set.of(label);

        LabelResponseDto responseDto = new LabelResponseDto();
        responseDto.setName("Label Name");
        Set<LabelResponseDto> responseDtos = Set.of(responseDto);

        when(labelRepository.findAllByTaskId(1L)).thenReturn(labels);
        when(labelMapper.toDtoSet(labels)).thenReturn(responseDtos);

        // Act
        Set<LabelResponseDto> result = labelService.getAllByTaskId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

    @Test
    public void update_ValidData_ReturnsLabelResponseDto() {
        // Arrange
        Long labelId = 1L;
        LabelRequestDto requestDto = new LabelRequestDto();
        requestDto.setName("Label Name");

        Label label = new Label();
        label.setId(labelId);
        label.setName("Label Name");

        LabelResponseDto responseDto = new LabelResponseDto();
        responseDto.setName("Label Name");

        when(labelRepository.findById(labelId)).thenReturn(Optional.of(label));
        when(labelRepository.save(label)).thenReturn(label);
        when(labelMapper.toDto(label)).thenReturn(responseDto);

        // Act
        LabelResponseDto result = labelService.update(labelId, requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(responseDto.getName(), result.getName());
    }
}
