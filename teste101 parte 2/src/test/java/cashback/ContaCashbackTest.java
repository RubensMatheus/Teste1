package cashback;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ContaCashbackTest {

	@ParameterizedTest
	@CsvSource(value = {//
			"ESSENCIAL, 100.0f, CARTAO_CREDITO, 1.0f",//
			"ESSENCIAL, 100.0f, PIX, 2.0f",//
			"ESSENCIAL, 100.0f, VALE_PREMIA, 0.0f",//
			"ADITIVADO, 100.0f, CARTAO_CREDITO, 2.0f",//
			"ADITIVADO, 100.0f, PIX, 4.0f",//
			"ADITIVADO, 100.0f, VALE_PREMIA, 0.0f",//
			"PREMIUM, 100.0f, CARTAO_CREDITO, 6.0f",//
			"PREMIUM, 100.0f, PIX, 6.0f",//
			"PREMIUM, 100.0f, VALE_PREMIA, 0.0f",//
	})
	void testeContaEssencialCartaoCreditoRetorna1PorCento(TipoPerfil perfil,
                                                          float valorPagamento,
                                                          TipoPagamento formaPagamento,
                                                          Float saldoEsperado) {
		float saldoInicial = 0.0f;
		
		ContaCashback conta = new ContaCashback(saldoInicial, perfil);
		Pagamento pagamento = new Pagamento(valorPagamento, formaPagamento, LocalDate.now());
		conta.registrarPagamento(pagamento);
		
		float saldoRetornado = conta.consultarSaldo();
		
		assertEquals(saldoEsperado, saldoRetornado);
	}
	
	@ParameterizedTest
	@CsvSource(value = {//
			"ESSENCIAL, 1500.0f, CARTAO_CREDITO",//
			"ADITIVADO, 1500.0f, CARTAO_CREDITO",//
	})
	void testeLimiteDeDozePorTransacao(TipoPerfil perfil, float valorPagamento, TipoPagamento formaPagamento) {
		float saldoInicial = 0.0f;
		
		ContaCashback conta = new ContaCashback(saldoInicial, perfil);
		Pagamento pagamento = new Pagamento(valorPagamento, formaPagamento, LocalDate.now());
		conta.registrarPagamento(pagamento);
		
		float saldoRetornado = conta.consultarSaldo();
		
		assertEquals(12.0, saldoRetornado);
	}
	
	@Test
	void testeVariosPagamentos() {
		float saldoInicial = 0.0f;
		
		ContaCashback conta = new ContaCashback(saldoInicial, TipoPerfil.PREMIUM);
		LocalDate hoje = LocalDate.now();
		LocalDate ontem = hoje.minusDays(1);
		Pagamento pagamento1 = new Pagamento(1500.0f, TipoPagamento.CARTAO_CREDITO, ontem);
		Pagamento pagamento2 = new Pagamento(1500.0f, TipoPagamento.CARTAO_CREDITO, hoje);
		conta.registrarPagamento(pagamento1);
		conta.registrarPagamento(pagamento2);
		
		float saldoRetornado = conta.consultarSaldo();
		
		assertEquals(12.0, saldoRetornado);
	}

    @Test
    @DisplayName("Deve permitir novo cashback quando a janela de 7 dias é renovada")
    void devePermitirNovoCashbackAposJanelaDeSeteDias() {
        // Arrange
        ContaCashback conta = new ContaCashback(0.0f, TipoPerfil.PREMIUM);
        LocalDate dia1 = LocalDate.of(2025, 9, 17);
        LocalDate dia7 = LocalDate.of(2025, 9, 23);
        LocalDate dia8 = LocalDate.of(2025, 9, 24); // Exatamente 7 dias depois do dia1

        // Pagamento 1 (dia 1) gera R$ 12 de cashback e atinge o limite da semana.
        Pagamento pagamentoAtingeLimite = new Pagamento(200.0f, TipoPagamento.PIX, dia1);
        conta.registrarPagamento(pagamentoAtingeLimite);
        assertEquals(12.0f, conta.consultarSaldo());

        // Pagamento 2 (dia 7) ainda está na janela de 7 dias do primeiro pagamento. Cashback = 0.
        Pagamento pagamentoDentroJanela = new Pagamento(100.0f, TipoPagamento.PIX, dia7);
        conta.registrarPagamento(pagamentoDentroJanela);
        assertEquals(12.0f, conta.consultarSaldo()); // Saldo não deve mudar

        // Act
        // Pagamento 3 (dia 8) está FORA da janela de 7 dias. O limite é renovado.
        Pagamento pagamentoForaJanela = new Pagamento(100.0f, TipoPagamento.PIX, dia8);
        conta.registrarPagamento(pagamentoForaJanela);
        float saldoFinal = conta.consultarSaldo();

        // Assert
        // Saldo esperado: 12 (da semana anterior) + 6 (da nova semana) = 18.
        assertEquals(18.0f, saldoFinal);
    }
}
