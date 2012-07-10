/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.util;

/**
 *
 * @author rmpestano
 */
public class Pages {

    /**
     * Pages controlled by TrackableBean
     */ 
    public static final class History {

        public static final String EDIT_PAGE = "/pages/history/addUser.faces";
        public static final String LIST_PAGE = "/pages/history/listUser.faces";
        public static final String FRIEND_PAGE = "/pages/history/addFriend.faces";
        public static final String HOME = "/pages/history/historyStackHome.faces";
    }
    public static final class AjaxHistory {

        public static final String EDIT_PAGE = "/pages/history/ajaxAddUser.faces";
        public static final String LIST_PAGE = "/pages/history/ajaxListUser.faces";
        public static final String FRIEND_PAGE = "/pages/history/ajaxAddFriend.faces";
        public static final String HOME = "/pages/history/ajaxHistoryHome.faces";
    }
    
    /**
     * Pages controlled by TrackableBean2
     */
    public static final class History2 {

        public static final String EDIT_PAGE = "/pages/history/addUser2.faces";
        public static final String LIST_PAGE = "/pages/history/listUser2.faces";
        public static final String FRIEND_PAGE = "/pages/history/addFriend2.faces";
        public static final String HOME = "/pages/history/historyStackHome.faces";
    }
    /**
     * Pages controlled by SecurityMBean
     */
    public static final class Security {

        public static final String EDIT_PAGE = "/pages/security/addUser.faces";
        public static final String LIST_PAGE = "/pages/security/listUser.faces";
        public static final String HOME_PAGE = "/pages/security/securityHome.faces";
        public static final String SECRET_PAGE = "/pages/security/secret.faces";
    }
}
