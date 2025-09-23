package br.ufrn.imd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MatriculaTest {

    private Matricula novaMatricula() {
        Discente d = new Discente();
        d.setNome("Aluno Teste");
        d.setMatricula(123);

        Docente prof = new Docente();
        prof.setNome("Prof Teste");
        prof.setSiape(999);

        Disciplina disc = new Disciplina();
        disc.setNome("Teste de Software");
        disc.setCodigo("IMD-TS");

        Turma turma = new Turma(prof, disc);
        return new Matricula(d, turma);
    }

    /**
     * CSV: n1;n2;n3;freq;statusEsperado
     */
    @ParameterizedTest(name = "[{index}] n1={0} n2={1} n3={2} freq={3}% => {4}")
    @CsvSource(
            value = {
                    // APR: média >= 6.0, nenhuma nota < 4.0 e freq >= 75
                    "6.0;6.0;6.0;80;APR",
                    "7.0;6.0;8.0;75;APR",

                    // REC: freq >= 75 e média >= 3.0, mas não atende APR
                    // (a) média no intervalo [3, 6)
                    "5.0;6.0;5.0;80;REC",
                    // (b) média >= 6.0 porém alguma nota < 4.0
                    "9.0;9.0;3.9;90;REC",

                    // REP: freq >= 75 e média < 3.0
                    "2.5;2.0;2.5;80;REP",

                    // REPF: freq < 75 e média >= 3.0
                    "8.0;8.0;8.0;70;REPF",
                    "5.0;5.0;5.0;74;REPF",

                    // REPMF: freq < 75 e média < 3.0
                    "2.0;2.0;2.0;60;REPMF",

                    // Casos de borda / interessantes:
                    // média = 3.0 e freq = 75 => REC
                    "1.0;3.0;5.0;75;REC",
                    // média ≈ 5.67 e freq = 75 => REC
                    "6.0;6.0;5.0;75;REC",
                    // média = 6.0 e menor nota = 4.0 (não é < 4.0) => APR
                    "4.0;7.0;7.0;90;APR",
                    // média < 6.0 por pouco e freq ok => REC
                    "4.0;6.0;6.0;90;REC"
            },
            delimiter = ';'
    )
    void deveConsolidarParcialmente(String sN1, String sN2, String sN3, int freq, String statusEsperado) {
        Matricula m = novaMatricula();
        m.cadastrarNota1(new BigDecimal(sN1));
        m.cadastrarNota2(new BigDecimal(sN2));
        m.cadastrarNota3(new BigDecimal(sN3));
        m.cadastrarFrequencia(freq);

        m.consolidarParcialmente();

        assertEquals(StatusAprovacao.valueOf(statusEsperado), m.getStatus());
    }

    // --------------------------- Testes de exceção ---------------------------

    @Test
    void deveLancarExcecaoQuandoNotaForMenorQueZero() {
        Matricula m = novaMatricula();
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarNota1(new BigDecimal("-0.01")));
    }

    @Test
    void deveLancarExcecaoQuandoNotaForMaiorQueDez() {
        Matricula m = novaMatricula();
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarNota2(new BigDecimal("10.01")));
    }

    @Test
    void deveLancarExcecaoQuandoNotaForNula() {
        Matricula m = novaMatricula();
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarNota3(null));
    }

    @Test
    void deveLancarExcecaoQuandoFrequenciaForMenorQueZero() {
        Matricula m = novaMatricula();
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarFrequencia(-1));
    }

    @Test
    void deveLancarExcecaoQuandoFrequenciaForMaiorQueCem() {
        Matricula m = novaMatricula();
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarFrequencia(101));
    }

    @Test
    void deveLancarExcecaoAoConsolidarSemNotasOuFrequencia() {
        Matricula m = novaMatricula();
        // cadastra apenas parte dos dados para forçar IllegalStateException
        m.cadastrarNota1(new BigDecimal("5.0"));
        m.cadastrarNota2(new BigDecimal("5.0"));
        // falta nota3 e frequência
        assertThrows(IllegalStateException.class, m::consolidarParcialmente);
    }

    // --------------------------- Teste do calcularMediaParcial ---------------------------

    @Test
    void deveCalcularMediaParcialCorretamente() {
        Matricula m = novaMatricula();
        m.cadastrarNota1(new BigDecimal("6.0"));
        m.cadastrarNota2(new BigDecimal("5.5"));
        m.cadastrarNota3(new BigDecimal("8.0"));

        BigDecimal media = m.calcularMediaParcial();

        assertEquals(new BigDecimal("6.50"), media);
    }
}
