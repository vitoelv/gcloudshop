/**
* Author: Bob Chen
*/

package com.jcommerce.core.module;

import java.net.URL;
import java.util.Map;

public interface IModule {
    String getCode();
    
    String getDescription();
    
    String getAuthor();
    
    URL getWebSite();
    
    String getVersion();
    
    Map<String, Object> getConfig();
}
