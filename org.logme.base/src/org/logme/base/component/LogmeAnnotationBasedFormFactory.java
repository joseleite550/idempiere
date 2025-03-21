package org.logme.base.component;

import org.adempiere.webui.factory.AnnotationBasedFormFactory;
import org.adempiere.webui.factory.IFormFactory;
import org.osgi.service.component.annotations.Component;

/**
*
* @author Jose.Leite (jose.franklin550@gmail.com)
*
*/
@Component(immediate = true, service = IFormFactory.class, property = { "service.ranking:Integer=1" })
public class LogmeAnnotationBasedFormFactory extends AnnotationBasedFormFactory {

	@Override
	protected String[] getPackages() {
		return new String[] { "org.logme.form" };
	}

}