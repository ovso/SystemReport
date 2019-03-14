package io.github.ovso.systemreport.utils;

import java.io.IOException;
import java.io.InputStream;

public final class Devices {

  public static String ReadCPUinfo()
  {
    ProcessBuilder cmd;
    String result="";

    try{
      String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
      cmd = new ProcessBuilder(args);

      Process process = cmd.start();
      InputStream in = process.getInputStream();
      byte[] re = new byte[1024];
      while(in.read(re) != -1){
        System.out.println(new String(re));
        result = result + new String(re);
      }
      in.close();
    } catch(IOException ex){
      ex.printStackTrace();
    }
    return result;
  }
}
