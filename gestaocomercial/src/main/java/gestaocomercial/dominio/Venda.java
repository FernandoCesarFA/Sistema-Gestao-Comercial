package gestaocomercial.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gestaocomercial.dominio.enuns.FormaPagamento;



@Entity
@Table(name = "vendas")
public class Venda {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Cliente cliente;
	@OneToMany
	private List<Produto> produtoList = new ArrayList<Produto>();
	private FormaPagamento formaPagamento;
	private LocalDate dataVenda;
	private Double valorVenda;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<Produto> getProdutoList() {
		return produtoList;
	}
	
	public void setProdutoList(List<Produto> produtoList) {
		this.produtoList = produtoList;
	}
	
	public void adicionarProduto(Produto produto) {
		produtoList.add(produto);
	}
	
	 /**
     * Obtém a forma de pagamento.
     *
     * @return a forma de pagamento
     */
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * Define a forma de pagamento.
     *
     * @param formaPagamento a forma de pagamento
     */
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * Define a forma de pagamento a partir de uma String.
     *
     * @param formaPagamento a forma de pagamento no formato de String
     * @throws IllegalArgumentException se a String fornecida for inválida
     */
    public void setFormaPagamento(String formaPagamento) throws IllegalArgumentException {
        FormaPagamento formaAux = FormaPagamento.converterStringParaFormaPagamento(formaPagamento);
        if (formaAux == null) {
            throw new IllegalArgumentException("Tipo de Pagamento Inválido");
        }
        this.formaPagamento = formaAux;
    }
    
	public LocalDate getDataVenda() {
		return dataVenda;
	}
	
	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}

	public void setVendas(Cliente cliente, List<Produto> produtoList, FormaPagamento formaPagamento) {
		setCliente(cliente);
		setProdutoList(produtoList);
		setFormaPagamento(formaPagamento);
	}
	
	 /**
     * Obtém o més do pagamento.
     *
     * @return o mes do pagamento
     */
    public String getMesVenda() {
        int mes = dataVenda.getMonthValue();
        return String.format("%,02d", mes);
    }
	
}//Venda
