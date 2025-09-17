package teste101;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes da Calculadora (Multiplicação)")
public class CalculadoraMultTest {

    private Calculadora calculadora;

    // Este método também é executado antes de cada teste desta classe.
    @BeforeEach
    void setUp() {
        calculadora = new Calculadora(OperationType.MUlT);
    }

    @Test
    @DisplayName("Deve multiplicar dois números positivos")
    void deveMultiplicarDoisNumeros() {
        assertEquals(15, calculadora.avaliar("5*3"));
    }

    @Test
    @DisplayName("Deve multiplicar múltiplos números")
    void deveMultiplicarMultiplosNumeros() {
        assertEquals(100, calculadora.avaliar("10*2*5"));
    }

    @Test
    @DisplayName("Deve retornar 1 para uma expressão vazia")
    void deveRetornarUmParaExpressaoVazia() {
        assertEquals(1, calculadora.avaliar(""));
    }

    @Test
    @DisplayName("O resultado deve ser zero se um dos fatores for zero")
    void resultadoDeveSerZeroSeFatorForZero() {
        assertEquals(0, calculadora.avaliar("15*4*0*8"));
    }
}