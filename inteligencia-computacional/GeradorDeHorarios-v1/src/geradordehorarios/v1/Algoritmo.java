package geradordehorarios.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Algoritmo {
    
    private int tamPop, maxGen, cortes;
    private double pc, pm;
    private String pop[][];
    private String popFilhos[][];
    private double notas[];
    public int conflitos[]; 
    
    public Algoritmo(int tamPop, int maxGen, double pc, double pm, int cortes){
        this.tamPop = tamPop;
        this.maxGen = maxGen;
        this.pc = pc;
        this.pm = pm;
        this.cortes = cortes;
        pop = new String[tamPop][100];
        notas = new double[tamPop];
        popFilhos = new String[tamPop][100];
        conflitos = new int [100];
    }
    
    public void popInicial(Set<String> periodo1,Set<String> periodo2,Set<String> periodo3,Set<String> periodo4,Set<String> periodo5){
        ArrayList<String> aux = new ArrayList<>();        
        for(int k=0; k<tamPop; k++){            
            int j=0;
            aux.clear();
            Iterator<String> it = periodo1.iterator(); 
            while(it.hasNext())
            {
               String s = it.next();               
               for(int i=0;i<4;i++)
                   aux.add(s);
            }
            Collections.shuffle(aux);            
            for(int i=0;i<aux.size();i++){
                pop[k][j] = aux.get(i);
                j++;
            }
            aux.clear();
            it = periodo2.iterator(); 
            while(it.hasNext())
            {
               String s = it.next();
               for(int i=0;i<4;i++)
                   aux.add(s);
            }
            Collections.shuffle(aux);            
            for(int i=0;i<aux.size();i++){
                pop[k][j] = aux.get(i);
                j++;
            }
            aux.clear();
            it = periodo3.iterator(); 
            while(it.hasNext())
            {
               String s = it.next();
               for(int i=0;i<4;i++)
                   aux.add(s);
            }
            Collections.shuffle(aux);
            for(int i=0;i<aux.size();i++){
                pop[k][j] = aux.get(i);
                j++;
            }
            aux.clear();
            it = periodo4.iterator(); 
            while(it.hasNext())
            {
               String s = it.next();
               for(int i=0;i<4;i++)
                   aux.add(s);
            }
            Collections.shuffle(aux);
            for(int i=0;i<aux.size();i++){
                pop[k][j] = aux.get(i);
                j++;
            }
            aux.clear();
            it = periodo5.iterator(); 
            while(it.hasNext())
            {
               String s = it.next();
               for(int i=0;i<4;i++)
                   aux.add(s);
            }
            Collections.shuffle(aux);            
            for(int i=0;i<aux.size();i++){
                pop[k][j] = aux.get(i);
                j++;
            }           
        }
    }
    
    public void avaliacao(){        
        for(int i=0; i<tamPop; i++)
        {
            int salto = 0;
            double cont=0;
            for(int k=0;k<4;k++){
                for(int j=salto;j<salto+20;j++)
                    for(int m=j+20;m<100;m=m+20)
                        if(pop[i][j].substring(0,2).compareTo(pop[i][m].substring(0,2))==0)
                            cont++;                    
                    
                salto = salto + 20;
            }
            notas[i] = cont;
        }     
    }
    
    public void avaliacao2(){      
        double cont;
        for(int i=0; i<tamPop; i++)
        {
            int salto = 0;
            cont=0;
            for(int k=0;k<4;k++){
                for(int j=salto;j<salto+20;j++)
                    for(int m=j+20;m<100;m=m+20)
                        if(pop[i][j].substring(0,2).compareTo(pop[i][m].substring(0,2))==0)
                            cont++;                    
                    
                salto = salto + 20;
            }
            notas[i] = cont;
        }  
        for(int i=0; i<tamPop; i++){
            cont=0;
            for(int j=0; j<99; j=j+2)
                if(pop[i][j].substring(2,4).compareTo(pop[i][j+1].substring(2,4))!=0)
                    cont++;
            notas[i] += cont;    
        }
    }
    
    public void ordenacao(){
       String aux1;
       double aux2;
       for(int i=0;i<tamPop-1;i++)
           for(int k=i+1;k<tamPop;k++)
               if(notas[i]>notas[k])
               {
                   aux2 = notas[i];
                   notas[i] = notas[k];
                   notas[k] = aux2;
                   for(int j=0;j<100;j++)
                   {
                       aux1 = pop[i][j];
                       pop[i][j] = pop[k][j];
                       pop[k][j] = aux1;
                   }
               }
    }
    
    public String [][] selecao(String pais[][], int contGen){
        Random num = new Random();
        int pos;
        if(contGen<(int)(maxGen/2)){
            pos = num.nextInt((int)(tamPop/2));
            for(int j=0;j<100;j++)
                pais[0][j] = pop[pos][j];  
            pos = num.nextInt(tamPop);
            for(int j=0;j<100;j++)
                pais[1][j] = pop[pos][j];
        }
        else
        {
            pos = num.nextInt((int)(tamPop/4));
            for(int j=0;j<100;j++)
                pais[0][j] = pop[pos][j];  
            pos = num.nextInt((int)(tamPop/2));
            for(int j=0;j<100;j++)
                pais[1][j] = pop[pos][j];  
        }       
        return pais;
    }
    
    public String [][] cruzamento(String pais[][], String filhos[][]){
        Set<Integer> pontosCortes = new TreeSet<>(); 
        Iterator<Integer> it;
        Random num = new Random();
        boolean cruza = false;
        int i=0;

        if(num.nextDouble()<pc){
            if(cortes<0)
                cortes = 1;
            if(cortes>4)
                cortes = 4;
            while(pontosCortes.size()<cortes)
                pontosCortes.add(num.nextInt(4) + 1);
            it = pontosCortes.iterator();   
            while(it.hasNext()){
                    int aux = it.next();  // próximo ponto de corte
                    for(int j=i; j<aux*20; j++)
                        if(cruza){
                            filhos[0][j] = pais[1][j];
                            filhos[1][j] = pais[0][j];
                        }
                        else{
                            filhos[0][j] = pais[0][j];
                            filhos[1][j] = pais[1][j];
                        }
                    cruza = !cruza;
                    i = aux*20;
            }
            for(int j=i; j<100; j++)
               if(cruza){
                    filhos[0][j] = pais[1][j];
                    filhos[1][j] = pais[0][j];
               }
               else{
                    filhos[0][j] = pais[0][j];
                    filhos[1][j] = pais[1][j];
               } 
        }
        else
            filhos = pais;  
        return filhos;
    }
    
    public String [][] mutacao(String filhos[][]){
        Random pos = new Random();
        int p1, p2;
        String aux;
        // troca dois horários, período a período
        if(pm>pos.nextDouble())
            for(int k=0; k<2; k++)  //dois filhos
                for(int i=0;i<6;i++)  //quantidade de horários dentro do período que serão trocados
                    for(int j=0; j<100; j+=20){
                       p1 = pos.nextInt(20) + j;  // escolher um horário no período
                       p2 = pos.nextInt(20) + j;  // escolher outro horário no período
                       aux = filhos[k][p1];
                       filhos[k][p1] = filhos[k][p2];
                       filhos[k][p2] = aux;
                    }                 
        return filhos;       
    }
        
    public void insereFilhos(String filhos[][], int contFilhos){
       for(int j=0; j<100; j++){   
           popFilhos[contFilhos][j] = filhos[0][j];
           popFilhos[contFilhos+1][j] = filhos[1][j];
       }
    }
    
    public String [] registro(int contGen, String melhorIndividuo[]){
        if((contGen==0)||(Double.parseDouble(melhorIndividuo[101])>notas[0])){
            for(int j=0;j<100;j++){
                melhorIndividuo[j] = pop[0][j];
                conflitos[j] = 0;
            }
            melhorIndividuo[100] = ""+contGen;
            melhorIndividuo[101] = ""+notas[0]; 
            int salto = 0;
            for(int k=0;k<4;k++){
                for(int j=salto;j<salto+20;j++)
                    for(int m=j+20;m<100;m=m+20)
                        if(melhorIndividuo[j].substring(0,2).compareTo(melhorIndividuo[m].substring(0,2))==0){
                            conflitos[j]=1;                    
                            conflitos[m]=1;
                        }
                salto = salto + 20;
            }
      
       /*     Interface.instancia.t1.setText(""+contGen);
            Interface.instancia.t2.setText(""+notas[0]);
            Interface.instancia.t3.setText("");         */  
        }        
        return melhorIndividuo;
    }
    
    public String [] aG(Set<String> periodo1,Set<String> periodo2,Set<String> periodo3,Set<String> periodo4,Set<String> periodo5){
        String pais[][] = new String[2][100];
        String filhos[][] = new String[2][100];
        String melhorIndividuo[] = new String [102];
        int contGen = 0;
        
        popInicial(periodo1,periodo2,periodo3,periodo4,periodo5);        
        do{
             avaliacao2();
             ordenacao();
             melhorIndividuo = registro(contGen,melhorIndividuo);
             if(contGen==0){
                System.out.println("Melhor Nota: "+notas[0]+" geração: "+contGen);
                System.out.println("Pior Nota: "+notas[tamPop-1]+" geração: "+contGen);
             }   
             int contFilhos = 0;
             do{
                  pais = selecao(pais,contGen);
                  filhos = cruzamento(pais,filhos);
                  filhos = mutacao(filhos);
                  insereFilhos(filhos,contFilhos);
                  contFilhos = contFilhos + 2;
             }while(contFilhos<tamPop);
             pop = popFilhos;
             contGen++;
        }while(contGen<maxGen);     
        System.out.println("Melhor Nota no final: "+notas[0]+" geração: "+contGen);
        System.out.println("Pior  Nota no final: "+notas[tamPop-1]+" geração: "+contGen);
        System.out.println("Melhor Nota de TODAS: "+melhorIndividuo[101]+" geração: "+melhorIndividuo[100]+"\n\n"); 
        return melhorIndividuo;
    }
    
}
