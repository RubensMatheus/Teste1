package teste101;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

// @Suite habilita a execução de suítes no JUnit 5
@Suite
// @SuiteDisplayName define um nome legível para a suíte nos relatórios de teste
@SuiteDisplayName("Suíte de Testes para todas as operações da Calculadora")
// @SelectClasses especifica quais classes de teste fazem parte desta suíte
@SelectClasses({
        CalculadoraAddTest.class,
        CalculadoraMultTest.class
})
public class CalculadoraSuiteTest {
    // Esta classe pode ficar vazia.
    // Ela é usada apenas como um "container" para as anotações da suíte.
}
