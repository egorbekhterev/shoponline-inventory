package com.shoponline.inventoryservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shoponline.inventoryservice.enums.Brand;
import com.shoponline.inventoryservice.enums.Category;
import com.shoponline.inventoryservice.enums.Size;
import com.shoponline.inventoryservice.view.InventoryDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventory")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode
public class Inventory {

    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(
            name = "custom-uuid",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;

    private BigDecimal price;

    private Double weight;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Column(name = "in_stock")
    private long inStock;

    @Column(name = "creation_date")
    private OffsetDateTime creationDate = OffsetDateTime.now();

    //TODO добавить потом связь для файлохранилища фотографий

    public InventoryDto getDto() {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setPrice(this.price);
        inventoryDto.setWeight(this.weight);
        inventoryDto.setSize(this.size.getName());
        inventoryDto.setCategory(this.category.getName());
        inventoryDto.setBrand(this.brand.getName());
        inventoryDto.setInStock(this.inStock);
        inventoryDto.setCreationDate(this.creationDate);
        return inventoryDto;
    }
}
