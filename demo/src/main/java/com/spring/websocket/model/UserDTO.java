package com.spring.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    @NotNull
    private String username;
    @Min(value = 18, message = "{min.validation.msg}")
    private Integer age;
    private String occupation;
    @NotNull
    private LocalDate birthday;
}
