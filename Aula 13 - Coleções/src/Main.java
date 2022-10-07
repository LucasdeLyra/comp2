/*Lucas de Lyra Monteiro DRE 121039714*/
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        AnalisaTexto analisaTexto = new AnalisaTexto("src\\poema-tabacaria.txt");
        String invertido = analisaTexto.getTextoInvertido();
        Set<String> sem_repeticao = analisaTexto.palavrasSemRepeticao();
        Map<String, Integer> freq = analisaTexto.computarFrequenciaDasPalavras(true);
        System.out.println(freq);
    }

}
