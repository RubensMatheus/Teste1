package teste101;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculadoraTest {
	
	@BeforeEach
	public void setUpBefore() {
		
	}
	
	@BeforeAll
	public static void setUp() {
		
	}
	
	@AfterEach
	public void tearDown() {
		
	}
	
	@AfterAll
	public static void tearDownAll() {
		
	}

	@ParameterizedTest(name="{0}-{1} {2}")
	@CsvSource(value = { //
			"10 20 +, 30, nome a ser exibido 1", //
			"1 2 *, 2, nome a ser exibido 2", //
			"1 2 * 3 +, 5,nome a ser exibido 3", //
			"1 2 -, -1, nome a ser exibido 4", //
			"1 2 /, 0, nome a ser exibido 5", //
			"1 2 + 3 - 2 *, 0, nome a ser exibido 6"
	})
	public void testeParametrizado(String entrada, Integer valorEsperado, String nomeExibido) {
		// Arrange
		CalculadoraPosFixa calculadora = new CalculadoraPosFixa();

		// Act
		int valorRetornado = calculadora.avaliar(entrada);

		// Assert
		assertEquals(valorEsperado, valorRetornado);
	}

	@Test
	public void testeDeveLancarExcecaoComExpressaoMalFormada() { 
		// Arrange
		CalculadoraPosFixa calculadora = new CalculadoraPosFixa();
		String entrada = "1 A *";
		
		/*
		 * Quando eu invocar o método avaliar com esta entrada,
		 * o comportamento esperado é lançar uma exceção do tipo
		 * IllegalArgumentException
		*/
		
		assertThrowsExactly(NumberFormatException.class,//
				() -> {calculadora.avaliar(entrada);});
	}
//	
//	@Test
//	public void testeDeveMultiplicarExpressaoCompostaCorretamente() { 
//		// Padrão triple-A
//		
//		// Arrange
//		CalculadoraPosFixa calculadora = new CalculadoraPosFixa();
//		String entrada = "1 2 * 3 *";
//		int valorEsperado = 6;
//		
//		// Act
//		int valorRetornado = calculadora.avaliar(entrada);
//		
//		// Assert
//		assertEquals(valorEsperado, valorRetornado);
//	}
//	
//	@Test
//	public void testeDeveSomarEMultiplicarCorretamente() { 
//		// Padrão triple-A
//		
//		// Arrange
//		CalculadoraPosFixa calculadora = new CalculadoraPosFixa();
//		String entrada = "1 2 * 3 +";
//		int valorEsperado = 5;
//		
//		// Act
//		int valorRetornado = calculadora.avaliar(entrada);
//		
//		// Assert
//		assertEquals(valorEsperado, valorRetornado);
//	}
//	
//	@Test
//	public void testeDeveSomarExpressaoSimplesCorretamente() { 
//		// Padrão triple-A
//		
//		// Arrange
//		CalculadoraPosFixa calculadora = new CalculadoraPosFixa();
//		String entrada = "1 2 +";
//		int valorEsperado = 3;
//		
//		// Act
//		int valorRetornado = calculadora.avaliar(entrada);
//		
//		// Assert
//		assertEquals(valorEsperado, valorRetornado);
//	}
//	
//	@Test
//	public void testeDeveSomarExpressaoCompostaCorretamente() { 
//		// Padrão triple-A
//		
//		// Arrange
//		CalculadoraPosFixa calculadora = new CalculadoraPosFixa();
//		String entrada = "1 2 + 3 +";
//		int valorEsperado = 6;
//		
//		// Act
//		int valorRetornado = calculadora.avaliar(entrada);
//		
//		// Assert
//		assertEquals(valorEsperado, valorRetornado);
//	}

}
