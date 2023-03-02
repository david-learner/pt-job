package com.david.ptjob.infra.item;

import com.david.ptjob.item.domain.Item;

public interface ItemClient {

    Item findItemByName(String name);

    String getAccessToken();
}
