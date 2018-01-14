package Choiceb;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.*;
import java.io.*;

public class ChoiceB {
    Scanner in = new Scanner(System.in);
    static BufferedReader br = null;
    static FileReader fl = null;
    static String lines;
    public static void main(String[] args) {
        Clase per = new Clase();
        Enemigo ene = new Enemigo();
        persona(per);
        Act1(per, ene);
    }
    //ACT 1
  public static void Act1(Clase per, Enemigo ene){
    System.out.print("BIENVENIDO: " +per.getNAME());
    System.out.println(". Incontables peligrosnte aguardan en tú aventura");
    System.out.println("Se valiente y recuerda. CADA DECISION CUENTA.");
    for(int i=0;i<3;i++)
      System.out.println();
    leer("Act1\\Parte 1\\Act 1.0.txt");
  }
  
    //LECTOR DEL TXT
  public static void leer(String a){
    try{
      fl = new FileReader(a);
      br = new BufferedReader(fl);
    while ((lines = br.readLine()) != null) {
      System.out.println(lines);
      }
    }catch(IOException ex){
      Logger.getLogger(ChoiceB.class.getName()).log(Level.SEVERE, null, ex);
    }catch (Exception e) {
      e.printStackTrace();
    }finally{ //Lectura del archivo de texto obligatorio
      try {
        if (br != null)
          br.close();
        if (fl != null)
          fl.close();
	}catch (IOException ex) {
          ex.printStackTrace();
        }
    }
    
  }
 
    //SELECCION DE LA CLASE
  public static void persona(Clase per){
    int selec, val;
    String a;
    Scanner in = new Scanner(System.in);
    do{  
     System.out.println("Selecciona una clase");
     System.out.println("Guerrero:0 / Picaro:1 / Mago:2");
     selec = in.nextInt();
     while(selec<0 || selec>2){
       System.out.println("Error clase no existente");
       selec = in.nextInt();
     }
        switch (selec) {
            case 0:
                Set(per, 10, 2, 2, 10, 0);
                break;
            case 1:
                Set(per, 7, 4, 0, 13, 2);
                break;
            default:
                Set(per, 8, 3, 1, 12, 0);
                break;
        }
     System.out.println("Selecciona tu sexo: Masculino 0 / Femenino 1");
     selec = in.nextInt();
     while(selec!=0 && selec!=1){
       System.out.println("Selecciona un sexo valido");
       selec = in.nextInt();
     }
     if(selec==0) per.setSexo('M');
     else per.setSexo('F');
     System.out.println("Selecciona tu nombre:");
     a = in.next();
     per.setNAME(a);
       //CONFIRMACIÓN DE SELECCIÓN
     System.out.println("HP: "+per.getHP());
     System.out.println("ATK: "+per.getATK());
     System.out.println("DEF: "+per.getDEF());
     System.out.println("Sanidad: "+per.getSAN());
     System.out.println("Suerte: "+per.getSUE());
     System.out.println("Sexo: "+per.getSexo());
     System.out.println("Nombre: "+per.getNAME());
     System.out.println("Confirmar seleccion");
     System.out.println("Si:1 / No:0");
     val = in.nextInt();
   }while(val!=1);
  }

    //RUTINA DE PELEA
  public static void pelea(Clase per, Enemigo ene, int h, int a, int d, int suerte, int pocion, int bomba){
    setEnemigo(ene, h, a, d, suerte);
    int HPP, HPE;
    HPP=per.getHP(); HPE=ene.getHP();
    while(HPP>0 && HPE>0){
      Random aleatorio = new Random(System.currentTimeMillis()); //RANDOM NUMBER
      int selec=0, defper=per.getDEF(), critico, defene=ene.getDEF(), accu;
      System.out.println("Selecciona una accion"); //TURNO DEL PERSONAJE
      System.out.println("-Atacar: 0 \n-Defender: 1 \n-Objeto: 2 \n-Huir:3");
      do{
        Select(selec);
        if(selec<0 && selec>3){
          System.out.println("Has una seleccion valida");
        }
      }while(selec<0 && selec>3);
      if(selec==0){ //ATAQUE
        accu = aleatorio.nextInt(50);
        if(accu>=10){
          critico = (aleatorio.nextInt(50) + per.getSUE());
          if(critico > 50){
            HPE=HPE-((per.getATK()-((defene)/2))*2);
            System.out.println("Daño CRITICO: "+((per.getATK()-((defene)/2))*2));
          }
          else{
            HPE=HPE-(per.getATK()-((defene)/2));
            System.out.println("Daño efectuado: "+(per.getATK()-((defene)/2)));
          }
        }
        else{
          System.out.println("FALLO");
        }
      }
      else if(selec==1){ //DEFENDER
        defper=defper+(per.getDEF()/2);
      }
      else if(selec==2){ //OBJETO
        objeto(per,pocion,bomba);
        if(objeto(per,pocion,bomba)==0){
          HPP=HPP+5;
        }
        else if(objeto(per,pocion,bomba)==1){
          HPE=HPE-4;
        }
      }
      if(HPE>0){ //TURNO DEL ENEMIGO
        selec = aleatorio.nextInt(10);
        if(selec<=8){
          critico = (aleatorio.nextInt(50) + per.getSUE());
          if(critico > 50){
            HPP=HPP-((ene.getATK()-(defper/2))*2);
            System.out.println("Daño CRITICO: "+(ene.getATK()-(defper/2)));
          }
          else{
            HPP=HPP-(ene.getATK()-((defper)/2));
            System.out.println("Daño efectuado: "+(ene.getATK()-((defper)/2)));
          }
        }
        else{
          System.out.println("FALLO ENEMIGO");
        }
      }
    }
    per.setHP(HPP);
  }
    //MENU DE OBJETOS
  public static int objeto(Clase per, int a, int b){
    int selec=2;
    System.out.println("Selecciona un objeto para usar");
    System.out.println("Pociones: 0 Existecias: " +a);
    System.out.println("Bombas Humo: 1 Existecias: " +b);
    System.out.println("Volver o No hacer nada si hay batalla: 2");
    do{
      Select(selec);
      if(selec<0 && selec>3){
        System.out.println("Has una seleccion valida");
      }
    }while(selec<0 && selec>2);
    if(selec==0 && a!=0){
      return 0;
    }
    else if(selec==1 && b!=0){
      return 1;
    }
    else{
      return 2;
    }
  }
    //SET DE STATS DE ENEMIGO
  public static void setEnemigo(Enemigo ene, int h, int a, int d, int suerte){
    ene.setHP(h); ene.setATK(a); ene.setDEF(d); ene.setSUE(suerte);
  }
  
    //SETEO DE STATS DEL PERSONAJE SEGUN SU CLASE
  public static void Set(Clase per, int h, int a, int d, int s, int suerte){
    per.setHP(h); per.setATK(a); per.setDEF(d); per.setSAN(s); per.setSUE(suerte);
  }
    //SELECCIONES
  public static void Select(int selec){
    Scanner in = new Scanner(System.in);
    selec = in.nextInt();
  }
}  