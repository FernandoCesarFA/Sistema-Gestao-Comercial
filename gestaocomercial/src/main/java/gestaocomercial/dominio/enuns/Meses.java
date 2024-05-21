package gestaocomercial.dominio.enuns;

public enum Meses {
	JANEIRO("Faneiro", 1),
	FEVEREITO("Fevereiro", 2),
	MARCO("Março", 3),
	ABRIL("Abril", 4),
	MAIO("Maio", 5),
	JUNHO("Junho", 6),
	JULHO("Julho", 7),
	AGOSTO("Agosto", 8),
	SETEMBRO("Setembro", 9),
	OUTUBRO("Outubro", 10),
	NOVEMBRO("Novembro", 11),
	DEZEMBRO("Dezembro", 12);
	
	private String abreviacao;
	int valor;
	
	private Meses(String abreviacao, int valor) {
		this.abreviacao = abreviacao;
		this.valor = valor;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public static String[] getAbreviacoes() {
	    Meses[] meses = Meses.values();
	    String[] abreviacoes = new String[meses.length + 1]; 

	    for (int i = 0; i < meses.length; i++) {
	        abreviacoes[i] = meses[i].getAbreviacao();
	    }

	    abreviacoes[meses.length] = "Todos"; 

	    return abreviacoes;
	}

	
	/**
	 * Retorna o valor correspondente a uma abreviação do enum Meses.
	 *
	 * @param abreviacao A abreviação a ser pesquisada.
	 * @return O valor correspondente à abreviação, ou 0 se a abreviação não for encontrada.
	 */
	public static int obterValorPorAbreviacao(String abreviacao) {
	    for (Meses mes : Meses.values()) {
	        if (mes.getAbreviacao().equalsIgnoreCase(abreviacao)) {
	            return mes.getValor();
	        }
	    }
	    return 0;
	}
}
