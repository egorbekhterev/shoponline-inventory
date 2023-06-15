package com.shoponline.inventoryservice.controller;

import com.shoponline.inventoryservice.service.InventoryService;
import com.shoponline.inventoryservice.view.InventoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/v1/inventory")
@Slf4j
@AllArgsConstructor
@Tag(name = "Сервис поиска товаров")
public class InventoryController {

    private InventoryService inventoryService;

    @Operation(summary = "Поиск товара по ID")
    @GetMapping(value = "/{inventoryUUID}")
    @ResponseStatus(HttpStatus.FOUND)
    public EntityModel<InventoryDto> findById(@RequestParam UUID inventoryUUID) {
        Link selfLink = linkTo(methodOn(InventoryController.class).findById(inventoryUUID)).withSelfRel();
        Link aggregateRoot = linkTo(methodOn(InventoryController.class).findAll(0, Integer.MAX_VALUE))
                .withRel("all");
        return EntityModel.of(inventoryService.findById(inventoryUUID), selfLink, aggregateRoot);
    }

    @Operation(summary = "Получить список всех товаров")
    @GetMapping
    @ResponseBody
    public EntityModel<Page<InventoryDto>> findAll(@RequestParam int page, @RequestParam int size) {
        Link selfLink = linkTo(methodOn(InventoryController.class).findAll(0, Integer.MAX_VALUE)).withSelfRel();
        return EntityModel.of(inventoryService.findAll(page, size), selfLink);
    }

    @Operation(summary = "Создание товара")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody InventoryDto inventoryDto) {
        log.info("Product saved in database.");
        inventoryService.create(inventoryDto);
    }

    @Operation(summary = "Обновление свойств товара по ID")
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam UUID uuid, @RequestBody InventoryDto inventoryDto) {
        inventoryService.update(uuid, inventoryDto);
    }

    @Operation(summary = "Удалить товар")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam UUID uuid) {
        inventoryService.delete(uuid);
    }
}
