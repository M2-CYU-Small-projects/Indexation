package fr.m2_cyu_indexation.ui.sub_panels.request.impls;

import fr.m2_cyu_indexation.engine.business.request.greyscale.GreyscaleRequest;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.request.AbstractRequestFormPanel;

/**
 * @author Aldric Vitali Silvestre
 */
public class GreyscaleRequestPanel extends AbstractRequestFormPanel {

    public GreyscaleRequestPanel(MainWindow context) {
        super(context);
    }

    @Override
    public void initLayout() {

    }

    @Override
    public void submitForm() {
        submitAndSwitch(new GreyscaleRequest());
    }
}
