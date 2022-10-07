package br.ufrj.ic.trabalhofinal;

import com.mpatric.mp3agic.ID3v1;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;


@ApplicationPath("/api")
@MultipartConfig
public class MusicApplication extends Application{
    public static String filepath = "./output.mp3";

    protected static String sucessoHTML(){
        String html = "<html><head><meta charset=\"UTF-8\"><title>Sucesso!</title></head>";
        html += "<body><h2>Sucesso no Upload!</h2><p>O arquivo foi gravado com sucesso!</p><p><a href=\"file\">Voltar</a></p></body></html>";
        return html;
    }

    protected static String erroHTML(){
        String html = "<html><head><meta charset=\"UTF-8\"><title>Erro!</title></head>";
        html += "<body><h2>Deu ruim!</h2><p>O arquivo não foi Gravado!</p><p><a href=\"file\">Voltar</a></p></body></html>";
        return html;
    }

    protected static Map<String, Object> infoMP3(String filepath){
        Map<String, Object> mp3Info = new HashMap<>();
        try {
            Mp3File mp3file = new Mp3File(filepath);
            mp3Info.put("duracao", mp3file.getLengthInSeconds());
            mp3Info.put("tamanho", mp3file.getLength());
            mp3Info.put("bitrate", mp3file.getBitrate());
            mp3Info.put("versao", mp3file.getVersion());
            mp3Info.put("sample", mp3file.getSampleRate());
            mp3Info.put("channel", mp3file.getChannelMode());
            mp3Info.put("id3v1", mp3file.hasId3v1Tag());
            mp3Info.put("id3v2", mp3file.hasId3v2Tag());
            mp3Info.put("custom", mp3file.hasCustomTag());
            return mp3Info;

        }catch (Exception e){
            return null;
        }
    }

    protected static String salvarParam(String titulo, String filename){
        try {
            Mp3File mp3file = new Mp3File(filepath);
            Map<String, Object> mp3Info = infoMP3(filepath);
            ID3v2 id3v2Tag;
            id3v2Tag = mp3file.getId3v2Tag();
            id3v2Tag.setTitle(titulo);
            mp3file.save(filename);
            return sucessoHTML();
        }catch (Exception e){
            return erroHTML();
        }

    }

    protected static Map<String, Object> showId3v2Tags(String filepath){
        Map<String, Object> id3v2Map = new HashMap<>();
        try {
            Mp3File mp3file = new Mp3File(filepath);
            ID3v2 id3v2Tag;

            if (mp3file.hasId3v2Tag()) {
                id3v2Tag = mp3file.getId3v2Tag();
            } else if(mp3file.hasId3v1Tag()){
                // mp3 does not have an ID3v2 tag, let's create one..
                id3v2Tag = new ID3v24Tag();
                mp3file.setId3v2Tag(id3v2Tag);
                ID3v1 id3v1Tag;
                id3v1Tag = mp3file.getId3v1Tag();
                id3v2Tag.setTrack(id3v1Tag.getTrack());
                id3v2Tag.setArtist(id3v1Tag.getArtist());//TODO: Fazer as outras tags

            }else{
                id3v2Tag = new ID3v24Tag();
                mp3file.setId3v2Tag(id3v2Tag);
            }
            id3v2Map.put("faixa", id3v2Tag.getTrack());
            id3v2Map.put("artista", id3v2Tag.getArtist());
            id3v2Map.put("album", id3v2Tag.getAlbum());
            id3v2Map.put("titulo", id3v2Tag.getTitle());
            id3v2Map.put("ano", id3v2Tag.getYear());
            id3v2Map.put("genero", id3v2Tag.getGenreDescription());
            id3v2Map.put("comentario", id3v2Tag.getComment());
            id3v2Map.put("compositor", id3v2Tag.getComposer());
            id3v2Map.put("artista original", id3v2Tag.getOriginalArtist());
            id3v2Map.put("copyright", id3v2Tag.getCopyright());
            id3v2Map.put("url", id3v2Tag.getUrl());
            id3v2Map.put("encoder", id3v2Tag.getEncoder());
            //id3v2tag.put("Imagem do album", id3v2Tag.getAlbumImage());
            return id3v2Map;

        }catch (Exception e){
            return null;
        }

    }

    private static String htmlForm(String filename) {
        Map<String, Object> showId3v2Tags = showId3v2Tags(filepath);

        String formhtml = "<form method=\"GET\" action=\"salvar\">";

        formhtml += "<label for=\"filename\">Nome do arquivo:</label><br>";
        formhtml += "<input type=\"filename\" id=\"filename\" name=\"filename\" value=" + filename + "><br>"; // alterar para remover o ".mp3" ou não?

        formhtml += "<label for=\"titulo\">Titulo:</label><br>";
        formhtml += "<input type=\"text\" id=\"titulo\" name=\"titulo\" value=" + showId3v2Tags.get("titulo") + " ><br>"; //TODO: forms pegando somente uma palavra

        formhtml += "<label for=\"artista\">Artista:</label><br>";
        formhtml += "<input type=\"text\" id=\"artista\" name=\"artista\" value=" + showId3v2Tags.get("artista") + "><br>";

        formhtml += "<label for=\"album\">Album:</label><br>";
        formhtml += "<input type=\"text\" id=\"album\" name=\"album\" value=" + showId3v2Tags.get("album") + "><br>";

        formhtml += "<label for=\"faixa\">Faixa:</label><br>";
        formhtml += "<input type=\"text\" id=\"faixa\" name=\"faixa\" value=" + showId3v2Tags.get("faixa") + "><br>";

        formhtml += "<label for=\"ano\">Ano:</label><br>";
        formhtml += "<input type=\"text\" id=\"ano\" name=\"ano\" value=" + showId3v2Tags.get("ano") + "><br>";

        formhtml += "<label for=\"genero\">Genero:</label><br>";
        formhtml += "<input type=\"text\" id=\"genero\" name=\"genero\" value=" + showId3v2Tags.get("genero") + "><br>";

        formhtml += "<label for=\"compositor\">Compositor:</label><br>";
        formhtml += "<input type=\"text\" id=\"compositor\" name=\"compositor\" value=" + showId3v2Tags.get("compositor") + "><br>";

        formhtml += "<label for=\"artista original\">Artista original:</label><br>";
        formhtml += "<input type=\"text\" id=\"artista original\" name=\"artista original\" value=" + showId3v2Tags.get("artista original") + "><br>";

        formhtml += "<label for=\"comentario\">Comentario:</label><br>";
        formhtml += "<input type=\"text\" id=\"comentario\" name=\"comentario\" value=" + showId3v2Tags.get("comentario") + "><br>";

        formhtml += "<label for=\"copyright\">Copyright:</label><br>";
        formhtml += "<input type=\"text\" id=\"copyright\" name=\"copyright\" value=" + showId3v2Tags.get("copyright") + "><br>";

        formhtml += "<label for=\"URL\">url:</Label><br>";
        formhtml += "<input type=\"text\" id=\"url\" name=\"url\" value=" + showId3v2Tags.get("url") + "><br>";

        formhtml += "<label for=\"encoder\">Encoder:</label><br>";
        formhtml += "<input type=\"text\" id=\"encoder1\" name=\"encoder\" value=" + showId3v2Tags.get("encoder") + "><br>";

        formhtml += "<input type=\"submit\" value=\"Salvar\"></input>";

        formhtml += "</form>";
        return formhtml;
    }

    private static String immutableObjectsHTML(){
        Map<String, Object> infoMP3 = infoMP3(filepath);
        String immutables = "<p>";
        immutables += "A duração é " + infoMP3.get("duracao") + " seg</p>"; // TODO: mudar para minuto:segundos
        immutables += "<p> O tamanho é " + infoMP3.get("tamanho") + " bytes</p>";
        immutables += "<p> A bitrate é " + infoMP3.get("bitrate") + " kbps</p>";
        immutables += "<p> A versao é " + infoMP3.get("versao") + "</p>";
        immutables += "<p> A taxa de amostragem é " + infoMP3.get("sample") + " Hz</p>";
        immutables += "<p> O canal é do tipo " + infoMP3.get("channel") + "</p>";
        immutables += "<p> ID3v1 " + infoMP3.get("id3v1") + "</p>";
        immutables += "<p> ID3v2 " + infoMP3.get("id3v2") + "</p>";
        immutables += "<p> Custom " + infoMP3.get("custom") + "</p>";
        return immutables;
    }


    public static String createFinalHTML(String filename){
        String html = "<html><head><meta charset=\"UTF-8\"><title>Dados do MP3</title></head>";
        html += "<body><h2>Informações sobre a música</h2><h3>Os dados imutáveis são</h3>" + immutableObjectsHTML() + "<h3>Os dados mutáveis são</h3>" + htmlForm(filename) + "<p><a href=\"file\">Cancelar</a></p><body></html>";
        /*html = html.replace("$formadicionar", htmlForm());*/ // TODO: consertar o replace
        return html;
    }

}