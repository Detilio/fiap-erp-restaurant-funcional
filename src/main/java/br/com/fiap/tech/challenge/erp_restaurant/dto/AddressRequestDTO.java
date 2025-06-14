package br.com.fiap.tech.challenge.erp_restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AddressRequestDTO {
    private Long id; // Adicionado ID para identificar o endereço, se necessário na resposta

    @NotBlank(message = "A rua é obrigatória")
    private String street;

    @NotBlank(message = "O número é obrigatório")
    private String number;

    @NotBlank(message = "O bairro é obrigatório")
    private String district;

    @NotBlank(message = "A cidade é obrigatória")
    private String city;

    @NotBlank(message = "O estado é obrigatório")
    private String state;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Formato de CEP inválido (ex: 00000-000)")
    private String zipCode;

    public AddressRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }
}
