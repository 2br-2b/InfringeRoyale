package org.infpls.royale.server.game.util;

import java.util.*;
import org.infpls.royale.server.util.Oak;

/* The final solution of anti-cheat */
public class VirginSlayer {
  
  private static final int STRIKES = 2;
  
  private static final List<Virgin> VIRGINS = new ArrayList();
  
  /* TODO: I should write this to a file but that would be more work so whatever.*/
  public static final String[] BAN_LIST = new String[] {
    "82.21.54.197",
    "88.90.251.221",
    "2.86.213.204",
    "72.179.247.80",
    "72.179.247.80",
    "104.131.176.234",
    "85.72.94.74",
    "104.236.53.155",
    "155.4.129.252",
    "155.4.129.252",
    "104.131.19.173",
    "79.157.67.69",
    "104.236.205.233",
    "104.236.205.233",
    "138.99.224.119",
    "89.152.216.115",
    "77.16.221.54",
    "77.16.67.80",
    "178.117.132.133",
    "77.16.210.4",
    "159.118.45.128",
    "85.225.155.28",
    "67.174.159.53",
    "77.16.208.130",
    "79.131.10.225",
    "77.16.57.171",
    "77.16.65.228",
    "77.16.67.222",
    "177.98.164.32",
    "77.18.57.197",
    "68.228.78.146",
    "77.16.77.242",
    "77.18.57.35",
    "73.75.66.185",
    "73.75.66.185",
    "77.16.78.80",
    "24.45.243.88",
    "24.45.243.88",
    "173.160.95.142",
    "185.159.82.172",
    "68.43.49.200",
    "70.29.84.251",
    "70.29.84.251",
    "96.27.57.112",
    "70.29.84.251",
    "96.27.57.112",
    "68.43.49.200",
    "68.43.49.200",
    "68.43.49.200",
    "173.44.48.24",
    "24.128.206.112",
    "185.180.197.93",
    "185.180.197.93",
    "173.44.48.47",
    "173.44.48.47",
    "161.129.70.74",
    "107.147.189.248",
    "107.147.189.248",
    "104.200.153.94",
    "199.116.115.134",
    "161.129.70.93",
    "161.129.70.93",
    "209.15.88.32",
    "209.15.88.32",
    "190.47.173.227",
    "174.218.147.167",
    "81.132.62.79",
    "77.111.246.161",
    "139.138.5.115",
    "199.116.115.131",
    "199.116.115.141",
    "199.116.115.136",
    "199.116.115.133",
    "199.116.115.142",
    "199.116.115.144",
    "193.37.253.88",
    "194.59.251.187",
    "194.59.251.187",
    "66.115.169.225",
    "73.244.233.166",
    "199.116.115.140",
    "89.2.151.85",
    "86.1.225.56",
    "193.37.253.132",
    "75.120.15.178",
    "199.66.91.181",
    "68.235.38.62",
    "107.150.30.73",
    "155.94.250.58",
    "178.128.238.51",
    "207.189.25.117",
    "38.132.116.170",
    "173.44.48.62",
    "159.89.136.131",
    "61.5.113.161",
    "174.62.197.184",
    "174.62.197.184",
    "104.3.14.8",
    "83.132.227.3",
    "83.132.227.3",
    "185.174.156.3",
    "185.174.156.3",
    "178.112.190.161",
    "95.90.227.114",
    "95.90.227.114",
    "185.174.156.4",
    "185.174.156.4",
    "104.236.70.228",
    "104.131.92.125",
    "65.19.167.132",
    "46.101.22.182",
    "95.34.165.82",
    "5.146.194.166",
    "5.146.194.166",
    "188.114.170.51",
    "23.111.165.2",
    "23.111.165.2",
    "46.101.16.229",
    "178.62.92.29",
    "104.236.55.136",
    "104.236.195.72",
    "46.101.38.123",
    "94.176.148.227",
    "213.76.55.128",
    "172.83.40.26",
    "173.170.178.198",
    "181.188.160.71",
    "71.164.174.74",
    "70.119.22.153",
    "71.15.199.194",
    "73.8.173.206",
    "75.84.160.165"
  };
  
  public static synchronized void strike(String ip) {
    try { 
      final String ipt = ip.substring(1).split(":")[0].trim(); // Cut string to just IP

      final Virgin v = get(ipt);
      if(v != null) {
        if(++v.strikes >= STRIKES) { Oak.log(Oak.Level.CRIT, "PERM BANNED IP: " + ipt); }
      }
      else {
        VIRGINS.add(new Virgin(ipt));
      }
      
    }
    catch(Exception ex) {
      Oak.log(Oak.Level.ERR, "Error while striking : " + ip, ex);
    }
  }
  
  private static Virgin get(String ipt) {
    for(int i=0;i<VIRGINS.size();i++) {
      final Virgin v = VIRGINS.get(i);
      if(v.ipt.equals(ipt)) { return v; }
    }
    return null;
  }
  
  public static boolean isBanned(String ip) {
    try { 
      final String ipt = ip.substring(1).split(":")[0].trim(); // Cut string to just IP
      
      for(int i=0;i<BAN_LIST.length;i++) {
        if(BAN_LIST[i].equals(ipt)) { return true; }
      }
      
      final Virgin v = get(ipt);
      if(v != null) { return v.strikes >= STRIKES; }
    }
    catch(Exception ex) {
      Oak.log(Oak.Level.ERR, "Error while checking ban for : " + ip, ex);
    }
    return false; // Good faith I guess...
  }
  
  
  private static class Virgin {
    public final String ipt;
    public int strikes;
    public Virgin(String ipt) {
      this.ipt = ipt;
      strikes = 1;
    }
  }
}
