/*Lucas de Lyra Monteiro DRE 121039714*/
/*Acho que acabei considerando coisa demais pra esse exercício, tomara que os comentários ajudem na correção*/

import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        /*System.out.println(numeroParaPalavras(11100));
        System.out.println(numeroParaPalavras(0));
        System.out.println(numeroParaPalavras(1));
        System.out.println(numeroParaPalavras(2));
        System.out.println(numeroParaPalavras(16));
        System.out.println(numeroParaPalavras(23));
        System.out.println(numeroParaPalavras(100001));
        System.out.println(numeroParaPalavras(103900));
        System.out.println(numeroParaPalavras(23138280));
        System.out.println(numeroParaPalavras(1999999999));
        System.out.println(numeroParaPalavras(10));
        System.out.println(numeroParaPalavras(100));
        System.out.println(numeroParaPalavras(1100));
        System.out.println(numeroParaPalavras(30003));
        System.out.println(numeroParaPalavras(400000));
        System.out.println(numeroParaPalavras(5000025));
        System.out.println(numeroParaPalavras(60000000));
        System.out.println(numeroParaPalavras(700000000));
        System.out.println(numeroParaPalavras(2001210000));
        System.out.println(numeroParaPalavras(721394860));
        System.out.println(numeroParaPalavras(1999));
        System.out.println(numeroParaPalavras(-9));
        */
        /*Comentei ai alguns testes que eu usei, o -9 é pra ver a condicional funcionando porque o JAVA não aceita 10^13*/
    }
    static final Map<Integer, String> unidade = Map.of(
            0,"Zero",1, "Um", 2, "Dois", 3, "Três", 4, "Quatro", 5, "Cinco",
            6, "Seis", 7, "Sete", 8, "Oito", 9, "Nove");
    static final Map<Integer, String> dezena_dez = Map.of(
            10, "Dez", 11, "Onze", 12,"Doze", 13, "Treze", 14, "Quatorze",
            15, "Quinze", 16, "Dezesseis", 17, "Dezessete", 18, "Dezoito", 19, "Dezenove"
    );
    static final Map<Integer, String> dezena_outros = Map.of(
            20, "Vinte", 30, "Trinta", 40, "Quarenta", 50, "Cinquenta", 60, "Sessenta",
            70, "Setenta", 80, "Oitenta", 90, "Noventa"
    );
    static final Map<Integer, String> centena = Map.of(
            100, "Cento", 200, "Duzentos", 300, "Trezentos", 400, "Quatrocentos", 500, "Quinhentos",
            600, "Seiscentos", 700, "Setecentos", 800, "Oitocentos", 900, "Novecentos"
    );

    public static String numeroParaPalavras(int num) {
        testa_range(num);
        String numero_escrito = "";
        if (num == 0){
            numero_escrito = unidade.get(num);
            return numero_escrito;
        }
        /*Abaixo, divide o numero nas "centenas" ou seja, 1675678 vivaria [1,675, 678], dividido na centena,
         centena de milhar e milhão*/
        ArrayList<Integer> dividido = divide_num(num,1000);

        int grandeza = (int) Math.ceil(String.valueOf(num).length()/3.0-1);
        /*Tentei usar Math.log10() mas não funciona pra todos os casos, provavelmente por causa daqueles arredondamentos
        * que os computadores são obrigados a fazer*/

        for (int i = grandeza; i>=0; --i) {
            if (i == 3){
                if(dividido.get(i) == 1){/*Singular*/
                    numero_escrito += centena_para_string(dividido.get(i)) + " Bilhão";
                }else{/*Plural*/
                    numero_escrito += centena_para_string(dividido.get(i)) + " Bilhões";
                }
                if(dividido.get(2) != 0 && (dividido.get(1) != 0 || dividido.get(0) >= 100 || dividido.get(0)%100!=0)){
                    /*Vou explicar esse, me agradeça eu do futuro:
                    * Se a trinca que ocupa a segunda posição, ou seja os AAA xxxAAAxxxxxx for diferentes de 0 então de
                    * duas uma: ou só tem esse número e temos algo tipo xxxAAA000000 e portanto temos, por exemplo,
                    * Um Bilhão e Cem Milhões. Se tivermos qualquer coisa xxxAAAxxxx1 então ja é preciso a vírgula.
                    * Por outro lado, pode acontecer de termos xxxxxxxx11 e o correto é, por exemplo, Um Bilhão e Onze.
                    * Isso se repete para todos os númeross menores que 100. Além disso, os números múltiplos de 100
                    * tem que ser acompanhados por "e" por exemplo Dois Bilhões e Quatrocentos. Aqui a gente testa o
                    * oposto disso tudo e coloca vírgula caso sejam todas verdadeiras*/
                    numero_escrito += ", ";
                }else if(dividido.get(2) != 0 || dividido.get(1) != 0 || dividido.get(0) != 0){
                    /* Testamos se todos os outros números são diferentes de zero, pois, caso sejam, então o número
                    * acaba por aqui, por exemplo, o correto é Dois Bilhões e não 'Dois Bilhões e e e ' */
                    numero_escrito += " e ";
                }

            }else if (i == 2){

                if(dividido.get(i) == 1){/*Singular*/
                    numero_escrito += centena_para_string(dividido.get(i)) + " Milhão";
                }else if (dividido.get(i)!=0){/*Plural*/
                    numero_escrito += centena_para_string(dividido.get(i)) + " Milhões";
                }
                if (!numero_escrito.substring(numero_escrito.length()-1).equals(" "))
                    /*Só funciona depois de colocar .substring mesmo a ide indicando como redudante????*/
                    if((dividido.get(1) != 0 || dividido.get(0) >=100) && dividido.get(0)%100!=0){
                        numero_escrito += ", ";
                    }else if(dividido.get(1) != 0 || dividido.get(0) != 0){
                        numero_escrito += " e ";
                    }
            }else if (i == 1){
                if (dividido.get(i)==1){
                    numero_escrito += "Mil"; /*É mil e não Um Mil*/
                } else if (dividido.get(i)!=0){
                    numero_escrito += centena_para_string(dividido.get(i)) + " Mil";
                }

                if (!numero_escrito.substring(numero_escrito.length()-1).equals(" ")) {
                    if ( (dividido.get(0)%100==0 || dividido.get(0) < 100) && dividido.get(0)!= 0) {
                        /*É Mil e Cem, não Mil Cem, ou Mil, Cem*/
                        numero_escrito += " e ";
                    } else if (dividido.get(0) != 0) {
                        /*É Mil Novecentos e Noventa e Nove, não Mil e Novecentos e Noventa e Nove*/
                        numero_escrito += " ";
                    }
                }
            }else{
                numero_escrito += centena_para_string(dividido.get(i));
            }
        }
        return numero_escrito;

    }


    private static boolean testa_range(Integer num) throws IllegalArgumentException{
        if (num > Math.pow(10, 12) || num < 0) {
            throw new IllegalArgumentException("O número deve ser maior que ou igual a 0 e menor que 1 trilhão");
        }
        return true;
    }

    private static ArrayList<Integer> divide_num(Integer num, Integer mod){
        /*Divide o numero(num) nas "centenas" ou seja, 1675678 vivaria [1, 675, 678], dividido na centena,
         centena de milhar e milhão*/
        ArrayList<Integer> trios = new ArrayList<>();
        while (num>0){
            Integer modulo = num%mod;
            trios.add(modulo);
            num = (num-modulo)/mod;
        }
        return trios;
    }

    private static String centena_para_string (Integer num){
        Integer tamanho = (String.valueOf(num).length());

        ArrayList<Integer> digitos = divide_num(num, 10);
        ArrayList<String> num_lista = new ArrayList<>();

        for (int casa_decimal=tamanho; casa_decimal>0; --casa_decimal) {
            /*Faz decrescente porque 1) a outra função entrega os números de maior grandeza nas posições iniciais
            * da lista e 2) Porque nós lemos do maior número para o menor: É Dois milhões e Um não Um e Dois milhões*/
            if (casa_decimal == 3) {
                num_lista.add(centena.get(digitos.get(2)*100));
            } else if(casa_decimal == 2){
                if (digitos.get(1) == 1){
                    num_lista.add(dezena_dez.get(digitos.get(1) * 10 + digitos.get(0)));
                }else if(dezena_outros.containsKey(digitos.get(1)*10)) {
                    num_lista.add(dezena_outros.get(digitos.get(1) * 10));
                }else{
                    continue; /*Sim eu sei qeu continue no fim não faz nada mas ja era umas 3 da manhã e eu queria dormir*/
                }
            }else{
                if (digitos.size()>1) {
                    if (digitos.get(0) != 0 && digitos.get(1)!=1) {
                        num_lista.add(unidade.get(digitos.get(0) * 1)); /*Cuida de 21, 38, 98, coisas assim,
                         deixei o *1 só pra ficar explícito, eu sei que multiplicar por 1 não faz nada*/
                    }else{
                        continue; /*Cuida do 11, 12, 13, ... */
                    }
                }else if(digitos.size()>0){
                    num_lista.add(unidade.get(digitos.get(0) * 1)); /*Cuida de XXXX3, xxxxxx9, XXX8 */
                }
            }
        }

        if (num == 100){
            num_lista = new ArrayList<>();
            num_lista.add("Cem"); /*100 isolado se lê cem e não cento como no dicionário*/
        }
        String numero = "";
        for (int i = 0; i < num_lista.size(); ++i) {
            if(i < num_lista.size()-1){

                numero += num_lista.get(i) + " e "; /*Por que ele reclama da concatenação de string em loop????*/
            }else{
                numero += num_lista.get(i);
            }
        }
        return numero;
    }
}


