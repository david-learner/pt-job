package com.david.ptjob.infra.item.fruit;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("fruit")
@ConstructorBinding
@Getter
@ToString
public class FruitProperties {

    private final String uri;

    public FruitProperties(String uri) {
        this.uri = uri;
    }
}
