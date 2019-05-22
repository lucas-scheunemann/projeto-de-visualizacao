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
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        File diretorio[]; //conjunto dos arquivos do diretório
        File arquivo = new File("C:\\Users\\lucas\\Desktop\\PC\\FACULDADE\\visualização da informação\\trabalho 2\\documentos"); //caminho do diretório
        diretorio = arquivo.listFiles(); //adição dos arquivos no conjunto
        
        BaseDeFrequencia base = new BaseDeFrequencia(); //base de dados com frequencia de cada palavra
        
        for (File diretorio1 : diretorio) { //para cada arquivo no diretorio
            
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

                    
                    if (base.getMap().isEmpty()) { //se a base estiver vazia,
                        base.getMap().put(linha1, INICIAL); //adiciona a palavra na base e adiciona 1 na contagem da mesma.
                        
                        BaseDeFrequencia.total = BaseDeFrequencia.total + INICIAL; //contagem do número total de palavras
                
                    }
                    else {//se não estiver vazia,
                        if (base.getMap().containsKey(linha1)){ //se a palavra está na base,
                            base.getMap().put(linha1, base.getMap().get(linha1)+1);//adiciona 1 na contagem dele.
                            BaseDeFrequencia.total = BaseDeFrequencia.total + INICIAL; //contagem do número total de palavras
                                           
                        }
                        else { //se não estiver na base,
                            base.getMap().put(linha1, INICIAL);//adiciona na próxima posição e conta a primeira aparição.
                            BaseDeFrequencia.total = BaseDeFrequencia.total + INICIAL; //contagem do número total de palavras
                
                        }
                    }
                }
                
            }
            System.out.println(base.getMap().toString() + " \n total: " + BaseDeFrequencia.total); //apresentação da base de frequencia e total de palavras

        }
        
        
    }
    
}
