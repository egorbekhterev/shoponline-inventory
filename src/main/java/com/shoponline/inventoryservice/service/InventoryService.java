package com.shoponline.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoponline.inventoryservice.entity.Inventory;
import com.shoponline.inventoryservice.exception.EntityNotExistException;
import com.shoponline.inventoryservice.repository.InventoryRepository;
import com.shoponline.inventoryservice.view.InventoryDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {

    private InventoryRepository inventoryRepository;

    private RabbitMqInventoryPublisher rabbitMqInventoryPublisher;

    private ObjectMapper objectMapper;

    public InventoryDto findById(UUID uuid) {
        return inventoryRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotExistException("Product with id '%s' not found.", uuid)).getDto();
    }

    private String mapObject(Inventory inventory) {
        try {
            return objectMapper.writeValueAsString(inventory);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void create(InventoryDto inventoryDto) {
        Inventory inventory = inventoryRepository.save(inventoryDto.fromDto());
        rabbitMqInventoryPublisher.publishInventoryUpdate(mapObject(inventory));
    }

    @Transactional
    public void update(UUID uuid, InventoryDto inventoryDto) {
        inventoryRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotExistException("Product with id '%s' not found.", uuid));
        Inventory inventory = inventoryDto.fromDto();
        inventory.setId(uuid);
        inventoryRepository.save(inventory);
        rabbitMqInventoryPublisher.publishInventoryUpdate(mapObject(inventory));
    }

    public Page<InventoryDto> findAll(int page, int size) {
        return inventoryRepository.findAll(PageRequest.of(page, size)).map(Inventory::getDto);
    }

    @Transactional
    public void delete(UUID uuid) {
        inventoryRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotExistException("Product with id '%s' not found.", uuid));
        inventoryRepository.deleteById(uuid);
    }
}
