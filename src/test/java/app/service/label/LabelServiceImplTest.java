package app.service.label;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import app.dto.label.LabelRequestDto;
import app.dto.label.LabelResponseDto;
import app.mapper.LabelMapper;
import app.model.Label;
import app.repository.LabelRepository;
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
}
