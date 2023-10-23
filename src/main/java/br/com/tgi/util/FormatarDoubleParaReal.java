package br.com.tgi.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatarDoubleParaReal {
	
	public static String formatar(double valor) {
		NumberFormat formatoReal = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatoReal.format(valor);
	}

}
