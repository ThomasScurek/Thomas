package crypto.platform.ui.api.controller;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public abstract class ComponentController implements IComponentController {

	private final Composite parent;
	
	public ComponentController(Composite parent){
		this.parent = parent;
	}
	
	public void refresh(){
		parent.layout();
		parent.redraw();
	}
	
	public void show(){
		parent.setVisible(true);
		Object layoutData = parent.getLayoutData();
		if(layoutData instanceof GridData)
			((GridData) layoutData).exclude = false;
		
		parent.getParent().layout();
	}
	
	public void hide(){
		parent.setVisible(false);
		Object layoutData = parent.getLayoutData();
		if(layoutData instanceof GridData)
			((GridData) layoutData).exclude = true;
		parent.getParent().layout();
	}
	
}
