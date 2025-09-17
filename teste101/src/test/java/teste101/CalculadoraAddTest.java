package teste101;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes da Calculadora (Adição)")
public class CalculadoraAddTest {

    private Calculadora calculadora;

    // O método com @BeforeEach é executado ANTES de cada @Test.
    // Garante que a calculadora é "nova" para cada teste.
    @BeforeEach
    void setUp() {
        calculadora = new Calculadora(OperationType.ADD);
    }

    @Test
    @DisplayName("Deve somar dois números positivos")
    void deveSomarDoisNumeros() {
        assertEquals(8, calculadora.avaliar("5+3"));
    }

    @Test
    @DisplayName("Deve somar múltiplos números")
    void deveSomarMultiplosNumeros() {
        assertEquals(20, calculadora.avaliar("10+5+5"));
    }

    @Test
    @DisplayName("Deve retornar 0 para uma expressão vazia")
    void deveRetornarZeroParaExpressaoVazia() {
        assertEquals(0, calculadora.avaliar(""));
    }
}