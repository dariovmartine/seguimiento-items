package edu.unlp.informatica.postgrado.seguimiento;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import edu.unlp.informatica.postgrado.seguimiento.view.DataSourceLocator;
import edu.unlp.informatica.postgrado.seguimiento.view.menu.TabbedPanelPage;

/**
 * @author dariovmartine
 */
public final class LoginPage extends WebPage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6396118920015652356L;

	public LoginPage() {
        this(null);
    }

    public LoginPage(final PageParameters parameters) {
        add(new LoginForm("loginform"));
        add(new HeaderPage("mainNavigation","Seguimiento de items", this));
        DataSourceLocator.getInstance();       
    }

    class LoginForm extends Form {

        /**
		 * 
		 */
		private static final long serialVersionUID = 4851073681114694802L;
		
		private String username;
        private String password;

        public LoginForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
            add(new RequiredTextField("username"));
            add(new PasswordTextField("password"));
            add(new FeedbackPanel("feedback"));
        }

        @Override
        protected void onSubmit() {
            SpringWicketWebSession session = SpringWicketWebSession.getSpringWicketWebSession();
            if (session.signIn(username, password)) {
        		setResponsePage(TabbedPanelPage.class);
            } else {
                error("Usuario erroneo.");
            }
        }

    }
}
