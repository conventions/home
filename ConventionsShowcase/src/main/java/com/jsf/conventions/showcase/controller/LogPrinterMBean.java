/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.util.MessagesController;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author rmpestano Jul 4, 2011 9:56:05 PM
 */
@SessionScoped
@Named("logPrinterMBean")
public class LogPrinterMBean implements Serializable{
    private String logPath = "";
    private String logContent = "";
    private BufferedReader br;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        logPath = fc.getExternalContext().getInitParameter("LOG_PATH");
        if(logPath != null){
            br = getLastLine();
        }
    }
    
    public void changeLocation(){
        br = getLastLine();
        if(br != null){
            MessagesController.addInfo("Log file changed!");
        }
    }
    

    public String getLogContent() throws IOException {
      if(br == null){
          return logContent;
      }
      String tmp = null;
             while ((tmp = br.readLine()) != null) {
                 if(tmp.contains("INFO") && tmp.contains("Hibernate")){
                    logContent += tmp.substring(tmp.lastIndexOf("Hibernate"))+"\n";
                 }
             }
       return logContent;
    }
    
    public void clear(){
        logContent = "";
    }
    
    public void handleClose() throws IOException{
        if(br != null){
            br.close();
        }
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }
     
     @PreDestroy
     public void close() {
        if(br != null){
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(LogPrinterMBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
     
    
    public BufferedReader getLastLine() {
        try {
             FileInputStream in = new FileInputStream(logPath);
             BufferedReader br = new BufferedReader(new InputStreamReader(in));
             String strLine = null, tmp;
             while ((tmp = br.readLine()) != null) {
                strLine = tmp;
             }
             return br;
         } catch (IOException x) {
             logContent = "Log file not found, please provide a valid log path";
             return null;
         }
    }
    
}
