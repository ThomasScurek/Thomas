package crypto.platform.ui.simple.component;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import crypto.platform.ui.api.component.ViewerComponent;
import crypto.platform.ui.simple.controller.SimpleListViewerComponentController;

public class SimpleListViewerComponent extends ViewerComponent {

	@Override
	public SimpleListViewerComponentController create(Composite parent) {
		
		if(contentProvider == null || labelProvider == null)
			throw new NullPointerException("Content provider and/or label provider not set.");
		
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,1));
		
		ListViewer viewer = new ListViewer(container, SWT.BORDER);
		viewer.getList().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,1));

		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		
		return new SimpleListViewerComponentController(container, viewer);
	}
}
