package br.ufrn.imd;

public enum StatusAprovacao {
	REPF,//	Reprovado por Faltas	Reprovado(a) por não atender ao critério de assiduidade (frequência inferior a 75%).
	REP,//	Reprovado por Média	Reprovado(a) por obter média inferior a 3,0 (neste caso, sem direito a reposição).
	REC,//	Em Recuperação	Aluno(a) que fará a reposição por obter média parcial entre 3,0 e 6,0, e que atende ao critério de assiduidade.
	APR,//	Aprovado por Média	Aprovado(a), sem reposição, por obter média igual ou superior a 6,0 e que não tirar nenhuma nota inferior a 4,0 antes da reposição.
	REPMF,//	Reprovado por Média e Faltas	Reprovado(a) por obter média inferior a 3,0 (sem direito a reposição) e também por não atender ao critério de assiduidade (frequência inferior a 75%).
}
