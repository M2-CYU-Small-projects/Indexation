package fr.m2_cyu_indexation.ui.sub_panels.menu;

import fr.m2_cyu_indexation.ui.GuiPreferences;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.components.TitlePanel;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;

import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public class MenuPanel extends AbstractSubPanel {

    private static final Dimension TITLE_DIMENSIONS = new Dimension(GuiPreferences.WIDTH, GuiPreferences.HEIGHT / 5);
    private static final Dimension REQUEST_BUTTONS_DIMENSIONS = new Dimension(GuiPreferences.WIDTH, 4 * GuiPreferences.HEIGHT / 5);

    private TitlePanel titlePanel;
    private RequestButtonsPanel requestButtonsPanel;

    public MenuPanel(MainWindow context) {
        super(context);
        titlePanel = new TitlePanel(context, "Requests client");
        titlePanel.setPreferredSize(TITLE_DIMENSIONS);
        requestButtonsPanel = new RequestButtonsPanel(context);
        requestButtonsPanel.setPreferredSize(REQUEST_BUTTONS_DIMENSIONS);
        init();
    }
    private void init() {
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(requestButtonsPanel, BorderLayout.CENTER);
    }
}
