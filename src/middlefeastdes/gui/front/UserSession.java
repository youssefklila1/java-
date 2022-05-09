/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeastdes.gui.front;

import middlefeastdes.entity.User;


public final class UserSession {
    
    private static UserSession instance;

    private User connectedUser=null;

    private UserSession(User connectedUser) {
        this.connectedUser = connectedUser;
        
    }

    public static UserSession getInstace() {
        if(instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }
    
    

    UserSession() {
    }


    public void cleanUserSession() {
       connectedUser = null;
    }
    
}
