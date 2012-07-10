/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.model.Theme;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Rafael M. Pestano Apr 5, 2011 8:37:24 PM
 */
@Named("themeMBean")
@SessionScoped
public class ThemeMBean implements Serializable{
    private Theme theme;
    private String evenRow;
    private List<Theme> themeList;
    
    
    @PostConstruct
    public void initThemes(){
        themeList = new ArrayList<Theme>();
        themeList.add(new Theme("aristo", "aristo.png"));
        themeList.add(new Theme("afterdark", "afterdark.png"));
        themeList.add(new Theme("blitzer", "blitzer.png"));
        themeList.add(new Theme("bluesky", "bluesky.png"));
        themeList.add(new Theme("casablanca", "casablanca.png"));
        themeList.add(new Theme("cupertino", "cupertino.png"));
        themeList.add(new Theme("flick", "flick.png"));
        themeList.add(new Theme("glass-x", "glass-x.png"));
        themeList.add(new Theme("home", "home.png"));
        themeList.add(new Theme("overcast", "overcast.png"));
        themeList.add(new Theme("redmond", "redmond.png"));
        themeList.add(new Theme("start", "start.png"));
        themeList.add(new Theme("sunny", "sunny.png"));
        themeList.add(new Theme("trontastic", "trontastic.png"));
        themeList.add(new Theme("ui-darkness", "ui-darkness.png"));
//        themeList.add(new Theme("ui-lightness", "ui-lightness.png"));
        themeList.add(new Theme("vader", "vader.png"));
        theme = this.getThemeByName("afterDark");
    }

    public List<Theme> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<Theme> themeList) {
        this.themeList = themeList;
    }
    
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getEvenRow() {
        evenRow = "#E9E9E9";
        if(theme.getName().equalsIgnoreCase("ui-darkness") ){
            evenRow = "#474747";
        }
        
        if(theme.getName().equalsIgnoreCase("afterdark")){
            evenRow = "#606060";
        }
        if(theme.getName().equalsIgnoreCase("vader")){
            evenRow = "#505050";
        }
        if(theme.getName().equalsIgnoreCase("bluesky")){
            evenRow = "#E5EEFC";
        }
        if(theme.getName().equalsIgnoreCase("cupertino")){
            evenRow = "#E6F2FB";
        }
        if(theme.getName().equalsIgnoreCase("redmond")){
            evenRow = "#87B6D9";
        }
        if(theme.getName().equalsIgnoreCase("sunny")){
            evenRow = "#feeebd";
        }
        if(theme.getName().equalsIgnoreCase("trontastic")){
            evenRow = "#262626";
        }
        return evenRow;
    }

    public void setEvenRow(String evenRow) {
        this.evenRow = evenRow;
    }

  public void saveTheme() {
        setTheme(theme);
    }
   
  public Theme getThemeByName(String themeName){
      for (Theme theme : themeList) {
          if(theme.getName().equalsIgnoreCase(themeName)){
              return theme;
          }
      }
      return null;
  }
  
}
