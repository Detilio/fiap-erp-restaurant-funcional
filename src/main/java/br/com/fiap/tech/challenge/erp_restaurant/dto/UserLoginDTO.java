package br.com.fiap.tech.challenge.erp_restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
    @NotBlank(message = "O login é obrigatório")
    private String login;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    public UserLoginDTO() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}