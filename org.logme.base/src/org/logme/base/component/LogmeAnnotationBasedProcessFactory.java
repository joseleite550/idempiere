package org.logme.base.component;
import org.adempiere.base.AnnotationBasedProcessFactory;
import org.adempiere.base.IProcessFactory;
import org.osgi.service.component.annotations.Component;

/**
*
* @author Jose.Leite (jose.franklin550@gmail.com)
*
*/
@Component(immediate = true, service = IProcessFactory.class, property = { "service.ranking:Integer=1" })
public class LogmeAnnotationBasedProcessFactory extends AnnotationBasedProcessFactory {

	@Override
	protected String[] getPackages() {
		return new String[] { "org.logme.process" };
	}

}