package com.shoponline.inventoryservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    COMPUTERS("Computers"),
    ELECTRONICS("ELECTRONICS"),
    SOFTWARE("SOFTWARE");

    private String name;
}
