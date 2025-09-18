package teste101;

import java.util.Stack;

/**
 * 1 + 2 ==> 3 - Notação Infixa 1 + 2 * 3 ==> 7
 * 
 * Posfixa: 1 2 + 1 2 3 * +
 */
public class CalculadoraPosFixa {
	public int avaliar(String expressao) {
		String[] partes = expressao.split("\\ ");

		Stack<Integer> pilha = new Stack<>();
		for (String parte : partes) {
			if ("+".equals(parte)) {
				Integer operando1 = pilha.pop();
				Integer operando2 = pilha.pop();
				Integer soma = operando1 + operando2;
				pilha.push(soma);
			} else if ("*".equals(parte)) {
				Integer operando1 = pilha.pop();
				Integer operando2 = pilha.pop();
				Integer produto = operando1 * operando2;
				pilha.push(produto);
			} else if ("-".equals(parte)) {
				Integer operando2 = pilha.pop();
				Integer operando1 = pilha.pop();
				Integer diferenca = operando1 - operando2;
				pilha.push(diferenca);
			} else if ("/".equals(parte)) {
				Integer operando2 = pilha.pop();
				Integer operando1 = pilha.pop();
				Integer div = operando1 / operando2;
				pilha.push(div);
			} else {
//				if (true) {
//					throw new Exception();
//				}
				int operando = Integer.parseInt(parte);
				pilha.push(operando);
			}
		}

		return pilha.pop();
	}
}
