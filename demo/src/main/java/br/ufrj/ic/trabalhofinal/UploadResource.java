package br.ufrj.ic.trabalhofinal;

import javax.ws.rs.*;

@Path("/file")
public class UploadResource{
    @GET
    @Produces("text/html")
    public String uploadForm(){
        String html = "<html><head><meta charset=\"UTF-8\"><title>Enviar MP3</title></head>" +
                "<body><form action=\"upload\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                "<label for=\"file\">Escolha um arquivo MP3:</label>" +
                "<input type=\"file\" accept=\"audio/mpeg3\" id=\"file\" name=\"file\"><br><br>" +
                "<input type=\"submit\" />" +
                "</form>" +
                "</body>" +
                "</html>";
        return html;
    }

}