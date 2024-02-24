package com.rcksrs.delivery.core.domain.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record AuthUserRequest(
        @NotBlank @Email String email,
        @NotBlank String password) {

}
