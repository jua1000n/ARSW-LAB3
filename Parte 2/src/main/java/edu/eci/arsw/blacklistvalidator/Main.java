/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;


import edu.eci.arsw.threads.CountThread;
import edu.eci.arsw.threads.HostBlackListThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {
    
    public static void main(String a[]){
        /*HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences=hblv.checkHost("200.24.34.55");
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);*/

        //Parte 1.1
        //CountThread countThread = new CountThread(0, 99);

        //Parte 1.2.1
        /*CountThread countThread1 = new CountThread(0, 99);
        CountThread countThread2 = new CountThread(99, 199);
        CountThread countThread3 = new CountThread(200, 299);
        countThread1.threadStart();
        countThread2.threadStart();
        countThread3.threadStart();*/

        //Parte 1.2.2
        /*CountThread countThread1 = new CountThread(0, 99);
        CountThread countThread2 = new CountThread(99, 199);
        CountThread countThread3 = new CountThread(200, 299);
        countThread1.threadRun();
        countThread2.threadRun();
        countThread3.threadRun();*/

        //Parte 2
        /*HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences=hblv.checkHost("200.24.34.55", 7);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);*/

        //Parte 3

        HostBlackListsValidator hblv=new HostBlackListsValidator(); //---------------Para pruebas de la parte tres descomentar-----------------------------------------------------------------

        //3.1 Un solo hilo. En este caso como esta corriendo con un hilo se demora aproximadamente 132.926 segundos
        /*long startTime = System.currentTimeMillis();
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55", 1);
        System.out.println(Double.valueOf(System.currentTimeMillis()-startTime)/1000+" Segundos");
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);*/

        //3.2 Tantos hilos como núcleos de procesamiento (haga que el programa determine esto haciendo uso del API Runtime). En este caso mi pc tiene 4 cores se demora 31.656 segundos aproximadamente
        /*int tCores = Runtime.getRuntime().availableProcessors();
        long startTime = System.currentTimeMillis();
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55", tCores);
        System.out.println(Double.valueOf(System.currentTimeMillis()-startTime)/1000+" Segundos, numero de cores"+tCores);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);*/

        //3.3 Tantos hilos como el doble de núcleos de procesamiento. En este caso mi pc tiene 4 cores ósea toca poner 8 hilos se demora 18.744 segundos aproximadamente
        /*int tCores = Runtime.getRuntime().availableProcessors()*2;
        long startTime = System.currentTimeMillis();
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55", tCores);
        System.out.println(Double.valueOf(System.currentTimeMillis()-startTime)/1000+" Segundos, numero de cores"+(tCores));
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);*/

        //3.4 50 hilos. se demora aproximadamente 3.358 segundos
        long startTime = System.currentTimeMillis();
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55", 50);
        System.out.println(Double.valueOf(System.currentTimeMillis()-startTime)/1000+" Segundos");
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);

        //3.4 100 hilos. se demora aproximadamente 1.819 segundos
        /*long startTime = System.currentTimeMillis();
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55", 100);
        System.out.println(Double.valueOf(System.currentTimeMillis()-startTime)/1000+" Segundos");
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);*/

        
    }
    
}
