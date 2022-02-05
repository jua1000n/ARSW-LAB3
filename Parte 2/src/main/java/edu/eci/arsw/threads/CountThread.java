/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThread implements Runnable {

    Thread myThread;
    public int ini;
    public int end;

    public CountThread(int inicio, int fin) {
        ini = inicio;
        end = fin;
        System.out.println(inicio +" "+fin);
        myThread = new Thread(this, "my runnable thread");
        System.out.println("my thread created" + myThread);
        //threadStart();
        //threadRun();
    }

    @Override
    public void run() {
            try
            {
                for (int i=ini ;i<end ;i++) {
                    System.out.println(i);
                    Thread.sleep(100);
                }
            }
            catch(InterruptedException e)
            {
                System.out.println("my thread interrupted");
            }
            System.out.println("mythread run is over" );
        }

    public void threadStart() {
        myThread.start();
    }

    public void threadRun() {
        myThread.run();
    }
}




