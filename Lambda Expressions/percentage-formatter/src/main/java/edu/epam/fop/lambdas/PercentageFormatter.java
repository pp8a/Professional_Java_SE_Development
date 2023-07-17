package edu.epam.fop.lambdas;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.DoubleFunction;

public interface PercentageFormatter {

  // TODO write you code here
//  DoubleFunction<String> INSTANCE = value -> String.format("%s %%", Math.round(value * 100.0));
	DecimalFormat decimalFormat = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.US));		
	DoubleFunction<String> INSTANCE = value -> decimalFormat.format(value *100) + " %";
}
