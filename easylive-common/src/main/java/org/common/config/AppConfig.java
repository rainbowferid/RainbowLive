package org.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
public class AppConfig {

    @Value("${project.folder}")
    private String projectFolder;
    @Value("${admin.account}")
    private String adminAccount;
    @Value("${admin.password}")
    private String adminPassword;


}
