package br.ufrn.imd;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Matricula {
	private final Discente discente;
	
	private final Turma turma;
	
	private BigDecimal nota1;

	private BigDecimal nota2;

	private BigDecimal nota3;

	private Integer frequencia;
	
	private StatusAprovacao status;

	Matricula(Discente discente, Turma turma) {
		this.discente = discente;
		this.turma = turma;
	}

	public BigDecimal getNota1() {
		return nota1;
	}

	public void cadastrarNota1(BigDecimal nota1) {
		this.nota1 = validarNota(nota1);
	}

	public BigDecimal getNota2() {
		return nota2;
	}

	public void cadastrarNota2(BigDecimal nota2) {
		this.nota2 = validarNota(nota2);
	}

	public BigDecimal getNota3() { return nota3; }

	public void cadastrarNota3(BigDecimal nota3) {
		this.nota3 = validarNota(nota3);
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void cadastrarFrequencia(Integer frequencia) {
		if (frequencia == null || frequencia < 0 || frequencia > 100) {
			throw new IllegalArgumentException("Frequência deve estar entre 0 e 100");
		}
		this.frequencia = frequencia;
	}

	private BigDecimal validarNota(BigDecimal nota) {
		if (nota == null) {
			throw new IllegalArgumentException("Nota não pode ser nula");
		}
		if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(BigDecimal.TEN) > 0) {
			throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
		}
		return nota.setScale(2, RoundingMode.HALF_UP);
	}

	public Discente getDiscente() {
		return discente;
	}


	public Turma getTurma() {
		return turma;
	}

	public void consolidarParcialmente() {
		// TODO Implementar aqui a lógica de consolidação parcial
		// this.setStatus(StatusAprovacao.APR);
		garantirNotasEIndiceCadastrados(true); // exige tudo

		BigDecimal media = calcularMediaParcial();
		boolean freqOk = frequencia >= 75;
		boolean nenhumaNotaAbaixoDeQuatro =
			nota1.compareTo(new BigDecimal("4.00")) >= 0 &&
			nota2.compareTo(new BigDecimal("4.00")) >= 0 &&
			nota3.compareTo(new BigDecimal("4.00")) >= 0;

		if (freqOk && media.compareTo(new BigDecimal("6.00")) >= 0 && nenhumaNotaAbaixoDeQuatro) {
			setStatus(StatusAprovacao.APR);
			return;
		}

		boolean mediaMenorQue3 = media.compareTo(new BigDecimal("3.00")) < 0;

		if (!freqOk && mediaMenorQue3) {
			setStatus(StatusAprovacao.REPMF); // reprovado por média e faltas
		} else if (!freqOk /* && media >= 3.0 */) {
			setStatus(StatusAprovacao.REPF);  // reprovado por faltas
		} else if (freqOk && mediaMenorQue3) {
			setStatus(StatusAprovacao.REP);   // reprovado por média
		} else {
			setStatus(StatusAprovacao.REC); // recuperação
		}
	}


	public StatusAprovacao getStatus() {
		return status;
	}

	private void setStatus(StatusAprovacao status) {
		this.status = status;
	}

	private void garantirNotasEIndiceCadastrados(boolean exigirFrequencia) {
		if (nota1 == null || nota2 == null || nota3 == null) {
			throw new IllegalStateException("Notas 1, 2 e 3 devem estar cadastradas");
		}
		if (exigirFrequencia && frequencia == null) {
			throw new IllegalStateException("Frequência deve estar cadastrada");
		}
	}

	public BigDecimal calcularMediaParcial() {
		BigDecimal soma = nota1.add(nota2).add(nota3);
		return soma.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
	}
}