package br.ufrj.ic.trabalhofinal;

import javax.ws.rs.*;

@Path("/salvar")
public class SalvarResource {
    @GET
    @Produces("text/html")
    public String Salvar(@QueryParam("filename") @DefaultValue("") String filename, @QueryParam("titulo") @DefaultValue("") String titulo){
        return MusicApplication.salvarParam(titulo, filename );
    }

}