/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizacaotabalho2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author lucas
 */
public class VisualizacaoTabalho2 {
public static final int INICIAL = 1;
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        
        File diretorio[]; //conjunto dos arquivos do diretório
        File arquivo = new File("C:\\Users\\lucas\\Desktop\\PC\\FACULDADE\\visualização da informação\\trabalho 2\\documentos"); //caminho do diretório
        diretorio = arquivo.listFiles(); //adição dos arquivos no conjunto
        int QTD_DOCS=0;//quantidade de arquivos analisados
        
        BaseDeFrequencia base = new BaseDeFrequencia(); //base de dados com frequencia de cada palavra
        
        ArrayList<String> documento = new ArrayList<>(); //lista para coleta das palavras
        
        ArrayList<ArrayList<String>> archives; //lista de palavras de cada documento
        archives = new ArrayList<>();
        
        
        for (File diretorio1 : diretorio) { //para cada arquivo no diretorio
            
            QTD_DOCS++;//conta quantos documentos serão analisados
            
            System.out.println(diretorio1.toString()); //mostra o caminho do arquivo sendo lido atualmente
            
            
            Scanner scan = new Scanner(new FileInputStream(diretorio1), "latin1"); //leitor de arquivos
            
            
            while (scan.hasNext()) { //enquanto tiver texto,
                String[] linha = scan.nextLine().split("\\s"); //separa as palavras, salva cada palavra numa String
                
                
                for (String linha1 : linha) {//para cada palavra da linha
                    
                    //-- filtro das palavras --//
                    
                    linha1 = linha1.replace(".", "");//retirada de "."
                    linha1 = linha1.replace(",", "");//retirada de ","
                    linha1 = linha1.replace("\"", "");//retirada de """
                    
                    if (BaseDeFrequencia.STOPWORDS.contains(linha1)){//se estiver na base de stopwords,
                        continue; //passa pra próxima
                    }
                    // ---------------------//
                    documento.add(linha1); //adiciona a palavra ao documento
                    
                    if (base.getMap().isEmpty()) { //se a base estiver vazia,
                        base.getMap().put(linha1, INICIAL); //adiciona a palavra na base e adiciona 1 na contagem da mesma.
                        
                        BaseDeFrequencia.TOTAL = BaseDeFrequencia.TOTAL + INICIAL; //contagem do número total de palavras
                
                    }
                    else {//se não estiver vazia,
                        if (base.getMap().containsKey(linha1)){ //se a palavra está na base,
                            base.getMap().put(linha1, base.getMap().get(linha1)+1);//adiciona 1 na contagem dele.
                            BaseDeFrequencia.TOTAL = BaseDeFrequencia.TOTAL + INICIAL; //contagem do número total de palavras
                                           
                        }
                        else { //se não estiver na base,
                            base.getMap().put(linha1, INICIAL);//adiciona na próxima posição e conta a primeira aparição.
                            BaseDeFrequencia.TOTAL = BaseDeFrequencia.TOTAL + INICIAL; //contagem do número total de palavras
                
                        }
                    }
                }
                
            }
            System.out.println(base.getMap().toString() + " \n total: " + BaseDeFrequencia.TOTAL); //apresentação da base de frequencia e total de palavras
            
            ArrayList<String> copy = new ArrayList<String>(documento);//faz uma cópia das palavras adquiridas
            archives.add(copy);//adiciona as palavras em archives para definir quais palavras estão em quais textos (1ºtexto no 1º indice e assim por diante)
            
             
             documento.clear();//limpa a lista para o novo documento
            
        }
        
        double[][] freq_p_doc = new double[QTD_DOCS][BaseDeFrequencia.TOTAL];
        
        int repeticoes = 0; //conta o número de vezes que este 'for' abaixo se repete
        
        //calcular frequencia de ocorrencias de cada palavra
        for (String palavra : base.getMap().keySet()){ //para cada palavra,

            int contagem = 0; //conta quantas vezes a palavra aparece em cada documento
            
            ArrayList<Double> freqs = new ArrayList();//lista de frequencias, numero de valores = numero de documentos
            
            int doc = 0;//indicador de cada documento, usado para detectar qual documento dentro de archives
            
            //tarefa: contar quantas vezes a palavra aparece nos documentos individualmente, então dividir pelo numero total de palavras em cada documento
            for (File diretorio1 : diretorio) {//para cada documento,
                
                for (String palavra_doc : archives.get(doc)){//para cada palavra no documento,
                    
                    if (palavra.equals(palavra_doc)){ //se a palavra da base for a mesma palavra no documento.
                        contagem++; //conta mais um
                    }
                    
                }
                
                //aqui contagem = número de aparições da palavra por documento
                //frequencia = frequencia de cada palavra em cada documento
                double frequencia = ((double) contagem / archives.get(doc).size()) * 100;//esse 100 serve para transformar o valor em porcentagem.
                
                freq_p_doc[doc][repeticoes] = frequencia; //joga a frequencia separadamente pra cada documento
                //essa variável será usada para calcular as distâncias entre documentos.
                
                contagem = 0; //resetando contagem para o próximo documento
                doc++; //contando o próximo documento
                freqs.add(frequencia);//adiciona a frequencia no conjunto de frequencias da palavra
                
                
                
            }
            //aqui deve calcular a distância entre os documentos usando as porcentagens acima
            //uso da distância euclidiana
            //System.out.println("palavra: "+palavra +" -> frequencia: " + freqs.toString()); //sout das frequencias de cada palavra.
            repeticoes++;
            
        }
        
        //aqui estão as frequencias de cada palavra em cada documento.
        for (int i =0;i<QTD_DOCS;i++){
            System.out.print("frequencias do documento "+ i + " [");
            for (int j=0;j<BaseDeFrequencia.TOTAL;j++){
                System.out.print(freq_p_doc[i][j]+", ");
            }
            System.out.println("]\n");
        }
        
        
        double[][] matrizDeSimilaridade = new double[QTD_DOCS][QTD_DOCS]; //matriz de similaridade entre os documentos
        
        
        for (int i = 0 ; i<QTD_DOCS;i++){ //contador das linhas da matriz
            for (int j = 0; j<QTD_DOCS; j++){ //contador das colunas da matriz
                matrizDeSimilaridade[i][j] = 0.0;//inicializando a matriz
            }
            
        }
        System.out.println("matriz de similaridade");
        for (int i = 0 ; i<QTD_DOCS;i++){ //contador das linhas da matriz
            for (int j = 0; j<QTD_DOCS; j++){ //contador das colunas da matriz
                for (int x = 0; x < BaseDeFrequencia.TOTAL; x++){ //selecionador da palavra
                    matrizDeSimilaridade[i][j] += Math.abs((freq_p_doc[i][x]) - (freq_p_doc[j][x]));
                }
                System.out.print("("+i+","+j+")"+matrizDeSimilaridade[i][j]+" ");
            }
            System.out.println();
        }
        //daqui, matrizDeSimilaridade possui a matriz corretamente
        
        
        
    }
    
}
