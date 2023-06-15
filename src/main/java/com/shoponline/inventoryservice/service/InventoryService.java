package com.shoponline.inventoryservice.service;

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

    public InventoryDto findById(UUID uuid) {
        return inventoryRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotExistException("Product with id '%s' not found.", uuid)).getDto();
    }

    public void create(InventoryDto inventoryDto) {
        inventoryRepository.save(inventoryDto.fromDto());
    }

    @Transactional
    public void update(UUID uuid, InventoryDto inventoryDto) {
        inventoryRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotExistException("Product with id '%s' not found.", uuid));
        Inventory inventory = inventoryDto.fromDto();
        inventory.setId(uuid);
        inventoryRepository.save(inventory);
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
