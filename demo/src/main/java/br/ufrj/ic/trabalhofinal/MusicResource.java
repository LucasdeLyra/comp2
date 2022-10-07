package br.ufrj.ic.trabalhofinal;

import javax.ws.rs.*;

@Path("/listar")
public class MusicResource {
    @GET
    @Produces("text/html")
    public String listar(@QueryParam("q") @DefaultValue("") String filename) {
        return MusicApplication.createFinalHTML(filename);
    }
}