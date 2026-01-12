package com.vr.repository;

import com.vr.domain.Cartao;
import com.vr.dto.cartao.CartaoDTORequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartaoRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    CartaoRepository cartaoRepository;

    @Test
    @DisplayName("Should get Cartao successfully from DB")
    void findByNumeroCartaoSucess() {
        String numeroCartao = "45387590342";

        CartaoDTORequest dto = new CartaoDTORequest(2335, numeroCartao);
        this.createCartao(dto);

        Optional<Cartao> cartao = this.cartaoRepository.findByNumeroCartao(numeroCartao);

        assertThat(cartao.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Cartao successfully from DB")
    void findByNumeroCartaoError() {
        String numeroCartao = "45387590342";

        Optional<Cartao> cartao = this.cartaoRepository.findByNumeroCartao(numeroCartao);

        assertThat(cartao.isPresent()).isFalse();
    }

    private  void createCartao(CartaoDTORequest dto) {
        Cartao cartao = new Cartao(dto);
        this.entityManager.persist(cartao);
    }
}