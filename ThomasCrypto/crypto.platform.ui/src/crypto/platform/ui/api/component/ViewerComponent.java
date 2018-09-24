package crypto.platform.ui.api.component;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.swt.widgets.Composite;
import crypto.platform.ui.api.controller.ViewerComponentController;

public abstract class ViewerComponent implements IComponent {

	protected IContentProvider contentProvider;
	protected IBaseLabelProvider labelProvider;
	
	public ViewerComponent setContentProvider(IContentProvider contentProvider){
		this.contentProvider = contentProvider;
		return this;
	}
	
	public ViewerComponent setLabelProvider(IBaseLabelProvider labelProvider){
		this.labelProvider = labelProvider;
		return this;
	}
	
	@Override
	public abstract ViewerComponentController create(Composite parent);

}
