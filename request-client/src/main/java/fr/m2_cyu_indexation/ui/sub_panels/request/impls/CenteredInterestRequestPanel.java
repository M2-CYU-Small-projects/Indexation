package fr.m2_cyu_indexation.ui.sub_panels.request.impls;

import fr.m2_cyu_indexation.engine.business.request.centered_interest.CenteredInterestRequest;
import fr.m2_cyu_indexation.engine.business.request.textured.TexturedRequest;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.request.AbstractRequestFormPanel;

/**
 * @author Aldric Vitali Silvestre
 */
public class CenteredInterestRequestPanel extends AbstractRequestFormPanel {

    public CenteredInterestRequestPanel(MainWindow context) {
        super(context);
    }

    @Override
    public void initLayout() {

    }

    @Override
    public void submitForm() {
        submitAndSwitch(new CenteredInterestRequest());
    }
}
