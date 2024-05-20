package gestaocomercial.aplicativo;

import java.util.Locale;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Cliente;
import gestaocomercial.dominio.Produto;
import gestaocomercial.dominio.Venda;
import gestaocomercial.gui.IgJanelaPrincipal;

public class Programa {
	
	public static void main(String[] args) {
		gestaoComercial();
	}//main()

	private static void gestaoComercial() {
		try {
			Locale.setDefault(new Locale("pt", "BR"));
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			new IgJanelaPrincipal(new DAO<>(Cliente.class), new DAO<> (Produto.class), new DAO<> (Venda.class));
		}
		catch (PersistenceException e) {
			JOptionPane.showMessageDialog(null, "Erro ao se conectar ao banco de dados", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro inesperado", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}//gestaoComercial()
}//Programa()
