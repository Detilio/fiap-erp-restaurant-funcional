package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressResponseDTO; // Alterado para AddressResponseDTO
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.entity.AddressEntity;
import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    // Converte UserRequestDTO para UserEntity
    public static UserEntity toEntity(UserRequestDTO dto) {
        if (dto == null) return null;

        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setType(dto.getType());
        entity.setDateGeneration(new Date());

        // Se futuramente Address for incluído no UserRequestDTO, mapeie aqui também
        return entity;
    }

    // Converte UserEntity para UserResponseDTO
    public static UserResponseDTO toResponseDTO(UserEntity entity) {
        if (entity == null) return null;

        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getDateChange(),
                entity.getDateGeneration(),
                entity.getType(),
                toAddressResponseDTOList(entity.getDeliveryAddresses()) // Alterado para AddressResponseDTO
        );
    }

    // Converte a lista de AddressEntity para uma lista de AddressResponseDTO
    private static List<AddressResponseDTO> toAddressResponseDTOList(List<AddressEntity> addressEntities) {
        if (addressEntities == null) return Collections.emptyList();

        return addressEntities.stream()
                .map(UserMapper::toAddressResponseDTO) // Alterado para AddressResponseDTO
                .collect(Collectors.toList());
    }

    // Converte AddressEntity para AddressResponseDTO
    private static AddressResponseDTO toAddressResponseDTO(AddressEntity entity) {
        if (entity == null) return null;

        return new AddressResponseDTO(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getDistrict(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.getUser() != null ? entity.getUser().getId() : null
        );
    }
}
