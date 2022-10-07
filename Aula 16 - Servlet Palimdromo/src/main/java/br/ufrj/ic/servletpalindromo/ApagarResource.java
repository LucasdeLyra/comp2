/*Lucas de Lyra Monteiro DRE 121039714*/

package br.ufrj.ic.servletpalindromo;

import javax.ws.rs.*;

@Path("/apagar")
public class ApagarResource {
    @GET
    @Produces("text/html")

    public String apagar(@QueryParam("palindromo") @DefaultValue("") String palindromo) {

        palindromo = palindromo.toUpperCase();
        if (PalindromoApplication.jaEstaNaLista(palindromo)) {
            PalindromoApplication.palindromos.remove(palindromo);
            String html = "<html><head><meta charset=\"UTF-8\"><title>Sucesso!</title></head>";
            html += "<body><h2>Palindromo removido!</h2><p>" +palindromo+ " foi removido da lista de palíndromos.</p><p><a href=\"listar\">Voltar à lista</a></p></body></html>";
            return html;
        }
        String html = "<html><head><meta charset=\"UTF-8\"><title>Sucesso!</title></head>";
        html += "<body><h2>Nada foi feito!</h2><p>" + palindromo + " já não estava na lista.</p><p><a href=\"listar\">Voltar à lista</a></p></body></html>";
        return html;
    }
}
