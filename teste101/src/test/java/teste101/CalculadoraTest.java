//package teste101;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//
//class CalculadoraTest {
//
//	@Test
//    @DisplayName("Expressão simples deve ser avaliada corretamente")
//	void test01() {
//        //fail("Not yet implemented");
//
//        //configuração
//        Calculadora c = new Calculadora();
//
//        // Ação
//        int soma = c.avaliar("1+2+3");
//
//        // Verificação
//        assertEquals(6,soma);
//	}
//
//    @Test
//    @DisplayName("Expressões com espaços em branco")
//    void test02() {
//        Calculadora c = new Calculadora();
//        int soma = c.avaliar("1 + 2 + 3");
//        assertEquals(6,soma);
//    }
//
//    @Test
//    @DisplayName("Expressões com números negativos")
//    void test03() {
//        Calculadora c = new Calculadora();
//        int soma = c.avaliar("-1+5-2+3");
//        assertEquals(5,soma);
//    }
//
//    @Test
//    @DisplayName("Entrada inválida ou string vazia.")
//    void test04() {
//        Calculadora c = new Calculadora();
//        int soma = c.avaliar("");
//        assertEquals(0,soma);
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "0+1+2+3, 6",
//            "1+5+9, 15"
//    })
//    void deveAvaliarExpressoesParametrizadas(String expressao, int
//            resultadoEsperado) {
//        Calculadora c = new Calculadora();
//        assertEquals(resultadoEsperado, c.avaliar(expressao));
//    }
//
//
//    @Test
//    void deveLancarExcecaoSeExpressaoForNula() {
//        Calculadora c = new Calculadora();
//        assertThrows(NullPointerException.class, () -> {
//            c.avaliar(null);
//        });
//    }
//}
