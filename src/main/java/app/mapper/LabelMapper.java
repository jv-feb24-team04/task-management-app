package app.mapper;

import app.config.MapperConfig;
import app.dto.label.LabelRequestDto;
import app.dto.label.LabelResponseDto;
import app.model.Label;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LabelMapper {
    Label toModel(LabelRequestDto dto);

    LabelResponseDto toDto(Label label);

    Set<LabelResponseDto> toDtoSet(Set<Label> labelSet);

    @Named("setLabels")
    default List<LabelResponseDto> setLabels(List<Label> labels) {
        return Optional.ofNullable(labels).orElse(Collections.emptyList()).stream()
                .map(this::toDto)
                .toList();
    }
}
