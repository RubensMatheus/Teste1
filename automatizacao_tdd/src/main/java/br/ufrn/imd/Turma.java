package br.ufrn.imd;

import java.util.ArrayList;
import java.util.List;

public class Turma {
	private final Docente docente;

	private final Disciplina disciplina;

	private final List<Matricula> matriculas;

	public Turma(Docente docente, Disciplina disciplina) {
		this.docente = docente;
		this.disciplina = disciplina;
		this.matriculas = new ArrayList<>();
	}
	
	public boolean matricular(Discente discente) {
		Matricula matricula = new Matricula(discente, this);
		boolean matriculou = this.matriculas.add(matricula);
		return matriculou;
	}
	
	public void consolidarParcialmente() {
		for(Matricula m : matriculas) {
			m.consolidarParcialmente();
		}
	}

	public Docente getDocente() {
		return docente;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}
	
}
