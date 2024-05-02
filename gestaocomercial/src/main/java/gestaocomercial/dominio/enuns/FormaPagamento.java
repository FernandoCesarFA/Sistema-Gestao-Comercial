package gestaocomercial.dominio.enuns;

/**
 * Enumeração que representa as formas de pagamento de uma despesa.
 */
public enum FormaPagamento {

    /**
     * Forma de pagamento: Cartão de Crédito.
     */
    CARTAO_DE_CREDITO("CC"),

    /**
     * Forma de pagamento: Cartão de Débito.
     */
    CARTAL_DE_DEBITO("CD"),

    /**
     * Forma de pagamento: Dinheiro.
     */
    DINHEIRO("Dinheiro"),

    /**
     * Forma de pagamento: PIX.
     */
    PIX("Pix");

    private String formaPagamento;

    private FormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * Obtém a forma de pagamento.
     *
     * @return A forma de pagamento.
     */
    public String getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * Converte uma string em uma instância do enum FormaPagamento.
     *
     * @param {@code String} A string representando a forma de pagamento.
     * @return Uma instância do enum {@code FormaPagamento} correspondente à string fornecida.
     *         Se a string não corresponder a nenhuma forma de pagamento conhecida, retorna null.
     */
    public static FormaPagamento converterStringParaFormaPagamento(String string) {
        for (FormaPagamento forma : FormaPagamento.values()) {
            if (forma.getFormaPagamento().equalsIgnoreCase(string)) {
                return forma;
            }
        }
        return null;
    }

    /**
     * Retorna a representação em formato de string da forma de pagamento.
     *
     * @return A forma de pagamento em formato de {@code String}.
     */
    @Override
    public String toString() {
        return getFormaPagamento();
    }
}//enum FormaPagamento
