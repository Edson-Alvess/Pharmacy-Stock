package com.farmacia.farmacia.dtos;


import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ProductRecordDto(UUID id_product , String name,@Positive double value, String description, int quantity) {
}
