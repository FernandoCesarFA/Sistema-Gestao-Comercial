package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;

import gestaocomercial.dominio.Item;
import gestaocomercial.dominio.Produto;
import gestaocomercial.dominio.Venda;

public class IgGraficoBarras {

    public static JPanel gerarGraficoBarras(List<Venda> vendaList, int filtro) {
        if (vendaList.isEmpty()) {
            final JPanel contentPanel = new JPanel();
            contentPanel.setBackground(new Color(255, 255, 255));
            contentPanel.setBounds(100, 100, 450, 300);
            contentPanel.setLayout(new BorderLayout());

            JTextPane textPane = new JTextPane();
            textPane.setText("Para exibir o gráfico, realize uma venda.");
            textPane.setFont(new Font("SansSerif", Font.PLAIN, 16));
            textPane.setEditable(false);
            textPane.setOpaque(false);

            StyledDocument doc = textPane.getStyledDocument();
            SimpleAttributeSet centerAlignment = new SimpleAttributeSet();
            StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), centerAlignment, false);

            contentPanel.add(textPane, BorderLayout.CENTER);

            JLabel imagemLabel = new JLabel("");
            imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imagemLabel.setIcon(new ImageIcon(IgGraficoBarras.class.getResource("/gestaocomercial/gui/imagens/comprasIcon.png")));

            contentPanel.add(imagemLabel, BorderLayout.NORTH);

            return contentPanel;
        }

        List<Produto> produtoList = new ArrayList<>();
        String titulo = "Produtos";

        switch (filtro) {
            case 0: {
                produtoList = produtosMaisVendidos(vendaList);
                titulo = "Mais Vendidos";
                break;
            }
            case 1: {
                produtoList = produtosMaisVendidos(vendaList);
                Collections.reverse(produtoList);
                titulo = "Menos Vendidos";
                break;
            }
            case 2: {
                produtoList = produtosQuantidadeEstoque(vendaList);
                titulo = "Maior Estoque";
                break;
            }
            case 3: {
                produtoList = produtosQuantidadeEstoque(vendaList);
                Collections.reverse(produtoList);
                titulo = "Menor Estoque";
                break;
            }
            default:
                break;
        }

        // Criação do conjunto de dados
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int numProdutos = Math.min(8, produtoList.size()); // Limitar a 8 produtos no gráfico
        for (int i = 0; i < numProdutos; i++) {
            Produto produto = produtoList.get(i);
            int quantidade = 0;
            if (filtro == 2 || filtro == 3) {
            	quantidade = produto.getQuantidadeEstoque();
            }
            else {
            	quantidade = calcularQuantidadeTotalProduto(produto, vendaList);
            }
            dataset.addValue(quantidade, produto.getNomeProduto(), "Vendas");
        }

        Locale.setDefault(Locale.US);

        // Criação do gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart("", // Título do gráfico
                titulo, // Rótulo do eixo x
                "Quantidade", // Rótulo do eixo y
                dataset, // Dados
                PlotOrientation.VERTICAL, true, false, false);

        // Personalização do estilo das barras
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());

        // Definir cores diferentes para cada série (produto)
        for (int i = 0; i < numProdutos; i++) {
            Color cor = obterCorProduto(i); // Método para obter cores diferentes
            renderer.setSeriesPaint(i, cor);
        }

        // Definir largura máxima para as barras
        double maxBarWidth = .1;
        double barMargin = (1.0 - maxBarWidth) / 2.0;
        renderer.setMaximumBarWidth(maxBarWidth);
        renderer.setItemMargin(barMargin);

        // Personalização do fundo e das linhas do gráfico
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Ajustar posição das legendas
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);

        // Personalização do rótulo do eixo y com formato personalizado (opcional)
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        double maxValue = rangeAxis.getUpperBound(); // Valor máximo atual do eixo y
        rangeAxis.setRange(0, maxValue + 2); // Ajuste da escala com 10 unidades a mais

        // Personalização dos rótulos dos valores acima das barras com formato personalizado
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);

        // Criação do painel do gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        // Desativar funcionalidade de zoom
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);

        return chartPanel;
    }

    // Método para calcular a quantidade total vendida de um produto em todas as vendas
    private static int calcularQuantidadeTotalProduto(Produto produto, List<Venda> vendaList) {
        int quantidadeTotal = 0;
        for (Venda venda : vendaList) {
            for (Item item : venda.getItemList()) {
                if (item.getProduto().equals(produto)) {
                    quantidadeTotal += item.getQuantidade();
                }
            }
        }
        return quantidadeTotal;
    }

    // Método para obter cores diferentes para cada série (produto)
    private static Color obterCorProduto(int indice) {
        switch (indice) {
            case 0:
                return new Color(65, 105, 225);
            case 1:
                return new Color(128, 0, 128);
            case 2:
                return new Color(70, 130, 180);
            case 3:
                return new Color(255, 165, 0);
            case 4:
                return new Color(220, 20, 60);
            case 5:
                return new Color(255, 192, 203);
            case 6:
                return new Color(255, 165, 0);
            case 7:
                return new Color(150, 75, 0);
            default:
                return Color.BLACK; // Caso geral
        }
    }

    public static List<Produto> produtosMaisVendidos(List<Venda> vendaList) {
        // Map para armazenar a quantidade total vendida de cada produto
        Map<Produto, Integer> quantidadePorProduto = new HashMap<>();

        // Percorre todas as vendas para calcular a quantidade total vendida de cada produto
        for (Venda venda : vendaList) {
            for (Item item : venda.getItemList()) {
                Produto produto = item.getProduto();
                int quantidade = item.getQuantidade();
                // Atualiza a quantidade total vendida do produto
                quantidadePorProduto.put(produto, quantidadePorProduto.getOrDefault(produto, 0) + quantidade);
            }
        }

        // Converte o Map para uma lista de Map.Entry para facilitar a ordenação
        List<Map.Entry<Produto, Integer>> listaOrdenada = new ArrayList<>(quantidadePorProduto.entrySet());

        // Ordena a lista com base na quantidade vendida, em ordem decrescente
        listaOrdenada.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Cria uma lista de produtos ordenados pela quantidade vendida
        List<Produto> produtosOrdenados = new ArrayList<>();
        for (Map.Entry<Produto, Integer> entry : listaOrdenada) {
            produtosOrdenados.add(entry.getKey());
        }

        return produtosOrdenados;
    }

    public static List<Produto> produtosQuantidadeEstoque(List<Venda> vendaList) {
        List<Produto> produtoList = vendaList.stream()
                .flatMap(v -> v.getItemList().stream())
                .map(Item::getProduto)
                .distinct()
                .collect(Collectors.toList());

        produtoList.sort((p1, p2) -> p2.getQuantidadeEstoque().compareTo(p1.getQuantidadeEstoque()));
        return produtoList;
    }
}
