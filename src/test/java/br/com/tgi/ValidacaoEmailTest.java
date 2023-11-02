package br.com.tgi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.tgi.validation.ValidacaoEmail;

public class ValidacaoEmailTest {

    @Test
    public void testEmailValido() {
        assertTrue(ValidacaoEmail.validar("usuario@example.com"));
        assertTrue(ValidacaoEmail.validar("meu.email@dominio.com.br"));
    }

    @Test
    public void testEmailInvalido() {
        assertFalse(ValidacaoEmail.validar("invalido@com"));
        assertFalse(ValidacaoEmail.validar("email@invalido"));
    }
}
