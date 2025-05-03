package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.entity.AddressEntity;
import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;

public class AddressMapper {
    // Converte AddressRequestDTO para AddressEntity
    public static AddressEntity toEntity(AddressRequestDTO dto, UserEntity user) {
        AddressEntity entity = new AddressEntity();
        entity.setStreet(dto.getStreet());
        entity.setNumber(dto.getNumber());
        entity.setDistrict(dto.getDistrict());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setUser(user);
        return entity;
    }

    // Converte AddressEntity para AddressResponseDTO
    public static AddressResponseDTO toResponseDTO(AddressEntity entity) {
        return new AddressResponseDTO(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getDistrict(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.getUser().getId()
        );
    }
}
