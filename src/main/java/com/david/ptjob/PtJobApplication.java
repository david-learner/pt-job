package com.david.ptjob;

import com.david.ptjob.infra.item.fruit.FruitProperties;
import com.david.ptjob.infra.item.vegetable.VegetableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@EnableConfigurationProperties(value = {FruitProperties.class, VegetableProperties.class})
public class PtJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(PtJobApplication.class, args);
    }

}
