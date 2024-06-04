package app.service.label;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import app.dto.label.LabelRequestDto;
import app.dto.label.LabelResponseDto;
import app.mapper.LabelMapper;
import app.model.Label;
import app.repository.LabelRepository;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LabelServiceImplTest {
    @Mock
    private LabelRepository labelRepository;
    @Mock
    private LabelMapper labelMapper;

    private LabelService labelService;

    @BeforeEach
    void setUp() {
        labelService = new LabelServiceImpl(labelRepository, labelMapper);
    }

    @Test
    public void create_ValidLabelRequestDto_ReturnsLabelResponseDto() {
        LabelRequestDto requestDto = new LabelRequestDto();
        requestDto.setName("Label Name");

        Label label = new Label();
        label.setName("Label");

        LabelResponseDto responseDto = new LabelResponseDto();
        responseDto.setName("Response Name");

        when(labelMapper.toModel(requestDto)).thenReturn(label);
        when(labelRepository.save(label)).thenReturn(label);
        when(labelMapper.toDto(label)).thenReturn(responseDto);

        LabelResponseDto result = labelService.create(requestDto);
        assertNotNull(result);
        assertEquals(responseDto.getName(), result.getName());
    }

    @Test
    public void getAllForProject_ValidProjectId_ReturnsSetOfLabelResponseDto() {
        Label label = new Label();
        label.setName("Label Name");
        Set<Label> labels = Set.of(label);

        LabelResponseDto responseDto = new LabelResponseDto();
        responseDto.setName("Label Name");
        Set<LabelResponseDto> responseDtos = Set.of(responseDto);

        when(labelRepository.findAllByProjectId(1L))
                .thenReturn(labels);
        when(labelMapper.toDtoSet(labels)).thenReturn(responseDtos);

        Set<LabelResponseDto> result = labelService.getAllForProject(1L);
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }
}
