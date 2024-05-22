package gestaocomercial.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Item {
	@Id
	@SequenceGenerator(name="item_id", sequenceName="item_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="item_id")
	private Long id;
	
	@ManyToOne
	private Produto produto;
	
	private Integer quantidade;
	private Float valorUnitario;
	private Float valorTotal;
	private boolean atendido = false;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Float getValorTotal() {
		valorTotal = (Float) (produto.getPreco() * quantidade); 
		return  valorTotal;
	}
	
	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	
	}

	public boolean isAtendido() {
		return atendido;
	}

	public void setAtendido(boolean atendido) {
		this.atendido = atendido;
	}

	public Float getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Float valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
}//Item
