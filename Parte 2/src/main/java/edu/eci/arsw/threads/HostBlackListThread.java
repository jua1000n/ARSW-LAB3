package edu.eci.arsw.threads;

import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HostBlackListThread implements Runnable {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    public Thread myThread;
    int start;
    int end;
    int limit;
    String ipaddress;
    List<Integer> blackListOcurrences;
    int checkedListsCount=0;
    int ocurrencesCount=0;

    public HostBlackListThread(int start, int end, String ipaddress) {

        this.start = start;
        this.end = end;
        this.ipaddress= ipaddress;

        myThread = new Thread(this, "my runnable thread");
        System.out.println("my thread created" + myThread);
    }

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

    @Override
    public void run() {
        LinkedList<Integer> blackListOcurrences= new LinkedList<>();

        ocurrencesCount=0;

        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();

        checkedListsCount = 0;

        for (int i = start; i < end && ocurrencesCount < BLACK_LIST_ALARM_COUNT; i++) {
            checkedListsCount++;

            if (skds.isInBlackListServer(i, ipaddress)) {

                blackListOcurrences.add(i);

                ocurrencesCount++;
            }
        }

        this.blackListOcurrences = blackListOcurrences;

        //LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{start, end});
    }

    public int getCheckedListsCount() {
        return checkedListsCount;
    }

    public void setCheckedListsCount(int checkedListsCount) {
        this.checkedListsCount = checkedListsCount;
    }

    public int getOcurrencesCount() {
        return ocurrencesCount;
    }

    public void setOcurrencesCount(int ocurrencesCount) {
        this.ocurrencesCount = ocurrencesCount;
    }

    public void threadStart() {
        myThread.start();
    }

    public void threadRun() {
        myThread.run();
    }

    public int getStart() {
        return start;
    }

    public void threadJoin() {
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Integer> getBlackListOcurrences() {
        return blackListOcurrences;
    }

    public void setBlackListOcurrences(List<Integer> blackListOcurrences) {
        this.blackListOcurrences = blackListOcurrences;
    }
}
