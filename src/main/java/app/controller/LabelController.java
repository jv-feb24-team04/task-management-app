package app.controller;

import app.dto.LabelRequestDto;
import app.dto.LabelResponseDto;
import app.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/labels")
@Tag(name = "Label", description = "Labels management")
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Label creation", description = "Create and save a new label")
    public LabelResponseDto createLabel(@Valid @RequestBody LabelRequestDto dto) {
        return labelService.create(dto);
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get all labels by project id", description = "Retrieve all labels related to the project")
    public Set<LabelResponseDto> getLabelsByProjectId(@PathVariable Long projectId) {
        return labelService.getAllLabelsForProject(projectId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Label update", description = "Update the label entity by id")
    public LabelResponseDto updateLabel(@PathVariable Long id, @Valid @RequestBody LabelRequestDto dto) {
        return labelService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "label delete", description = "Delete the label entity by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }

}
