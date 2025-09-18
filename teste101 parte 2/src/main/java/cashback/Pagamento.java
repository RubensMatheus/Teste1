package cashback;

import java.time.LocalDate;

public class Pagamento {
	float valor;
	TipoPagamento tipo;
	LocalDate data;
	
	public Pagamento(float valor, TipoPagamento tipo, LocalDate data) {
		this.valor = valor;
		this.tipo = tipo;
		this.data = data;
	}
}
