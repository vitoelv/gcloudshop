package com.jcommerce.web.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    
    String user;
    String passwd;
    
    public MyAuthenticator(String user, String passwd) {
        this.user = user;
        this.passwd = passwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        // TODO enhance
        return new PasswordAuthentication(user, passwd);
    }

}
