package org._404notfound.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ImageDTO {
    @NotBlank
    @Size(max = 255)
    private String name;
}
