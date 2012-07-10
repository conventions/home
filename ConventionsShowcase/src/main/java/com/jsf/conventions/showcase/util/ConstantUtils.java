/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.util;

/**
 *
 * @author rmpestano Aug 10, 2011 12:02:59 PM
 */
public  class ConstantUtils {
    
    public static final String FACES_REDIRECT = "?faces-redirect=true";
    
     public static final class Role {
       public static final String ROLE_ADMIN = "admin";
       public static final String ROLE_GUEST = "guest";
         
     }
     
      public static final class State {
        public static final String FIND_STATE = "find";
        public static final String INSERT_STATE = "insert";
        public static final String UPDATE_STATE = "update";
        public static final String FRIEND_STATE = "friend";
     }
      
      public static final class Invoker {
        public static final String PERSON_BEAN = "personMBean";
        public static final String PERSON_CONVERSATION_BEAN = "personConversationMBean";
        public static final String PERSON_STATELESS_BEAN = "statelessPersonMBean";
     }

}
