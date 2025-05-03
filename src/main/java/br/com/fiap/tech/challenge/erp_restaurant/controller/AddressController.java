package br.com.fiap.tech.challenge.erp_restaurant.controller;

import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Adiciona um novo endereço para o usuário
    @PostMapping
    public ResponseEntity<AddressResponseDTO> addAddress(@PathVariable Long userId,
                                                         @Valid @RequestBody AddressRequestDTO addressRequestDTO) {
        AddressResponseDTO addedAddress = addressService.addAddressToUser(userId, addressRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAddress);
    }

    // Lista todos os endereços do usuário
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses(@PathVariable Long userId) {
        List<AddressResponseDTO> addresses = addressService.getAllAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    // Busca um endereço específico por ID
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long userId,
                                                             @PathVariable Long addressId) {
        AddressResponseDTO address = addressService.getAddressByUserAndId(userId, addressId);
        return ResponseEntity.ok(address);
    }

    // Atualiza um endereço existente
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long userId,
                                                            @PathVariable Long addressId,
                                                            @Valid @RequestBody AddressRequestDTO addressRequestDTO) {
        AddressResponseDTO updatedAddress = addressService.updateAddress(userId, addressId, addressRequestDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    // Remove um endereço por ID
    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long userId,
                                                @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok("Endereço " + addressId + " do usuário " + userId + " excluído com sucesso!");
    }
}
