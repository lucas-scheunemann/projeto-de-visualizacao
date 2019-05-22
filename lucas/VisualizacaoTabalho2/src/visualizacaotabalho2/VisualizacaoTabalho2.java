/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizacaotabalho2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        File diretorio[]; //conjunto dos arquivos do diretório
        File arquivo = new File("C:\\Users\\lucas\\Desktop\\PC\\FACULDADE\\visualização da informação\\trabalho 2\\documentos"); //caminho do diretório
        diretorio = arquivo.listFiles(); //adição dos arquivos no conjunto
        
        BaseDeFrequencia base = new BaseDeFrequencia(); //base de dados com frequencia de cada palavra
        
        for (File diretorio1 : diretorio) { //para cada arquivo no diretorio
            
            System.out.println(diretorio1.toString()); //mostra o caminho do arquivo sendo lido atualmente
            FileReader FiRe = new FileReader(diretorio1); //lê cada arquivo
            Scanner scan = new Scanner(FiRe); //leitor de arquivos
            
            
            while (scan.hasNext()) { //enquanto tiver texto,
                String[] linha = scan.nextLine().split("\\s"); //salva cada palavra numa String
                
                System.out.println(Arrays.toString(linha) + linha.length); //e imprime na tela o conjunto de palavras da linha.
                
                for (String linha1 : linha) {//para cada palavra da linha
                    
                    linha1 = linha1.replace(".", "");//retirada de "."
                    linha1 = linha1.replace(",", "");//retirada de ","
                    linha1 = linha1.replace("\"", "");//retirada de """
                                        
                    if (base.getMap().isEmpty()) { //se a base estiver vazia,
                        base.getMap().put(linha1, INICIAL); //adiciona a palavra na base e adiciona 1 na contagem da mesma.
                    }
                    else {//se não estiver vazia,
                        if (base.getMap().containsKey(linha1)){ //se a palavra está na base,
                            base.getMap().put(linha1, base.getMap().get(linha1)+1);//adiciona 1 na contagem dele.
                                                       
                        }
                        else { //se não estiver na base,
                            base.getMap().put(linha1, INICIAL);//adiciona na próxima posição e conta a primeira aparição.
                            
                        }
                    }
                }
                System.out.println(base.getMap().toString() + " \n ");
            }
        }
        
        
    }
    
}
