package app.mapper;

import app.config.MapperConfig;
import app.dto.LabelRequestDto;
import app.dto.LabelResponseDto;
import app.model.Label;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LabelMapper {
    Label toModel(LabelRequestDto dto);

    LabelResponseDto toDto(Label label);

    Set<LabelResponseDto> toDtoSet(Set<Label> labelSet);
}
