package org.fatec.base.component;

import org.adempiere.base.AnnotationBasedColumnCalloutFactory;
import org.adempiere.base.IColumnCalloutFactory;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author Jose.Leite (jose.franklin550@gmail.com)
 *
 */
@Component(immediate = true, service = IColumnCalloutFactory.class)
public class FatecAnnotationBasedColumnCalloutFactory extends AnnotationBasedColumnCalloutFactory {

	@Override
	protected String[] getPackages() {
		return new String[] { "org.fatec.callout" };
	}

}
