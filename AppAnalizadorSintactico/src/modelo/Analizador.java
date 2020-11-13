/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author spart
 */
public class Analizador {
    private  ArrayList<String> listaTokens;
    private Stack<String> pila;
    private ArrayList<String> historiaPila;
    boolean fParametro;
    boolean f2;
    private ArrayList<Renglon> listaSalida;
    public Analizador(ArrayList<String> listaTokens) {
        this.listaTokens=acortarLista(listaTokens);
        pila= new Stack<String>();
        fParametro = false;
        f2=false;
        historiaPila = new ArrayList<String>();
        listaSalida = new ArrayList<Renglon>();
        generarPila();
        
    }
    public void verPila(){
        Stack<String> pilaTemporal = (Stack<String>) pila.clone();
        Stack<String> pila3 = new Stack<String>();
        int tamTemporal = pilaTemporal.size();
        for (int i = 0; i < tamTemporal; i++) {
            pila3.push(pilaTemporal.pop());
        }
        String par = "";
        int max = pila3.size();
        for (int i = 0; i < max; i++) {
            par= par +" "+ pila3.pop();
        }
        historiaPila.add(par);
    }
    public void generarPila(){
        int numRenglon = 0;
        String temporal = "";
        boolean fprimerDiferente = false;
        boolean fprimerCodigo = true;
        int tope = listaTokens.size();
        tope = tope+5;
        for (int cont = 0; cont < tope; cont++) {
            String listaToken="";
            if (cont<listaTokens.size()) {
                listaToken = listaTokens.get(cont);
                pila.push(listaToken);
                verPila();
            }
            
        //}
        //for (String listaToken : listaTokens) {
             
            System.out.println("Pila "+pila.peek());
             if (esInicio()) {
                 
                 temporal =pila.pop();
                 numRenglon++;
                 listaSalida.add(generarRenglon(numRenglon, temporal));
                 temporal =pila.pop();
                 numRenglon++;
                 listaSalida.add(generarRenglon(numRenglon, temporal));
                 pila.push("INICIO");
             System.out.println("entro if"+pila.peek());
             verPila();
            }
             if (esTipo()) {
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                pila.push("TIPO");
                System.out.println("entro if2"+pila.peek());
                verPila();
            }  
             /*
             if (esValores()) {
                pila.pop();
                pila.pop();
                pila.pop();
                pila.push("VALORES");
                System.out.println("entro if3"+pila.peek());
            }
             */
             if (esValor()) {
               temporal = pila.pop();
               numRenglon++;
               listaSalida.add(generarRenglon(numRenglon, temporal));
                pila.push("VALOR");
                System.out.println("entro if4"+pila.peek());
                verPila();
            }
             
             for (int i = 0; i < 10; i++) {
                
               if (esParametro()) {
                 if (fParametro) {
                pila.pop();
                pila.pop();
                pila.pop();
                pila.push("PARAMETRO");
                verPila();
                 }else{
                pila.pop();
                pila.pop();
                pila.push("PARAMETRO");
                verPila();
                 }
                 
            } 
            }
             /*
             if (esParenteseis()) {
                pila.pop();
                pila.pop();
                pila.pop();
                pila.push("PARENTESIS");
                System.out.println("entro if6"+pila.peek());
            }
             */
             if (esTipoOpe()) {
                pila.pop();
                pila.push("TIPOOPE");
                System.out.println("entro if7"+pila.peek());
                verPila();
            }
             if (esOperacion()) {
                pila.pop();
                pila.pop();
                pila.push("OPERACION");
                System.out.println("entro if8"+pila.peek());
                verPila();
            }
             /*
             if (esListaOpe()) {
                pila.pop();
                pila.pop();
                pila.push("LISTAOPE");
                System.out.println("entro if9"+pila.peek());
            }
             */
             /*
             if (esAr()) {
                pila.pop();
                pila.pop();
                pila.pop();
                pila.push("AR");
                System.out.println("entro if10"+pila.peek());
            }*/
             if (esAsig()) {
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                generarPadre("tk_num", numRenglon);
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                generarPadre("tk_id", numRenglon);
                pila.push("ASIG");
                System.out.println("entro if11"+pila.peek());
                verPila();
            }
             if (esIdasig()) {
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                 if (f2) {
                     generarPadre("VALOR", numRenglon);
                     generarPadre("tk_=", numRenglon);
                     generarPadre("IDASIG", numRenglon);
                     f2=false;
                 }
                pila.push("IDASIG");
                System.out.println("entro if12"+pila.peek());
                verPila();
            }
             
             if (esFuncion()) {
                pila.pop();
                pila.push("FUNCION");
                System.out.println("entro if13"+pila.peek());
                verPila();
            }
             if (esLW()) {
                pila.pop();
                pila.pop();
                pila.pop();
                pila.push("LW");
                 System.out.println("entro if14"+pila.peek());
                 verPila();
            }
             
             for (int i = 0; i < 10; i++) {
                if (esLista()) {
                 if (fParametro) {
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                     if (fprimerDiferente) {
                         generarPadre("tk_;", numRenglon);
                         generarPadre("IDASIG", numRenglon);
                         fprimerDiferente=false;
                     }else{
                         generarPadre("LISTA", numRenglon);
                         generarPadre("tk_,", numRenglon);
                         generarPadre("IDASIG", numRenglon);
                     }
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                     generarPadreDosOpciones("tk_id","ASIG", numRenglon);
                
                pila.push("LISTA");
                System.out.println("entro for"+pila.peek());
                verPila();
                 }else{
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                     generarPadreDosOpciones("tk_id", "ASIG", numRenglon);
                //generarPadre("tk_id", numRenglon);
                pila.push("LISTA");
                fprimerDiferente = true;
                 System.out.println("entro for"+pila.peek());
                 verPila();
                 }
            }
                
            }
             if (esDeclaracion()) {
                temporal = pila.pop();
                numRenglon++;
                listaSalida.add(generarRenglon(numRenglon, temporal));
                 generarPadre("LISTA", numRenglon);
                 generarPadre("tk_,", numRenglon);
                 generarPadre("IDASIG", numRenglon);
                 temporal = pila.pop();
                 numRenglon++;
                 listaSalida.add(generarRenglon(numRenglon, temporal));
                 generarPadreDosOpciones("tk_INTEGER", "tk_REAL", numRenglon);
                 pila.push("DECLARACION");
                System.out.println("entro if15"+pila.peek());
                verPila();
            }
             
            
                if (esIdope()) {
                // if (fParametro) {
                    pila.pop();
                    pila.pop();
                    pila.pop();
                    pila.push("IDOPE");
                System.out.println("entro if16"+pila.peek()); 
                verPila();
                /* }else{
                    pila.pop();
                    pila.pop();
                    pila.push("IDOPE");
                System.out.println("entro if16"+pila.peek());  
                verPila();
                 }*/
                
            }
            
             if (esFin()) {
                pila.pop();
                pila.pop();
                pila.push("FIN");
                System.out.println("entro if16"+pila.peek());
                verPila();
            }
            for (int i = 0; i < 10; i++) {
                if (esCodigo()) {
                    if (fParametro) {
                        temporal = pila.pop();
                        numRenglon++;
                        listaSalida.add(generarRenglon(numRenglon, temporal));
                        if (f2) {
                            if (fprimerCodigo) {
                                generarPadreDosOpciones("DECLARACION", "LW", numRenglon);
                                temporal =pila.pop();
                                numRenglon++;
                                listaSalida.add(generarRenglon(numRenglon, temporal));
                                generarPadreDosOpciones("DECLARACION", "LW", numRenglon);
                                pila.push("CODIGO");
                            } else {
                                generarPadre("tk_;", numRenglon);
                                generarPadre("IDOPE", numRenglon);
                                temporal =pila.pop();
                                numRenglon++;
                                listaSalida.add(generarRenglon(numRenglon, temporal));
                                generarPadre("CODIGO", numRenglon);
                                pila.push("CODIGO");
                            }
                        }else{
                            if (fprimerCodigo) {
                                generarPadreDosOpciones("DECLARACION", "LW", numRenglon);
                                temporal =pila.pop();
                                numRenglon++;
                                listaSalida.add(generarRenglon(numRenglon, temporal));
                                generarPadreDosOpciones("DECLARACION", "LW", numRenglon);
                                pila.push("CODIGO");
                            } else {
                               generarPadreDosOpciones("DECLARACION", "LW", numRenglon);
                               temporal =pila.pop();
                                numRenglon++;
                                listaSalida.add(generarRenglon(numRenglon, temporal));
                                generarPadre("CODIGO", numRenglon);
                                pila.push("CODIGO");
                            }
                        }
                        
                        
                        fParametro=false;
                    }else{
                        temporal = pila.pop();
                        numRenglon++;
                        listaSalida.add(generarRenglon(numRenglon, temporal));
                        if (temporal.equals("DECLARACION")) {
                            generarPadre("LISTA", numRenglon);
                             generarPadre("TIPO", numRenglon);
                        }
                        if (temporal.equals("LW")) {
                            generarPadre("tk_;", numRenglon);
                             generarPadre("PARAMETRO", numRenglon);
                             generarPadre("FUNCION", numRenglon);
                        }
                        pila.push("CODIGO");
                        
                }
                  verPila();  
                }
            }
             if (esPrograma()) {
                pila.pop();
                pila.pop();
                pila.pop();
                pila.push("PROGRAMA");
                verPila();
            }
        }
    }
    public boolean esIdope(){
        if (pila.peek().equals("OPERACION")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_=")) {
               String b = pila.pop();
                if (pila.peek().equals("IDASIG")) {
                    pila.push(b);
                    pila.push(a);
                    fParametro=true;
                return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                pila.push(a);
            }
        }else{
            
        }/*
        if (pila.peek().equals("tk_;")) {
            String a = pila.pop();
            if (pila.peek().equals("IDOPE")) {
                pila.push(a);
                fParametro=false;
                return true;
            }else{
                pila.push(a);
            }
        }else{
            
        }*/
        return false;
    }
    public boolean esCodigo(){
        if (pila.peek().equals("CODIGO")) {
            String a = pila.pop();
            if (pila.peek().equals("CODIGO")) {
                pila.push(a);
                fParametro=true;
                f2=true;
                return true;
            }else{
                pila.push(a);
            }
        }else{
            
        }
        if (pila.peek().equals("tk_;")) {
            String a = pila.pop();
            if (pila.peek().equals("IDOPE")) {
                pila.push(a);
                fParametro=true;
                return true;
            }else{
                pila.push(a);
            }
        }else{
            
        }
        if (pila.peek().equals("DECLARACION")) {
            fParametro = false;
            return true;
        }/*
        if (pila.peek().equals("IDOPE")) {
            fParametro = false;
            return true;
        }*/
        if (pila.peek().equals("LW")) {
            fParametro = false;
            return true;
        }
        
        return false;
    }
    public boolean esPrograma(){
        if (pila.peek().equals("FIN")) {
            String a = pila.pop();
            if (pila.peek().equals("CODIGO")) {
                String b = pila.pop();
                if (pila.peek().equals("INICIO")) {
                    pila.push(b);
                    pila.push(a);
                    fParametro = true;
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
        return false;
    }
    public boolean esLista(){
        if (pila.peek().equals("LISTA")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_,")) {
                String b = pila.pop();
                if (pila.peek().equals("IDASIG")) {
                    pila.push(b);
                    pila.push(a);
                    fParametro = true;
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
        if (pila.peek().equals("tk_;")) {
            String a = pila.pop();
             if (pila.peek().equals("IDASIG")) {
                  pila.push(a);
                  fParametro = false;
                  
            return true;
        }else{
                  pila.push(a);
             }
        }
        return false;
    }
    
    public boolean esIdasig(){
        if (pila.peek().equals("tk_id")) {
            return true;
        }
         if (pila.peek().equals("ASIG")) {
             f2 = true;
            return true;
        }
         return false;
    }
    public boolean esAsig(){
        if (pila.peek().equals("VALOR")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_=")) {
                String b = pila.pop();
                if (pila.peek().equals("IDASIG")) {
                    pila.push(b);
                    pila.push(a);
                   
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
         if (pila.peek().equals("OPERACION")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_=")) {
                String b = pila.pop();
                if (pila.peek().equals("tk_id")) {
                    pila.push(b);
                    pila.push(a);
                    
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
        return false;
    }
    public boolean esAr(){
        if (pila.peek().equals("tk_;")) {
            String a = pila.pop();
            if (pila.peek().equals("LISTAOPE")) {
                String b = pila.pop();
                if (pila.peek().equals("tk_id")) {
                    pila.push(b);
                    pila.push(a);
                    
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
        return false;
    }
    public boolean esListaOpe(){
        if (pila.peek().equals("OPERACION")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_=")) {
                pila.push(a);
                return true;
            }else{
                pila.push(a);
                return false;
            }
        }else{
            return false;
        }
    }
    public boolean esOperacion(){
        /*
        if (pila.peek().equals("PARENTESIS")) {
            String a = pila.pop();
            if (pila.peek().equals("TIPOOPE")) {
                pila.push(a);
                return true;
            }else{
                pila.push(a);
                return false;
            }
        }else{
            return false;
        }
        */
        if (pila.peek().equals("PARAMETRO")) {
            String a = pila.pop();
            if (pila.peek().equals("TIPOOPE")) {
                pila.push(a);
                return true;
            }else{
                pila.push(a);
            }
        }else{
            
        }
        if (pila.peek().equals("tk_;")) {
            String a = pila.pop();
            if (pila.peek().equals("PARAMETRO")) {
                String b = pila.pop();
                if (pila.peek().equals("TIPOOPE")) {
                    pila.push(b);
                    pila.push(a);
                return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                pila.push(a);
            }
        }else{
            
        }
        return false;
    }
    public boolean esTipoOpe(){
        if (pila.peek().equals("tk_ADD")) {
            return true;
        }
         if (pila.peek().equals("tk_SUB")) {
            return true;
        }
          if (pila.peek().equals("tk_MUL")) {
            return true;
        }
           if (pila.peek().equals("tk_DIV")) {
            return true;
        }
           return false;
    }
    public boolean esLW(){
        if (pila.peek().equals("tk_;")) {
            String a = pila.pop();
            if (pila.peek().equals("PARAMETRO")) {
                String b = pila.pop();
                if (pila.peek().equals("FUNCION")) {
                    pila.push(b);
                    pila.push(a);
                    
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
        return false;
    }
    public boolean esParenteseis(){   //POSIBLE ERROR PORQUE PODRIA FALTAR (LISTA)
        if (pila.peek().equals("tk_)")) {
            String a = pila.pop();
            if (pila.peek().equals("PARAMETRO")) {
                String b = pila.pop();
                if (pila.peek().equals("tk_(")) {
                    pila.push(b);
                    pila.push(a);
                    
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
        
        if (pila.peek().equals("tk_)")) {
            String a = pila.pop();
            if (pila.peek().equals("IDASIG")) {
                String b = pila.pop();
                if (pila.peek().equals("tk_(")) {
                    pila.push(b);
                    pila.push(a);
                    
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
        return false;
    }
    
    public boolean esParametro(){
        /*
        if (pila.peek().equals("tk_id")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_,")) {
                String b = pila.pop();
                if (pila.peek().equals("tk_id")) {
                    pila.push(b);
                    pila.push(a);
                    fParametro = false;
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
        
        if (pila.peek().equals("VALORES")) {
            fParametro = true;
            return true;
            
        }
        if (pila.peek().equals("OPERACION")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_,")) {
                String b = pila.pop();
                if (pila.peek().equals("tk_id")) {
                    pila.push(b);
                    pila.push(a);
                    fParametro = false;
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                 pila.push(a);
            }
        }
       return false; 
       */
        if (pila.peek().equals("tk_)")) {
            String a = pila.pop();
            if (pila.peek().equals("IDASIG")) {
                pila.push(a);
                fParametro = false;
                return true;
            }else{
                pila.push(a);
            }
        }else{
            
        }
        if (pila.peek().equals("tk_)")) {
            String a = pila.pop();
            if (pila.peek().equals("VALOR")) {
                pila.push(a);
                fParametro = false;
                return true;
            }else{
                pila.push(a);
            }
        }else{
            
        }
        if (pila.peek().equals("PARAMETRO")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_,")) {
                String b = pila.pop();
                if (pila.peek().equals("IDASIG")) {
                    pila.push(b);
                    pila.push(a);
                    fParametro = true;
                return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                pila.push(a);
            }
        }else{
            
        }
        if (pila.peek().equals("PARAMETRO")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_,")) {
                String b = pila.pop();
                if (pila.peek().equals("VALOR")) {
                    pila.push(b);
                    pila.push(a);
                    fParametro = true;
                return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                }
            }else{
                pila.push(a);
            }
        }else{
            
        }
        if (pila.peek().equals("PARAMETRO")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_(")) {
                pila.push(a);
                fParametro=false;
                return true;
            }else{
                pila.push(a);
            }
        }else{
            
        }
        
        if (pila.peek().equals("tk_)")) {
            String a = pila.pop();
            if (pila.peek().equals("OPERACION")) {
                pila.push(a);
                fParametro = false;
                return true;
            }else{
                pila.push(a);
            }
        }else{
            
        }
        return false;
    }
    public boolean esValores(){
        if (pila.peek().equals("VALOR")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_,")) {
                String b = pila.pop();
                if (pila.peek().equals("tk_id")) {
                    pila.push(b);
                    pila.push(a);
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                    
                }
            }else{
                pila.push(a);
                
            }
        }
        if (pila.peek().equals("tk_id")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_,")) {
                String b = pila.pop();
                if (pila.peek().equals("VALOR")) {
                    pila.push(b);
                    pila.push(a);
                    return true;
                }else{
                    pila.push(b);
                    pila.push(a);
                    
                }
            }else{
                pila.push(a);
                
            }
        }
        
        return false;
    }
    public boolean esValor(){
        if (pila.peek().length()>=5) {
            //if (pila.peek().substring(0,5).equals("tk_num")) {
            if (pila.peek().equals("tk_num")) {
            return true;
        }
        }
        
        return false;
    }
    public boolean esTipo(){
        if (pila.peek().equals("tk_INTEGER")) {
            return true;
        }
        if (pila.peek().equals("tk_REAL")) {
            return true;
        }
       return false;
    }
    public boolean esFuncion(){
        if (pila.peek().equals("tk_READ")) {
            return true;
        }
        if (pila.peek().equals("tk_WRITE")) {
            return true;
        }
       return false;
    }
    public boolean esDeclaracion(){
        if (pila.peek().equals("LISTA")) {
            String a = pila.pop();
            if (pila.peek().equals("TIPO")) {
                pila.push(a);
                return true;
            }else{
                pila.push(a);
                return false;
            }
        }else{
            return false;
        }
    }
    public boolean esInicio(){
        if (pila.peek().equals("tk_{")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_BEGIN")) {
                pila.push(a);
                return true;
            }else{
                pila.push(a);
                return false;
            }
        }else{
            return false;
        }
        
    }
    public boolean esFin(){
        if (pila.peek().equals("tk_END")) {
            String a = pila.pop();
            if (pila.peek().equals("tk_}")) {
                pila.push(a);
                return true;
            }else{
                pila.push(a);
                return false;
            }
        }else{
            return false;
        }
        
    }
    
    
    
    public ArrayList<String> acortarLista(ArrayList<String> a){
        System.out.println("llamada a acortar");
        int conta=0;
        int contb=0;
        ArrayList<String> listaAcortada = new ArrayList<String>();
        for (String palabra : a) {
            
            for (int i = palabra.length()-3; i > 0; i--) {
              
                if (palabra.charAt(i)==' ') {
                    listaAcortada.add(palabra.substring(i+1, palabra.length()-2));
                    
                    i=0;
                }
            }
        }
        return listaAcortada;
    }
    public String MostrarLista(ArrayList lista){
      String a="";
      if (!lista.isEmpty()) {
          for (int i = 0; i < lista.size(); i++) {
              a+=lista.get(i).toString()+"\n";
          }
      }
      return a;
  }

    public ArrayList<String> getListaTokens() {
        return listaTokens;
    }

    public Stack<String> getPila() {
        return pila;
    }

    public ArrayList<String> getHistoriaPila() {
        return historiaPila;
    }

    public boolean isfParametro() {
        return fParametro;
    }
    
    public Renglon generarRenglon(int num,String lexema){
        Renglon renglon = new Renglon(num, lexema);
        return renglon;
    }
    public void generarPadre(String palabra,int num){
        for (int i = listaSalida.size()-2; i > 0; i--) {
            if (listaSalida.get(i).getLexema().equals(palabra) && listaSalida.get(i).isFpadre()==false) {
                listaSalida.get(i).setPadre(num);
                listaSalida.get(i).setFpadre(true);
                break;
            }
        }
    }
    
    public void generarPadreDosOpciones(String palabra,String text,int num){
        for (int i = listaSalida.size()-2; i > 0; i--) {
            if (listaSalida.get(i).getLexema().equals(palabra) && listaSalida.get(i).isFpadre()==false ||   listaSalida.get(i).getLexema().equals(text) && listaSalida.get(i).isFpadre()==false  ) {
                listaSalida.get(i).setPadre(num);
                listaSalida.get(i).setFpadre(true);
                break;
            }
        }
    }

    public ArrayList<Renglon> getListaSalida() {
        return listaSalida;
    }
    
    
    
}

