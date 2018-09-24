package crypto.platform.ui.simple.component;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import crypto.platform.ui.api.component.ViewerComponent;
import crypto.platform.ui.simple.controller.SimpleTreeViewerComponentController;

public class SimpleTreeViewerComponent extends ViewerComponent {

	@Override
	public SimpleTreeViewerComponentController create(Composite parent) {

		if(contentProvider == null || labelProvider == null)
			throw new NullPointerException("Content provider and/or label provider not set.");
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,1));
		
		TreeViewer viewer = new TreeViewer(container, SWT.BORDER);
		viewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,1));
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		
		return new SimpleTreeViewerComponentController(container, viewer);
	}

}
