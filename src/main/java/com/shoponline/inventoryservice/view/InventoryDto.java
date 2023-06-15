package com.shoponline.inventoryservice.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shoponline.inventoryservice.entity.Inventory;
import com.shoponline.inventoryservice.enums.Brand;
import com.shoponline.inventoryservice.enums.Category;
import com.shoponline.inventoryservice.enums.Size;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(value = {"_links"})
public class InventoryDto extends RepresentationModel<InventoryDto> {

    private BigDecimal price;

    private Double weight;

    private String size;

    private String category;

    private String brand;

    private long inStock;

    private OffsetDateTime creationDate;

    public Inventory fromDto() {
        Inventory inventory = new Inventory();
        inventory.setPrice(this.price);
        inventory.setWeight(this.weight);
        inventory.setSize(Size.valueOf(this.size));
        inventory.setCategory(Category.valueOf(this.category));
        inventory.setBrand(Brand.valueOf(this.brand));
        inventory.setInStock(this.inStock);
        inventory.setCreationDate(this.creationDate);
        return inventory;
    }
}
