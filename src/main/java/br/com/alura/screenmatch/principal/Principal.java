package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        System.out.println(serie);

        List<DadosTemporada> temporadas = new ArrayList<>();
		for(int i = 1; i <= serie.totalTemporadas(); i++) {
			json = consumo.obterDados("https://www.omdbapi.com/?t=" + nomeSerieUrl +"&season=" + i + "&apikey=6585022c");
			DadosTemporada dadosTemporadas = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporadas);
		}

		temporadas.forEach(System.out::println);
    }
}
