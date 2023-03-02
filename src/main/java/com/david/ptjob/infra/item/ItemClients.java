package com.david.ptjob.infra.item;

import com.david.ptjob.infra.item.fruit.FruitClient;
import com.david.ptjob.infra.item.vegetable.VegetableClient;
import com.david.ptjob.item.domain.Category;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ItemClients {

    private final Map<Category, ItemClient> itemClients = new HashMap<>();

    public ItemClients(FruitClient fruitClient, VegetableClient vegetableClient) {
        itemClients.put(Category.FRUIT, fruitClient);
        itemClients.put(Category.VEGETABLE, vegetableClient);
    }

    public ItemClient findClient(Category category) {
        return itemClients.get(category);
    }
}
