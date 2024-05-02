package gestaocomercial.aplicativo;

import java.util.Locale;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Produto;

public class Programa {
	
	public static void main(String[] args) {
		gestaoComercial();
	}//main()

	private static void gestaoComercial() {
		Locale.setDefault(new Locale("pt", "BR"));
		
		DAO<Produto> produtoDao = new DAO<>(Produto.class);
		
		Produto produto = produtoDao.buscaPorId(1l);
		
		System.out.println("Opa");
		
		System.out.println(produto.getId() + " " + produto.getNomeProduto() + " " + produto.getPreco());
		
		try {
			produtoDao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//gestaoComercial()
}//Programa()
