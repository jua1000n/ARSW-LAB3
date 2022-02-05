/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.HostBlackListThread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    HostBlacklistsDataSourceFacade skds;
    List<Integer> rangeThread;
    List<Integer> report= new ArrayList<Integer>();
    List<HostBlackListThread> myThreads = new ArrayList<>();
    int ocurrencesCount=0;
    int checkedListsCount=0;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        
        ocurrencesCount=0;
        
        skds = HostBlacklistsDataSourceFacade.getInstance();
        
        checkedListsCount=0;
        
        for (int i=0;i<skds.getRegisteredServersCount() && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
            checkedListsCount++;
            
            if (skds.isInBlackListServer(i, ipaddress)){
                
                blackListOcurrences.add(i);
                
                ocurrencesCount++;
            }
        }
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }

    public List<Integer> checkHost(String ipaddress, int n) {

        skds = HostBlacklistsDataSourceFacade.getInstance();
        div(n);
        ArrayList<HostBlackListThread> hostThread = new ArrayList<HostBlackListThread>();

        for(int i= 0;i< n;i++) {
            myThreads.add(new HostBlackListThread(rangeThread.get(i), rangeThread.get(i+1), ipaddress));
        }

        for(int i= 0;i< n; i++) {
            myThreads.get(i).threadStart();
        }

        //Espera a que termine los subProcesos para que pueda dar el reporte sin dar error
        for(int i= 0;i< n; i++) {
            myThreads.get(i).threadJoin();
        }

        for(int i= 0;i< n; i++) {
            report.addAll(myThreads.get(i).getBlackListOcurrences());
            checkedListsCount+= myThreads.get(i).getCheckedListsCount();
        }

        if (report.size()>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{

            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});

        return report;
    }

    public void div(int num) {

        rangeThread= new ArrayList<>();
        int resi = skds.getRegisteredServersCount()%num;
        for(int i= 0; i<= num; i++) {
            if(i == num) {
                rangeThread.add((skds.getRegisteredServersCount()/num)*i+resi);
            } else {
                rangeThread.add((skds.getRegisteredServersCount()/num*i));
            }
        }

    }
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

}
