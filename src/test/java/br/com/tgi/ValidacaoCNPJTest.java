package br.com.tgi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.tgi.validation.ValidacaoCNPJ;

public class ValidacaoCNPJTest {

    @Test
    public void testCNPJValido() {
        assertTrue(ValidacaoCNPJ.validar("14.686.262/0001-66"));
    }

    @Test
    public void testCNPJInvalido() {
        assertFalse(ValidacaoCNPJ.validar("11.111.111/1111-11"));
    }
}