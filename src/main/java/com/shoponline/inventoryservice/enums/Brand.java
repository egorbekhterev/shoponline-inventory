package com.shoponline.inventoryservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Brand {

    SAMSUNG("Samsung"),
    APPLE("Apple"),
    HUAWEI("Huawei");

    private String name;
}
