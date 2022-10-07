/*Lucas de Lyra Monteiro DRE 121039714*/

package br.ufrj.ic.servletpalindromo;

import javax.ws.rs.*;
import java.util.Locale;

@Path("/salvar")
public class SalvarResource {
    @GET
    @Produces("text/html")
    public String salvar(@QueryParam("palindromo") @DefaultValue("") String palindromo) {

        if (PalindromoApplication.ehPalindromo(palindromo)) {
            palindromo = palindromo.toUpperCase();
            if (PalindromoApplication.jaEstaNaLista(palindromo)) {
                String html = "<html><head><meta charset=\"UTF-8\"><title>Erro!</title></head>";
                html += "<body><h2>Erro ao adicionar!</h2><p>" +palindromo+ " não foi adicionado pois já se encontra na lista de palíndromos.</p><p><a href=\"listar\">Voltar à lista</a></p></body></html>";
                return html;
            }
            PalindromoApplication.palindromos.add(palindromo);
            String html = "<html><head><meta charset=\"UTF-8\"><title>Sucesso!</title></head>";
            html += "<body><h2>Novo palíndromo adicionado!</h2><p>" +palindromo+ " foi adicionado à lista.</p><p><a href=\"listar\">Voltar à lista</a></p></body></html>";
            return html;
        } else {
            String html = "<html><head><meta charset=\"UTF-8\"><title>Erro!</title></head>";
            html += "<body><h2>Erro ao adicionar!</h2><p>" + palindromo + " não foi adicionado por não ser um palíndromo.</p><p><a href=\"listar\">Voltar à lista</a></p></body></html>";
            return html;

        }
    }
}