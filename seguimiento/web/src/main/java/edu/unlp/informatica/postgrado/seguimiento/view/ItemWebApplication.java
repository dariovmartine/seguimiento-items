package edu.unlp.informatica.postgrado.seguimiento.view;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Controller;

@Controller("wicketApplication") 
public class ItemWebApplication extends WebApplication {
	    
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<HelloWorld> getHomePage() {
        return HelloWorld.class;
    }
}