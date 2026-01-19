package com.example.notebook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequestDto {


    @Size(max = 50)
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Size(max = 2000)
    @NotNull
    private String content;


}
