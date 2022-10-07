/*Lucas de Lyra Monteiro DRE 121039714*/

package br.ufrj.ic.servletpalindromo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

@ApplicationPath("/api")
public class PalindromoApplication extends Application {
    protected static ArrayList<String> palindromos = new ArrayList<>();

    protected static String palindromosToHTML(boolean ordena) {
        if (palindromos.size() == 0) {
            return "<p>Lista vazia. Adicione seu primeiro palíndromo para começar.</p>";
        }
        ArrayList<String> pali_copy = new ArrayList<>(palindromos);
        if (ordena) {
            Collections.sort(pali_copy);
        }
        String html = "";
        for (int i = 0; i < pali_copy.size(); ++i) {
            html += "<p style=\"color: " + (i % 2 == 0 ? "orange" : "green") + "\">" + pali_copy.get(i) +
                    "<a href=apagar?palindromo=" + pali_copy.get(i) + "><img src=\"https://cdn-icons-png.flaticon.com/512/126/126468.png\", " +
                    "width=10px, height=15px>"+"</a></p>"; /*A boa prática era baixar a imagem e colocar aqui no repositório eu acho*/
        }
        return html;
    }

    protected static boolean ehPalindromo(String texto) {
        if (texto.length() < 1) {
            return false;
        }
        texto= texto.toUpperCase(); /*assim pode colocar por exemplo 'Ovo' e ele reconhece como palindromo*/
        for (int i = 0; i < texto.length() / 2; ++i) {
            char c1 = texto.charAt(i);
            char c2 = texto.charAt(texto.length() - 1 - i);
            if (c1 != c2) {
                return false;
            }
        }
        return true;
    }
    protected static boolean jaEstaNaLista(String texto){
        return palindromos.contains(texto);
    }
}