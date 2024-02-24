package com.rcksrs.delivery.infra.role;

import com.rcksrs.delivery.infra.swagger.OpenApiConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Secured("MANAGER")
@SecurityRequirement(name = OpenApiConfig.SECURITY_NAME)
public @interface RequiresManagerRole {
}
