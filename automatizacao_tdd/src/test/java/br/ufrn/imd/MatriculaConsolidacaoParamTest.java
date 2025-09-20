package br.ufrn.imd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MatriculaConsolidacaoParamTest {

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

                    // Testes interessantes:
                    // média = 3.0 e freq = 75 => REC
                    "1.0;3.0;5.0;75;REC",
                    // média = 5.99... (aprox) e freq = 75 => REC
                    "6.0;6.0;5.0;75;REC",
                    // média = 6.0 mas uma nota = 4.0 => continua válido p/ APR (nota não <4.0)
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


    // --------------------------- Testes para lançamento de exceções ------------------------

    private Matricula m;

    @BeforeEach
    void setup() {
        Discente d = new Discente();
        d.setNome("Aluno X");
        d.setMatricula(456);

        Docente prof = new Docente();
        prof.setNome("Prof Y");
        prof.setSiape(111);

        Disciplina disc = new Disciplina();
        disc.setNome("IMD-TS");
        disc.setCodigo("TS-01");

        Turma t = new Turma(prof, disc);
        m = new Matricula(d, t);
    }

    @Test
    void deveLancarExcecaoQuandoNotaForMenorQueZero() {
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarNota1(new BigDecimal("-0.01")));
    }

    @Test
    void deveLancarExcecaoQuandoNotaForMaiorQueDez() {
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarNota2(new BigDecimal("10.01")));
    }

    @Test
    void deveLancarExcecaoQuandoNotaForNula() {
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarNota3(null));
    }

    @Test
    void deveLancarExcecaoQuandoFrequenciaForMenorQueZero() {
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarFrequencia(-1));
    }

    @Test
    void deveLancarExcecaoQuandoFrequenciaForMaiorQueCem() {
        assertThrows(IllegalArgumentException.class, () -> m.cadastrarFrequencia(101));
    }

    @Test
    void deveLancarExcecaoAoConsolidarSemNotasOuFrequencia() {
        // cadastra apenas parte dos dados para forçar IllegalStateException
        m.cadastrarNota1(new BigDecimal("5.0"));
        m.cadastrarNota2(new BigDecimal("5.0"));
        // falta nota3 e frequência
        assertThrows(IllegalStateException.class, () -> m.consolidarParcialmente());
    }
}
