package br.com.fiap.tech.challenge.erp_restaurant.service;

import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.entity.AddressEntity;
import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.AddressMapper;
import br.com.fiap.tech.challenge.erp_restaurant.repository.AddressRepository;
import br.com.fiap.tech.challenge.erp_restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Adiciona um novo endereço para um usuário
    public AddressResponseDTO addAddressToUser(Long userId, AddressRequestDTO addressRequestDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o ID: " + userId));

        AddressEntity addressEntity = AddressMapper.toEntity(addressRequestDTO, user);
        AddressEntity savedAddressEntity = addressRepository.save(addressEntity);

        return AddressMapper.toResponseDTO(savedAddressEntity);
    }

    // Lista todos os endereços de um usuário específico
    public List<AddressResponseDTO> getAllAddressesByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o ID: " + userId));

        List<AddressEntity> addressEntities = addressRepository.findByUser_Id(userId);
        return addressEntities.stream()
                .map(AddressMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Busca um endereço específico por ID e verifica se pertence ao usuário
    public AddressResponseDTO getAddressByUserAndId(Long userId, Long addressId) {
        AddressEntity addressEntity = addressRepository.findByIdAndUser_Id(addressId, userId)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com o ID: " + addressId + " para o usuário com ID: " + userId));

        return AddressMapper.toResponseDTO(addressEntity);
    }

    // Atualiza um endereço existente, verificando se pertence ao usuário
    public AddressResponseDTO updateAddress(Long userId, Long addressId, AddressRequestDTO addressRequestDTO) {
        AddressEntity existingAddressEntity = addressRepository.findByIdAndUser_Id(addressId, userId)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com o ID: " + addressId + " para o usuário com ID: " + userId));

        // Atualiza os campos com os dados do DTO
        existingAddressEntity.setStreet(addressRequestDTO.getStreet());
        existingAddressEntity.setNumber(addressRequestDTO.getNumber());
        existingAddressEntity.setDistrict(addressRequestDTO.getDistrict());
        existingAddressEntity.setCity(addressRequestDTO.getCity());
        existingAddressEntity.setState(addressRequestDTO.getState());
        existingAddressEntity.setZipCode(addressRequestDTO.getZipCode());

        AddressEntity updatedAddressEntity = addressRepository.save(existingAddressEntity);
        return AddressMapper.toResponseDTO(updatedAddressEntity);
    }

    // Remove um endereço por ID, verificando se pertence ao usuário
    public void deleteAddress(Long userId, Long addressId) {
        if (!addressRepository.existsByIdAndUser_Id(addressId, userId)) {
            throw new NoSuchElementException("Endereço não encontrado com o ID: " + addressId + " para o usuário com ID: " + userId);
        }
        addressRepository.deleteById(addressId);
    }
}
