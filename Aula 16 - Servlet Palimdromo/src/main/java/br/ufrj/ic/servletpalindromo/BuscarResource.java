/*Lucas de Lyra Monteiro DRE 121039714*/

package br.ufrj.ic.servletpalindromo;

import javax.ws.rs.*;


@Path("/buscar")
public class BuscarResource {
    @GET
    @Produces("text/html")

    public String buscar() {
        String html = "<html><head><meta charset=\"UTF-8\"><title>Buscar por palíndromos</title></head>";
        html += "<body><h2>Buscar por Palíndromos</h2>$formabuscar<p><a href=\"listar\">Voltar à lista</a></p><body></html>";
        html = html.replace("$formabuscar", htmlForm());
        return html;
    }

    private String htmlForm() {
        String formhtml = "<form method=\"GET\" action=\"resultadobusca\">";
        formhtml += "<input type=\"text\" name=\"texto\"></input>";
        formhtml += "<input type=\"submit\" value=\"Buscar\"></input></form>";
        return formhtml;
    }


}


