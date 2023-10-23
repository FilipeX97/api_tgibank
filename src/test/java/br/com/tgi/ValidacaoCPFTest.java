package br.com.tgi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.tgi.validation.ValidacaoCPF;

public class ValidacaoCPFTest {

    @Test
    public void testValidarCPFValido() {
        String cpfValido = "13607461295";
        assertTrue(ValidacaoCPF.validar(cpfValido));
    }

    @Test
    public void testValidarCPFIvalido() {
        String cpfInvalido = "12345678900";
        assertFalse(ValidacaoCPF.validar(cpfInvalido));
    }
}
