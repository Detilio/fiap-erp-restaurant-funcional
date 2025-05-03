package br.com.fiap.tech.challenge.erp_restaurant.repository;

import br.com.fiap.tech.challenge.erp_restaurant.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    // Busca todos os endereços de um usuário
    List<AddressEntity> findByUser_Id(Long userId);

    // Busca um endereço por ID e ID de usuário
    Optional<AddressEntity> findByIdAndUser_Id(Long addressId, Long userId);

    // Verifica se um endereço existe com ID e ID de usuário
    boolean existsByIdAndUser_Id(Long addressId, Long userId);
}