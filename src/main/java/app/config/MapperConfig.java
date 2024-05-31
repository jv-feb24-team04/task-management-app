package app.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public class MapperConfig {
}
