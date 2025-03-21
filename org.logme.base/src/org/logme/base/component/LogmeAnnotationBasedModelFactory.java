package org.logme.base.component;

import org.adempiere.base.AnnotationBasedModelFactory;
import org.osgi.service.component.annotations.Component;
import org.adempiere.base.IModelFactory;

/**
 *
 * @author Jose.Leite (jose.franklin550@gmail.com)
 *
 */
@Component(immediate = true, service = IModelFactory.class, property = "service.ranking:Integer=1")
public class LogmeAnnotationBasedModelFactory extends AnnotationBasedModelFactory {

	@Override
	public String[] getPackages() {
		return new String[] { "org.logme.model" };
	}

}
