/*Lucas de Lyra Monteiro DRE 121039714*/

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.List;

public class AnalisaTexto {

    private String texto;
    public static final String PONTUACAO = "[!\\\"#$%&'()*+,-./:;<=>?@\\\\[\\\\]^_`{|}~]";

    public AnalisaTexto(String caminhoarq) {
        try {
            FileReader leitorarq = new FileReader(caminhoarq);
            BufferedReader leitorbuf = new BufferedReader(leitorarq);
            String linha = leitorbuf.readLine();
            String texto_nao_tratado = "";
            while (linha != null) {
                texto_nao_tratado += linha + " ";
                linha = leitorbuf.readLine();
            }
            this.texto = this.tratarTexto(texto_nao_tratado);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Arquivo não pode ser lido!");
        }
    }

    private String tratarTexto(String texto_nao_tratado) {
        String texto_tratado = texto_nao_tratado.toUpperCase();
        texto_tratado = texto_tratado.replaceAll(AnalisaTexto.PONTUACAO,"");
        return texto_tratado;
    }

    public String getTexto() {
        return texto;
    }

    public ArrayList<String> quebrarTextoEmPalavras() {
        String[] arr = this.texto.split("\\s+");
        ArrayList<String> palavras = new ArrayList<>(List.of(arr));
        return palavras;
    }

    public String getTextoInvertido() {
        ArrayList<String> palavras = this.quebrarTextoEmPalavras();
        String texto_invertido = "";
        for(int i = palavras.size()-1; i >= 0; --i) {
            texto_invertido += palavras.get(i) + " ";
        }
        return texto_invertido;
    }

    public Set<String> palavrasSemRepeticao() {
        Set<String> conjunto_palavras = new HashSet<>();
        ArrayList<String> palavras = this.quebrarTextoEmPalavras();
        for (int i = 0;i < palavras.size(); ++i) {
            conjunto_palavras.add(palavras.get(i));
        }
        return conjunto_palavras;
    }

    public void imprimePalavrasSemRepeticao() {
        Set<String> conjunto_palavras = this.palavrasSemRepeticao();
        for (String palavra : conjunto_palavras) {
            System.out.println(palavra);
        }
    }

    public Map<String, Integer> computarFrequenciaDasPalavras() {
        Map<String, Integer> frequencia_palavras = new HashMap<>();
        ArrayList<String> palavras = this.quebrarTextoEmPalavras();
        for (String palavra : palavras) {
            if (frequencia_palavras.containsKey(palavra)) {
                Integer freq = frequencia_palavras.get(palavra);
                freq += 1;
                frequencia_palavras.put(palavra,freq);
            } else {
                frequencia_palavras.put(palavra,1);
            }
        }
        return frequencia_palavras;
    }

    public Map<String, Integer> computarFrequenciaDasPalavras(boolean remover_stop_words){
        Map<String, Integer> frequencia_palavras  = computarFrequenciaDasPalavras();

        if (!remover_stop_words){
            return frequencia_palavras;
        }

        Map<String, Integer>copia_frequencia_palavras = new HashMap<>();
        AnalisaTexto stop_words_archive = new AnalisaTexto("src\\stop-words.txt");
        Set<String> stop_words = stop_words_archive.palavrasSemRepeticao();
        Set<String> stop_words_copy = new HashSet<>();

        /*Era melhor ter criado um metodo pros 2 for abaixo, eu acho*/
        for(String palavra: frequencia_palavras.keySet()) {
            String palavra_sem_acento = Normalizer.normalize(palavra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            copia_frequencia_palavras.put(palavra_sem_acento, frequencia_palavras.get(palavra));
        }

        for (String word: stop_words){
            String word_without_diacritic = Normalizer.normalize(word, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            stop_words_copy.add(word_without_diacritic);
        }

        Iterator<Map.Entry<String, Integer>> it = copia_frequencia_palavras.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Integer> entry = it.next();
            if (stop_words_copy.contains(entry.getKey())){
                it.remove();
            }
        }

        return copia_frequencia_palavras;
    }

    public void imprimePalavraMaisFrequente() {
        Map<String, Integer> frequencia_palavras = this.computarFrequenciaDasPalavras();
        int freq_max = 0;
        String palavra_mais_freq = "";
        for (String palavra: frequencia_palavras.keySet()) {
            int freq = frequencia_palavras.get(palavra);
            if (freq > freq_max) {
                freq_max = freq;
                palavra_mais_freq = palavra;
            }
        }
        System.out.println("A palavra " + palavra_mais_freq + " é a mais frequente.");
        System.out.print("Sua frequência é de: ");
        System.out.println(freq_max);
    }
}
