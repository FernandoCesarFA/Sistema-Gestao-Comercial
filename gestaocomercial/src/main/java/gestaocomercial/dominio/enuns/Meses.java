package gestaocomercial.dominio.enuns;

public enum Meses {
	JANEIRO("janeiro", 1),
	FEVEREITO("fevereiro", 2),
	MARCO("março", 3),
	ABRIL("abril", 4),
	MAIO("maio", 5),
	JUNHO("junho", 6),
	JULHO("julho", 7),
	AGOSTO("agosto", 8),
	SETEMBRO("setembro", 9),
	OUTUBRO("outubro", 10),
	NOVEMBRO("novembro", 11),
	DEZEMBRO("dezembro", 12);
	
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
        String[] abreviacoes = new String[meses.length];

        for (int i = 0; i < meses.length; i++) {
            abreviacoes[i] = meses[i].getAbreviacao();
        }

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
