package gestaocomercial.aplicativo;

import java.awt.Color;
import java.util.Locale;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Cliente;
import gestaocomercial.dominio.Item;
import gestaocomercial.dominio.Produto;
import gestaocomercial.dominio.Venda;
import gestaocomercial.gui.IgJanelaPrincipal;

public class Programa {

    public static void main(String[] args) {
        gestaoComercial();
    }

    private static void gestaoComercial() {
        try {
            configurarLocalidade();
            configurarLookAndFeelNimbus();
            personalizarScrollBarNimbus();
            aplicarScrollBarUIWindows();
            iniciarAplicacao();
        } catch (PersistenceException e) {
            exibirMensagemErro("Erro ao se conectar ao banco de dados", e);
        } catch (Exception e) {
            exibirMensagemErro("Erro inesperado", e);
        }
    }

    private static void configurarLocalidade() {
        Locale.setDefault(new Locale("pt", "BR"));
    }

    private static void configurarLookAndFeelNimbus() throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }

    private static void personalizarScrollBarNimbus() {
        UIManager.put("ScrollBar.thumb", new ColorUIResource(Color.GREEN));
        UIManager.put("ScrollBar.thumbDarkShadow", new ColorUIResource(Color.DARK_GRAY));
        UIManager.put("ScrollBar.thumbHighlight", new ColorUIResource(Color.LIGHT_GRAY));
        UIManager.put("ScrollBar.thumbShadow", new ColorUIResource(Color.GRAY));
        UIManager.put("ScrollBar.track", new ColorUIResource(Color.WHITE));
        UIManager.put("ScrollBar.trackHighlight", new ColorUIResource(Color.BLUE));
        UIManager.put("ScrollBar.width", 15); // Largura maior para maior visibilidade
    }

    private static void aplicarScrollBarUIWindows() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            ComponentUI windowsScrollBarUI = UIManager.getUI(new JScrollBar());

            UIManager.setLookAndFeel(new NimbusLookAndFeel());

            UIManager.put("ScrollBarUI", windowsScrollBarUI.getClass().getName());
        } catch (Exception e) {
            exibirMensagemErro("Erro ao configurar ScrollBarUI", e);
        }
    }

    private static void iniciarAplicacao() {
        new IgJanelaPrincipal(new DAO<>(Cliente.class), new DAO<>(Produto.class), new DAO<>(Venda.class), new DAO<>(Item.class));
    }

    private static void exibirMensagemErro(String mensagem, Exception e) {
        JOptionPane.showMessageDialog(null, mensagem, "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
