package org.fatec.base.component;

import org.adempiere.base.AnnotationBasedEventManager;
import org.osgi.service.component.annotations.Component;

/**
*
* @author Jose.Leite (jose.franklin550@gmail.com)
*
*/
@Component(immediate = true, service = {})
public class FatecAnnotationBasedEventManager extends AnnotationBasedEventManager {

	@Override
	public String[] getPackages() {
		return new String[] { "org.fatec.event" };
	}

}