/*Lucas de Lyra Monteiro DRE 121039714*/

package br.ufrj.ic.servletpalindromo;

import javax.ws.rs.*;
import java.util.ArrayList;

@Path("/resultadobusca")
public class ResultadobuscaResource {
    @GET
    @Produces("text/html")
    public String Resultadobusca(@QueryParam("texto") @DefaultValue("") String texto) {
        ArrayList<String> achados = new ArrayList<>();
        texto = texto.toUpperCase();
        for(int i = 0;i < PalindromoApplication.palindromos.size();++i){
            if (PalindromoApplication.palindromos.get(i).indexOf(texto) != -1){
                achados.add(PalindromoApplication.palindromos.get(i));
            }
        }
        if (achados.size() == 0){
            String html = "<html><head><meta charset=\"UTF-8\"><title>Nenhum Palindromo achado!</title></head>";
            html += "<body><h2>Nenhum Palindromo achado!</h2><p>" + texto + " não foi encontrado em nenhum palindromo da lista.</p><p><a href=\"listar\">Voltar à lista</a></p></body></html>";
            return html;
        }
        String html = "<html><head><meta charset=\"UTF-8\"><title>Lista de Palindromos achados</title></head>";
        html += "<body><ul>";
        for(String palavra : achados){
            html += "<li>"+palavra+"</li>";
        }
        html += "</ul>";
        html += "<p><a href=\"buscar\">Voltar à busca por palíndromos</a></p>";
        html += "<p><a href=\"listar\">Voltar à lista</a></p></body></html>";
        return html;
    }
}


