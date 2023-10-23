package br.com.tgi;

import org.junit.jupiter.api.Test;

import br.com.tgi.validation.ValidacaoCNPJ;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacaoCNPJTest {

    @Test
    public void testCNPJValido() {
        assertTrue(ValidacaoCNPJ.validar("12.345.678/0001-90"));
    }

    @Test
    public void testCNPJInvalido() {
        assertFalse(ValidacaoCNPJ.validar("11.111.111/1111-11"));
    }
}