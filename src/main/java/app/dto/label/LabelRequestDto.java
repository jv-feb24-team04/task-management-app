package app.dto.label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelRequestDto {
    @NotBlank(message = "Define the label name")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Pick the label colour")
    @Size(max = 17)
    private String color;
}
