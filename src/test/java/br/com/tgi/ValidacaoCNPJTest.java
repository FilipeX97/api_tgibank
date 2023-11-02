package br.com.tgi;

import org.junit.jupiter.api.Test;

import br.com.tgi.validation.ValidacaoCNPJ;

import static org.junit.jupiter.api.Assertions.*;

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