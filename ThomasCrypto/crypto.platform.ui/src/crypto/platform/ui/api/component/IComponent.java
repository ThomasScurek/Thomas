package crypto.platform.ui.api.component;

import org.eclipse.swt.widgets.Composite;
import crypto.platform.ui.api.controller.ComponentController;

public interface IComponent {

	public ComponentController create(Composite parent);
	
}
