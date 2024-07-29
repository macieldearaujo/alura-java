package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.entities.Episodio;
import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private ConsumoApi consumo = new ConsumoApi();

    public void exibirMenu() {
        System.out.println("Digite o nome da série:");
        String nomeSerie = sc.nextLine();

        String nomeSerieUrl = nomeSerie.replace(" ", "+");

        String json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        ConverteDados conversor = new ConverteDados();
        DadosSerie serie = conversor.obterDados(json, DadosSerie.class);

        List<DadosTemporada> temporadas = new ArrayList<>();
		for(int i = 1; i <= serie.totalTemporadas(); i++) {
			json = consumo.obterDados("https://www.omdbapi.com/?t=" + nomeSerieUrl +"&season=" + i + "&apikey=6585022c");
			DadosTemporada dadosTemporadas = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporadas);
		}

		temporadas.forEach(System.out::println);

//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.title())));

        System.out.println("Top 5 episódios");
        List<DadosEpisodio> dadosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream()).toList();
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(10)
                .map(e -> e.title().toUpperCase())
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numeroTemporada(), d))).toList();

        episodios.forEach(System.out::println);

//        System.out.println("A partir de qual ano você deseja ver os episódios?");
//        int anoSelecionado = sc.nextInt();
//        LocalDate dataSelecionada = LocalDate.of(anoSelecionado, 1, 1);
//
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        List<Episodio> episodiosFiltrados = episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataSelecionada)).toList();
//        episodiosFiltrados.forEach(e -> System.out.println(
//                "Temporada: " + e.getNumeroTemporada() + " - Título: " + e.getTitulo() + ", Data de lançamento: " +  df.format(e.getDataLancamento())
//        ));

//        System.out.println("Digite um trecho do título do episódio:");
//        String trecho = sc.nextLine();
//
//        Optional<Episodio> episodioFiltrado = episodios.stream().filter(e -> e.getTitulo().toUpperCase().contains(trecho.toUpperCase()))
//                .findFirst();
//
//        if(episodioFiltrado.isEmpty()) {
//            System.out.println("Episódio não encontrado");
//        } else {
//            System.out.println("Episódio encontrado: \nTítulo: do episódio: "
//                    + episodioFiltrado.get().getTitulo()
//                    + " - Temporada: " + episodioFiltrado.get().getNumeroTemporada());
//        }

        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                .filter(t -> t.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getNumeroTemporada
                        ,Collectors.averagingDouble(Episodio :: getAvaliacao)));

//        System.out.println(avaliacaoPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor avaliação: " + est.getMax());
        System.out.println("Pior avaliação: " + est.getMin());

//        List<String> nomesDosEpisodios = new ArrayList<>();
//        for(DadosTemporada temp : temporadas) {
//            for(DadosEpisodio ep : temp.episodios()) {
//                nomesDosEpisodios.add(ep.title());
//            }
//        }
//
//        nomesDosEpisodios.forEach(System.out::println);

    }
}
