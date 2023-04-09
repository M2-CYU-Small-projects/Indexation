package fr.m2_cyu_indexation.ui.sub_panels.results;

import fr.m2_cyu_indexation.ui.GuiPreferences;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.AbstractSubPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public class StatsPanel extends AbstractSubPanel {

    public static final Dimension TOP_DIMENSIONS = ResultPanel.TOP_DIMENSIONS;
    public static final Font FONT = GuiPreferences.BASE_FONT.deriveFont((float) TOP_DIMENSIONS.width / 30.0f);

    private JLabel nbResultsLabel;

    public StatsPanel(MainWindow context) {
        super(context);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        int nbResponses = context.getNbResponses();
        nbResultsLabel = new JLabel("Result count : " + nbResponses, SwingConstants.CENTER);
        nbResultsLabel.setFont(FONT);
        nbResultsLabel.setPreferredSize(TOP_DIMENSIONS);
        add(nbResultsLabel, BorderLayout.CENTER);
    }


}
