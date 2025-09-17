package teste101;

public class Calculadora {

    /*
    public int avaliar(String expressao) {
        int soma = 0;
        String expressaoSemEspacos = expressao.replace(" ", "");
        String expressaoTransformada = expressaoSemEspacos.replace("-", "+-");
        for (String parcela : expressaoTransformada.split("\\{operacap}")) {
            if (!parcela.isEmpty()) {
                soma += Integer.valueOf(parcela);
            }
        }
        return soma;
    }*/

    OperationType operation;

    public Calculadora(OperationType operation) {
        this.operation = operation;
    }

    public int avaliar(String expressao) {
        // Lida com o caso de a expressão ser nula ou vazia.
        if (expressao == null || expressao.isEmpty()) {
            // Retorna o elemento neutro de cada operação.
            return (operation == OperationType.ADD) ? 0 : 1;
        }

        String[] parcelas;
        int resultado;

        // O switch define o comportamento com base na operação
        switch (operation) {
            case ADD:
                parcelas = expressao.split("\\+");
                resultado = 0; // Elemento neutro da adição
                for (String parcela : parcelas) {
                    resultado += Integer.parseInt(parcela.strip());
                }
                break;

            case MUlT:
                parcelas = expressao.split("\\*"); // Delimitador para multiplicação
                resultado = 1; // Elemento neutro da multiplicação
                for (String parcela : parcelas) {
                    resultado *= Integer.parseInt(parcela.strip());
                }
                break;

            default:
                // Lança uma exceção se um tipo de operação não for suportado
                throw new UnsupportedOperationException("Operação não suportada: " + operation);
        }

        return resultado;
    }

}
