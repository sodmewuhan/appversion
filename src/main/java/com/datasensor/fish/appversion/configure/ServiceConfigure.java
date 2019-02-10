package com.datasensor.fish.appversion.configure;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ServiceConfigure {

    @Value( "${application.storeFilePath}" )
    private String storeFilePath;

    @Value( "${application.imagesPath}" )
    private String imagesPath;
}
