package gestaocomercial.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gestaocomercial.dominio.enuns.FormaPagamento;
import gestaocomercial.utilitarios.Utilitario;



@Entity
@Table(name = "vendas")
public class Venda implements Serializable, Utilitario {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Cliente cliente;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "venda_item",
		joinColumns = @JoinColumn(name = "venda_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id")
	)
	private List<Item> itemList = new ArrayList<>();

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
	
	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public void adicionarItem(Item item) {
		itemList.add(item);
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
	
	public String getDataVendaFormatada() {
		return dataVenda.format(DIA_MES_ANO_FORMATTER);
	}
	
	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public Double getValorVenda() {
		return valorVenda == null ? 0 : valorVenda;
	}

	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}

	public void setVendas(Cliente cliente, List<Item> itemList, FormaPagamento formaPagamento) {
		setCliente(cliente);
		setItemList(itemList);
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
