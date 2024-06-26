package gestaocomercial.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nomeProduto;
	private Integer quantidadeEstoque;
	private Double preco;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	
	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void subtraiQuantidadeEmEstoque(int quantidadeVendida) {
		this.quantidadeEstoque-= quantidadeVendida;
	}
	
	public void setProduto(String nomeProduto, Integer quantidadeEstoque, Integer quantidadeVendida, Double preco) {
		setNomeProduto(nomeProduto);
		setQuantidadeEstoque(quantidadeEstoque);
		setPreco(preco);
	}
	
	public boolean isProduto(String nomeProduto) {
		return this.nomeProduto.equals(nomeProduto) ? true : false;
	}
	
}//Produto
