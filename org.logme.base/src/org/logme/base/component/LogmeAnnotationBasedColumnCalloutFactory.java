package org.logme.base.component;

import org.adempiere.base.AnnotationBasedColumnCalloutFactory;
import org.adempiere.base.IColumnCalloutFactory;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author Jose.Leite (jose.franklin550@gmail.com)
 *
 */
@Component(immediate = true, service = IColumnCalloutFactory.class)
public class LogmeAnnotationBasedColumnCalloutFactory extends AnnotationBasedColumnCalloutFactory {

	@Override
	protected String[] getPackages() {
		return new String[] { "org.logme.callout" };
	}

}
