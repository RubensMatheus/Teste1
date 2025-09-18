package cashback;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

record CashbackGanho(float valor, LocalDate data) {}

public class ContaCashback {

	private TipoPerfil perfil;
	private float saldo;
    // Lista para manter o histórico de todos os cashbacks creditados.
    private final List<CashbackGanho> historicoCashback;

	public ContaCashback(float saldoInicial, TipoPerfil perfil) {
		this.saldo = saldoInicial;
		this.perfil = perfil;
        this.historicoCashback = new ArrayList<>();
    }

    public void registrarPagamento(Pagamento pagamento) {
        // 1. Calcula o percentual de cashback da mesma forma que antes
        float percentual = calcularPercentual(pagamento.tipo);

        // 2. Calcula o cashback potencial para ESTA transação, com limite de R$ 12 por transação.
        float cashbackPotencial = Math.min(pagamento.valor * percentual, 12.0f);

        // 3. Calcula o total de cashback ganho nos últimos 7 dias
        LocalDate dataLimite = pagamento.data.minusDays(6);
        float cashbackNosUltimos7Dias = (float) historicoCashback.stream()
                .filter(c -> !c.data().isBefore(dataLimite)) // Filtra cashbacks na janela de 7 dias
                .mapToDouble(CashbackGanho::valor)
                .sum();

        // 4. Determina quanto cashback ainda pode ser ganho nesta semana
        float limiteDisponivelNaSemana = 12.0f - cashbackNosUltimos7Dias;

        // 5. O cashback a ser creditado é o menor valor entre o potencial e o disponível
        float cashbackACreditar = Math.max(0, Math.min(cashbackPotencial, limiteDisponivelNaSemana));

        // 6. Se houver cashback a creditar, atualiza o saldo e o histórico
        if (cashbackACreditar > 0) {
            this.saldo += cashbackACreditar;
            this.historicoCashback.add(new CashbackGanho(cashbackACreditar, pagamento.data));
        }
    }

    // Método auxiliar para manter o código mais limpo
    private float calcularPercentual(TipoPagamento formaPagamento) {
        float percentual = 0.0f;
        if (TipoPagamento.CARTAO_CREDITO.equals(formaPagamento)) {
            if (TipoPerfil.ESSENCIAL.equals(this.perfil)) percentual = 0.01f;
            else if (TipoPerfil.ADITIVADO.equals(this.perfil)) percentual = 0.02f;
            else if (TipoPerfil.PREMIUM.equals(this.perfil)) percentual = 0.06f;
        } else if (TipoPagamento.PIX.equals(formaPagamento)) {
            if (TipoPerfil.ESSENCIAL.equals(this.perfil)) percentual = 0.02f;
            else if (TipoPerfil.ADITIVADO.equals(this.perfil)) percentual = 0.04f;
            else if (TipoPerfil.PREMIUM.equals(this.perfil)) percentual = 0.06f;
        }
        return percentual;
    }

	/*public void calcularPerceregistrarPagamentontual(Pagamento pagamento) {
		TipoPagamento formaPagamento = pagamento.tipo;
		
		float percentual = 0.0f;

		if (TipoPagamento.CARTAO_CREDITO.equals(formaPagamento)) {
			if (TipoPerfil.ESSENCIAL.equals(this.perfil)) {
				percentual = 0.01f;
			} else if (TipoPerfil.ADITIVADO.equals(this.perfil)) {
				percentual = 0.02f;
			} else if (TipoPerfil.PREMIUM.equals(this.perfil)) {
				percentual = 0.06f;
			}
		} else if (TipoPagamento.PIX.equals(formaPagamento)) {
			if (TipoPerfil.ESSENCIAL.equals(this.perfil)) {
				percentual = 0.02f;
			} else if (TipoPerfil.ADITIVADO.equals(this.perfil)) {
				percentual = 0.04f;
			} else if (TipoPerfil.PREMIUM.equals(this.perfil)) {
				percentual = 0.06f;
			}
		}

		float cashback = pagamento.valor * percentual;
		if(cashback > 12.0f) {
			cashback = 12.0f;
		}

        if (saldo + cashback > 12.0f) {
            saldo = 12.0f;
        }else {
            this.saldo += cashback;
        }
	}*/

	public float consultarSaldo() {
		return this.saldo;
	}

}
