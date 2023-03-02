package com.david.ptjob.infra.item.vegetable;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("vegetable")
@ConstructorBinding
@Getter
@ToString
public class VegetableProperties {

    private final String uri;

    public VegetableProperties(String uri) {
        this.uri = uri;
    }
}
