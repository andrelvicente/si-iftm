package geradordehorarios.v1;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Interface extends javax.swing.JFrame {    
    SimpleAttributeSet texto1;
    SimpleAttributeSet texto2;
    SimpleAttributeSet texto3;
    SimpleAttributeSet texto4;
    Set<String> periodo1, periodo2, periodo3, periodo4, periodo5;
    NumberFormat formatado = new DecimalFormat("00");
    String melhorHorario[] = new String [101];
    String professores[] = new String [100];
    String disciplinas[] = new String [100];
    int conflitos[] = new int [100];
  /*  public static Interface instancia;
    public JTextField t1, t2;
    public JTextPane t3; */

    public Interface() {
        initComponents();
     /*   instancia = this; 
        t1 = jTextField1;
        t2 = jTextField7;
        t3 = saida;  */
        texto1 = new SimpleAttributeSet();
        texto2 = new SimpleAttributeSet();
        texto3 = new SimpleAttributeSet();
        texto4 = new SimpleAttributeSet();
        setEstilos();
        setPeriodos();
        jTextField2.requestFocus();
        p1.setSelected(true);    
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setEstilos(){              
        StyleConstants.setBold(texto1,true);
        StyleConstants.setFontSize(texto1,20);
        StyleConstants.setFontFamily(texto1,"Arial");
        StyleConstants.setForeground(texto1,Color.WHITE);
        StyleConstants.setBackground(texto1, Color.DARK_GRAY);

        StyleConstants.setBold(texto2,true);
        StyleConstants.setFontSize(texto2, 14);
        StyleConstants.setFontFamily(texto2,"Arial");
        StyleConstants.setForeground(texto2, Color.black);     
        
        StyleConstants.setBold(texto3,false);
        StyleConstants.setFontSize(texto3, 12);
        StyleConstants.setFontFamily(texto3,"Arial");
        StyleConstants.setForeground(texto3, Color.black);  
        
        StyleConstants.setBold(texto4,true);
        StyleConstants.setFontSize(texto4, 12);
        StyleConstants.setFontFamily(texto4,"Arial");
        StyleConstants.setForeground(texto4, Color.red);  
        
        
    }
    
    private void setTextNoFinal(){
        Document doc = saida.getDocument();
        int tamanhoDocumento = doc.getLength();
        saida.setSelectionStart(tamanhoDocumento);
        saida.setSelectionEnd(tamanhoDocumento);        
    }
    
    private void insertTexto(String t, SimpleAttributeSet estilo){        
        setTextNoFinal();
        Document doc = saida.getDocument();
        try{
            doc.insertString(doc.getLength(),t,estilo);
        }catch(BadLocationException e){
           e.printStackTrace();
        }
    }
    
    private void setPeriodos(){
       periodo1 = new HashSet<>();
       periodo2 = new HashSet<>();
       periodo3 = new HashSet<>();
       periodo4 = new HashSet<>();
       periodo5 = new HashSet<>();
   }
   
    private void descodificador(){
        for(int j=0;j<100;j++)        
            if(melhorHorario[j].substring(0,2).compareTo("01")==0)
                professores[j] = "A";
            else if(melhorHorario[j].substring(0,2).compareTo("02")==0)
                     professores[j] = "B";
                 else if(melhorHorario[j].substring(0,2).compareTo("03")==0)
                          professores[j] = "C";
                      else if(melhorHorario[j].substring(0,2).compareTo("04")==0)
                               professores[j] = "D";
                           else if(melhorHorario[j].substring(0,2).compareTo("05")==0)
                                    professores[j] = "E";
                                else if(melhorHorario[j].substring(0,2).compareTo("06")==0)
                                         professores[j] = "F";
                                     else if(melhorHorario[j].substring(0,2).compareTo("07")==0)
                                              professores[j] = "G";
                                          else if(melhorHorario[j].substring(0,2).compareTo("08")==0)
                                                   professores[j] = "H";
                                               else if(melhorHorario[j].substring(0,2).compareTo("09")==0)
                                                        professores[j] = "I";
                                                    else if(melhorHorario[j].substring(0,2).compareTo("10")==0)
                                                             professores[j] = "J";
        for(int j=0; j<100; j++)                                                 
            if(melhorHorario[j].substring(2,4).compareTo("01")==0)
                disciplinas[j] = "PWE..";
            else if(melhorHorario[j].substring(2,4).compareTo("02")==0)
                     disciplinas[j] = "L.P..";
            else if(melhorHorario[j].substring(2,4).compareTo("03")==0)
                     disciplinas[j] = "Requ.";
            else if(melhorHorario[j].substring(2,4).compareTo("04")==0)
                     disciplinas[j] = "S.O..";
            else if(melhorHorario[j].substring(2,4).compareTo("05")==0)
                     disciplinas[j] = "FSI..";
            else if(melhorHorario[j].substring(2,4).compareTo("06")==0)
                     disciplinas[j] = "FSWMS";
            else if(melhorHorario[j].substring(2,4).compareTo("07")==0)
                     disciplinas[j] = "A.P..";
            else if(melhorHorario[j].substring(2,4).compareTo("08")==0)
                     disciplinas[j] = "POO1.";
            else if(melhorHorario[j].substring(2,4).compareTo("09")==0)
                     disciplinas[j] = "F.B..";
            else if(melhorHorario[j].substring(2,4).compareTo("10")==0)
                     disciplinas[j] = "JSB.."; 
            else if(melhorHorario[j].substring(2,4).compareTo("11")==0)
                     disciplinas[j] = "PBeMO";
            else if(melhorHorario[j].substring(2,4).compareTo("12")==0)
                     disciplinas[j] = "PFeWJ";
            else if(melhorHorario[j].substring(2,4).compareTo("13")==0)
                     disciplinas[j] = "POO1.";
            else if(melhorHorario[j].substring(2,4).compareTo("14")==0)
                     disciplinas[j] = "T.A..";
            else if(melhorHorario[j].substring(2,4).compareTo("15")==0)
                     disciplinas[j] = "BDA..";
            else if(melhorHorario[j].substring(2,4).compareTo("16")==0)
                     disciplinas[j] = "PBeMN";
            else if(melhorHorario[j].substring(2,4).compareTo("17")==0)
                     disciplinas[j] = "PADM.";
            else if(melhorHorario[j].substring(2,4).compareTo("18")==0)
                     disciplinas[j] = "IHC..";
            else if(melhorHorario[j].substring(2,4).compareTo("19")==0)
                     disciplinas[j] = "BDNoS";
            else if(melhorHorario[j].substring(2,4).compareTo("20")==0)
                     disciplinas[j] = "S.D..";
            else if(melhorHorario[j].substring(2,4).compareTo("21")==0)
                     disciplinas[j] = "TE1..";
            else if(melhorHorario[j].substring(2,4).compareTo("22")==0)
                     disciplinas[j] = "TE2L.";
            else if(melhorHorario[j].substring(2,4).compareTo("23")==0)
                     disciplinas[j] = "I.C..";
            else if(melhorHorario[j].substring(2,4).compareTo("24")==0)
                     disciplinas[j] = "PRAM.";
            else if(melhorHorario[j].substring(2,4).compareTo("25")==0)
                     disciplinas[j] = "SSI..";     
    }
    
    private void showResultados2(){       
        saida.setText("");
        insertTexto("                                                   1°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);
        String aux = "";
        for(int j=0;j<4;j++){
               aux ="\n\t "+disciplinas[j]+"/ "+professores[j];
               if(conflitos[j]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+4]+"/ "+professores[j+4];
               if(conflitos[j+4]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+8]+"/ "+professores[j+8];
               if(conflitos[j+8]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+12]+"/ "+professores[j+12];
               if(conflitos[j+12]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
               if(conflitos[j+16]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4);
        }
        insertTexto("\n                                                   2°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);
        for(int j=20;j<24;j++){
               aux ="\n\t "+disciplinas[j]+"/ "+professores[j];
               if(conflitos[j]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+4]+"/ "+professores[j+4];
               if(conflitos[j+4]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+8]+"/ "+professores[j+8];
               if(conflitos[j+8]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+12]+"/ "+professores[j+12];
               if(conflitos[j+12]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
               if(conflitos[j+16]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4);
        }
        insertTexto("\n                                                   3°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);
        for(int j=40;j<44;j++){
               aux ="\n\t "+disciplinas[j]+"/ "+professores[j];
               if(conflitos[j]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+4]+"/ "+professores[j+4];
               if(conflitos[j+4]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+8]+"/ "+professores[j+8];
               if(conflitos[j+8]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+12]+"/ "+professores[j+12];
               if(conflitos[j+12]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
               if(conflitos[j+16]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4);
        }
        insertTexto("\n                                                   4°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);        
        for(int j=60;j<64;j++){
               aux ="\n\t "+disciplinas[j]+"/ "+professores[j];
               if(conflitos[j]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+4]+"/ "+professores[j+4];
               if(conflitos[j+4]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+8]+"/ "+professores[j+8];
               if(conflitos[j+8]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+12]+"/ "+professores[j+12];
               if(conflitos[j+12]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
               if(conflitos[j+16]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4);
        }
        insertTexto("\n                                                   5°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);
        for(int j=80;j<84;j++){
               aux ="\n\t "+disciplinas[j]+"/ "+professores[j];
               if(conflitos[j]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+4]+"/ "+professores[j+4];
               if(conflitos[j+4]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+8]+"/ "+professores[j+8];
               if(conflitos[j+8]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+12]+"/ "+professores[j+12];
               if(conflitos[j+12]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4); 
               aux = "\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
               if(conflitos[j+16]==0)
                     insertTexto(aux,texto3);   
               else
                     insertTexto(aux,texto4);
        }
    }
    
    private void showResultados(){       
        insertTexto("                                                   1°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);
        String aux = "";
        for(int j=0;j<4;j++){
           aux+="\n\t "+disciplinas[j]+"/ "+professores[j]+"\t\t "+disciplinas[j+4]+"/ "+professores[j+4]+"\t\t "+disciplinas[j+8]+"/ "+professores[j+8]+"\t\t "+disciplinas[j+12]+"/ "+professores[j+12]+"\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
        }
        insertTexto(aux,texto3);   
        insertTexto("\n                                                   2°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);
        aux = "";
        for(int j=20;j<24;j++){
           aux+="\n\t "+disciplinas[j]+"/ "+professores[j]+"\t\t "+disciplinas[j+4]+"/ "+professores[j+4]+"\t\t "+disciplinas[j+8]+"/ "+professores[j+8]+"\t\t "+disciplinas[j+12]+"/ "+professores[j+12]+"\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
        }
        insertTexto(aux,texto3);  
        insertTexto("\n                                                   3°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);
        aux = "";
        for(int j=40;j<44;j++){
           aux+="\n\t "+disciplinas[j]+"/ "+professores[j]+"\t\t "+disciplinas[j+4]+"/ "+professores[j+4]+"\t\t "+disciplinas[j+8]+"/ "+professores[j+8]+"\t\t "+disciplinas[j+12]+"/ "+professores[j+12]+"\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
        }
        insertTexto(aux,texto3);
        insertTexto("\n                                                   4°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);        
        aux = "";
        for(int j=60;j<64;j++){
           aux+="\n\t "+disciplinas[j]+"/ "+professores[j]+"\t\t "+disciplinas[j+4]+"/ "+professores[j+4]+"\t\t "+disciplinas[j+8]+"/ "+professores[j+8]+"\t\t "+disciplinas[j+12]+"/ "+professores[j+12]+"\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
        }
        insertTexto(aux,texto3);
        insertTexto("\n                                                   5°   P E R Í O D O                                          \n",texto1);
        insertTexto("\n\tSEG\t\tTER\t\tQUA\t\tQUI\t\tSEX\n",texto2);
        aux = "";
        for(int j=80;j<84;j++){
           aux+="\n\t "+disciplinas[j]+"/ "+professores[j]+"\t\t "+disciplinas[j+4]+"/ "+professores[j+4]+"\t\t "+disciplinas[j+8]+"/ "+professores[j+8]+"\t\t "+disciplinas[j+12]+"/ "+professores[j+12]+"\t\t "+disciplinas[j+16]+"/ "+professores[j+16]+"\n";
        }
        insertTexto(aux,texto3);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        combo1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        combo6 = new javax.swing.JComboBox<>();
        p1 = new javax.swing.JRadioButton();
        p2 = new javax.swing.JRadioButton();
        combo2 = new javax.swing.JComboBox<>();
        combo7 = new javax.swing.JComboBox<>();
        p3 = new javax.swing.JRadioButton();
        combo3 = new javax.swing.JComboBox<>();
        combo8 = new javax.swing.JComboBox<>();
        p4 = new javax.swing.JRadioButton();
        combo4 = new javax.swing.JComboBox<>();
        combo9 = new javax.swing.JComboBox<>();
        p5 = new javax.swing.JRadioButton();
        combo5 = new javax.swing.JComboBox<>();
        combo10 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        saida = new javax.swing.JTextPane();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1100, 650));
        setPreferredSize(new java.awt.Dimension(950, 650));

        jPanel1.setMaximumSize(new java.awt.Dimension(950, 820));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 820));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("G E R A D O R    A U T O M Á T I C O    D E    H O R Á R I O S");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, -1, 34));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("PROFESSORES");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, -1, -1));

        combo1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" }));
        jPanel1.add(combo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("DISCIPLINAS");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, -1));

        combo6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Projeto Wesite Estático", "Lógica de Programação", "Requisitos", "Sistemas Operacionais", "Fundamentos de Sist. para Internet" }));
        combo6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo6ActionPerformed(evt);
            }
        });
        jPanel1.add(combo6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 300, -1));

        buttonGroup1.add(p1);
        p1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        p1.setText("1° P");
        jPanel1.add(p1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        buttonGroup1.add(p2);
        p2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        p2.setText("2° P");
        jPanel1.add(p2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        combo2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" }));
        jPanel1.add(combo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, -1, -1));

        combo7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Projeto Sistema Web MVC e SQL", "Algoritmos e Programação", "Progrogramação Orientada a Objetos 1", "Fundamentos de Banco de Dados", "Java Script Básico" }));
        combo7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo7ActionPerformed(evt);
            }
        });
        jPanel1.add(combo7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, -1, -1));

        buttonGroup1.add(p3);
        p3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        p3.setText("3° P");
        jPanel1.add(p3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        combo3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" }));
        jPanel1.add(combo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, -1, -1));

        combo8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Projeto Back-end Mon. com ORM", "Projeto Front-end Web JavaScript", "Programação Orientada a Objetos 2", "Testes Automatizados", "Banco de Dados Avançados" }));
        combo8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo8ActionPerformed(evt);
            }
        });
        jPanel1.add(combo8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 300, -1));

        buttonGroup1.add(p4);
        p4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        p4.setText("4° P");
        p4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p4ActionPerformed(evt);
            }
        });
        jPanel1.add(p4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        combo4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" }));
        jPanel1.add(combo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, -1, -1));

        combo9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Projeto Back-end Micros. e NoSQL", "Projeto Aplicação para Disp. Móveis", "Interface Humano-Computador", "Banco de Dados NoSQL", "Sistemas Distribuídos" }));
        combo9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo9ActionPerformed(evt);
            }
        });
        jPanel1.add(combo9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 300, -1));

        buttonGroup1.add(p5);
        p5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        p5.setText("5° P");
        p5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p5ActionPerformed(evt);
            }
        });
        jPanel1.add(p5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        combo5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" }));
        jPanel1.add(combo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, -1, -1));

        combo10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        combo10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tópicos Especiais 1", "Tópicos Especiais 2 / Libras", "Inteligência Computacional", "Prod. de Relatório, Artigo e Monografia", "Segurança em Sistemas para Internet" }));
        combo10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo10ActionPerformed(evt);
            }
        });
        jPanel1.add(combo10, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 300, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 153));
        jButton1.setText("ASSOCIAR MANUAL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        jPanel2.setBackground(new java.awt.Color(153, 255, 153));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Tamanho Pop.");

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Máx. Gerações");

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Prob. Cruzam.");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Prob. Mutação");

        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Núm. de Cortes");

        jTextField6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField6))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 210, 230));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 0));
        jLabel4.setText("PARÂMETROS  DO  A.G.");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 102, 51));
        jButton2.setText("GERAR HORÁRIO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 330, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 51));
        jLabel5.setText("NOTA:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, -1, -1));

        jTextField1.setBackground(new java.awt.Color(153, 255, 153));
        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 440, 60, -1));

        jScrollPane2.setViewportView(saida);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 750, 210));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 153));
        jButton3.setText("ASSOCIAR AUTOMÁTICO");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 330, -1, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 0, 0));
        jButton4.setText("LIMPAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 330, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 51));
        jLabel11.setText("Melhor");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 390, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 51));
        jLabel12.setText("Geração:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 410, -1, -1));

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(153, 255, 153));
        jTextField7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 540, 52, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 967, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addGap(186, 186, 186))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void combo7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo7ActionPerformed

    private void p4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p4ActionPerformed

    private void combo8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo8ActionPerformed

    private void combo9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo9ActionPerformed

    private void p5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p5ActionPerformed

    private void combo10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo10ActionPerformed

    private void combo6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int tamPop = Integer.parseInt(jTextField2.getText());
        int maxGen = Integer.parseInt(jTextField3.getText());
        double pc = Double.parseDouble(jTextField4.getText());
        double pm = Double.parseDouble(jTextField5.getText());
        int cortes = Integer.parseInt(jTextField6.getText()); 
               
        if((tamPop<=0)||(maxGen<=0)||(pc<=0)||(pc>1)||(pm<=0)||(pm>1))
            JOptionPane.showMessageDialog(null,"Parâmetros Inválidos, insira-os novamente!","ERROR",1);
        else{
               if(tamPop%2!=0)
                   tamPop++;
               saida.setText("");
               Algoritmo alg = new Algoritmo(tamPop,maxGen,pc,pm,cortes);  
               conflitos = alg.conflitos;
               //melhorHorario = alg.aG(periodo1, periodo2, periodo3, periodo4, periodo5, Interface.instancia);
               melhorHorario = alg.aG(periodo1, periodo2, periodo3, periodo4, periodo5);               
               descodificador();
               showResultados2();
               jTextField1.setText(melhorHorario[100]);
               jTextField7.setText(melhorHorario[101]);
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       setPeriodos();
       Random num = new Random();

       for(int i=1; i<=5; i++)
          periodo1.add(""+formatado.format(i)+formatado.format(i));
       for(int i=6; i<=10; i++)
          periodo2.add(""+formatado.format(i)+formatado.format(i));
       for(int i=1; i<=5; i++)
          periodo3.add(""+formatado.format(i)+formatado.format(i+10));
       for(int i=6; i<=10; i++)
          periodo4.add(""+formatado.format(i)+formatado.format(i+10));   
       for(int i=21; i<=25; i++)
          periodo5.add(""+formatado.format(num.nextInt(10)+1)+formatado.format(i));  
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       if(p1.isSelected())
          if(periodo1.size()<5)
              periodo1.add(""+formatado.format(combo1.getSelectedIndex()+1)+formatado.format(combo6.getSelectedIndex()+1));
          else
              JOptionPane.showMessageDialog(null,"jÁ FORAM INCLUÍDAS 5 DISCIPLINAS NESSE PERÍODO","ATENÇÃO",1);
       else
          if(p2.isSelected())
             if(periodo2.size()<5)
                periodo1.add(""+formatado.format(combo2.getSelectedIndex()+1)+formatado.format(combo7.getSelectedIndex()+1));
             else
                JOptionPane.showMessageDialog(null,"jÁ FORAM INCLUÍDAS 5 DISCIPLINAS NESSE PERÍODO","ATENÇÃO",1);
          else
             if(p3.isSelected())
                if(periodo3.size()<5)
                    periodo1.add(""+formatado.format(combo3.getSelectedIndex()+1)+formatado.format(combo8.getSelectedIndex()+1));
                else
                    JOptionPane.showMessageDialog(null,"jÁ FORAM INCLUÍDAS 5 DISCIPLINAS NESSE PERÍODO","ATENÇÃO",1);
             else
                if(p4.isSelected())
                   if(periodo4.size()<5)
                      periodo1.add(""+formatado.format(combo4.getSelectedIndex()+1)+formatado.format(combo9.getSelectedIndex()+1));
                   else
                      JOptionPane.showMessageDialog(null,"jÁ FORAM INCLUÍDAS 5 DISCIPLINAS NESSE PERÍODO","ATENÇÃO",1);
                else
                    if(p5.isSelected())
                       if(periodo5.size()<5)
                          periodo1.add(""+formatado.format(combo5.getSelectedIndex()+1)+formatado.format(combo10.getSelectedIndex()+1));
                       else
                          JOptionPane.showMessageDialog(null,"jÁ FORAM INCLUÍDAS 5 DISCIPLINAS NESSE PERÍODO","ATENÇÃO",1);   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      setPeriodos();
      this.dispose();
      this.setVisible(true);
      jTextField1.setText("");
 /*     jTextField2.setText("");
      jTextField3.setText("");
      jTextField4.setText("");
      jTextField5.setText("");
      jTextField6.setText(""); */
      jTextField7.setText("");
      saida.setText("");   
      jTextField2.requestFocus();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        jTextField3.requestFocus();
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        jTextField4.requestFocus();
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        jTextField5.requestFocus();
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        jTextField6.requestFocus();
    }//GEN-LAST:event_jTextField5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> combo1;
    private javax.swing.JComboBox<String> combo10;
    private javax.swing.JComboBox<String> combo2;
    private javax.swing.JComboBox<String> combo3;
    private javax.swing.JComboBox<String> combo4;
    private javax.swing.JComboBox<String> combo5;
    private javax.swing.JComboBox<String> combo6;
    private javax.swing.JComboBox<String> combo7;
    private javax.swing.JComboBox<String> combo8;
    private javax.swing.JComboBox<String> combo9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JRadioButton p1;
    private javax.swing.JRadioButton p2;
    private javax.swing.JRadioButton p3;
    private javax.swing.JRadioButton p4;
    private javax.swing.JRadioButton p5;
    private javax.swing.JTextPane saida;
    // End of variables declaration//GEN-END:variables
}
