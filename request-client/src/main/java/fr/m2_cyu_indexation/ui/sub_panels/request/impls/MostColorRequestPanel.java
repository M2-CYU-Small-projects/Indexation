package fr.m2_cyu_indexation.ui.sub_panels.request.impls;

import fr.m2_cyu_indexation.engine.business.request.most_color.DominantColorType;
import fr.m2_cyu_indexation.engine.business.request.most_color.MostColorRequest;
import fr.m2_cyu_indexation.engine.business.request.most_color.RecessiveColorType;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.request.AbstractRequestFormPanel;
import fr.m2_cyu_indexation.ui.sub_panels.request.RequestPanel;
import fr.m2_cyu_indexation.ui.sub_panels.results.ResultPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public class MostColorRequestPanel extends AbstractRequestFormPanel {

    public static final Dimension CENTER_DIMENSIONS = RequestPanel.CENTER_DIMENSIONS;

    private ButtonGroup dominantColorButtonGroup = new ButtonGroup();
    private ButtonGroup recessiveColorButtonGroup = new ButtonGroup();

    public MostColorRequestPanel(MainWindow context) {
        super(context);
    }

    @Override
    public void initLayout() {
        setLayout(new GridLayout(1, 0));
        JPanel leftPane = createLeftPane();
        JPanel rightPane = createRightPane();

        add(leftPane);
        add(rightPane);
    }

    private JPanel createLeftPane() {
        JPanel leftPane = new JPanel();
        leftPane.setLayout(new GridLayout(1, 0));
        leftPane.add(new JLabel("Dominant color", SwingConstants.CENTER));

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.add(new JPanel());
        JPanel dominantColorChoicePanel = new JPanel();
        dominantColorChoicePanel.setLayout(new BoxLayout(dominantColorChoicePanel, BoxLayout.Y_AXIS));
        centerPanel.add(dominantColorChoicePanel);

        boolean select = false;
        for (DominantColorType type : DominantColorType.values()) {
            JRadioButton radioButton;
            if (!select) {
                radioButton = new JRadioButton(type.name(), true);
                select = true;
            } else {
                radioButton = new JRadioButton(type.name());
            }

            radioButton.setActionCommand(type.name());

            dominantColorChoicePanel.add(radioButton);
            dominantColorButtonGroup.add(radioButton);
        }

        leftPane.add(centerPanel);
        return leftPane;
    }

    private JPanel createRightPane() {
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new GridLayout(1, 0));
        rightPane.add(new JLabel("Recessive color", SwingConstants.CENTER));

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.add(new JPanel());
        JPanel ressColorChoicePanel = new JPanel();
        ressColorChoicePanel.setLayout(new BoxLayout(ressColorChoicePanel, BoxLayout.Y_AXIS));
        centerPanel.add(ressColorChoicePanel);

        boolean select = false;
        for (RecessiveColorType type : RecessiveColorType.values()) {
            JRadioButton radioButton;
            if (!select) {
                radioButton = new JRadioButton(type.name(), true);
                select = true;
            } else {
                radioButton = new JRadioButton(type.name());
            }

            radioButton.setActionCommand(type.name());

            ressColorChoicePanel.add(radioButton);
            recessiveColorButtonGroup.add(radioButton);
        }

        rightPane.add(centerPanel);
        return rightPane;
    }

    @Override
    public void submitForm() {
        System.out.println("Submit most color request");

        String dominantColor = dominantColorButtonGroup.getSelection().getActionCommand();
        String recessiveColor = recessiveColorButtonGroup.getSelection().getActionCommand();

        DominantColorType dominantColorType = DominantColorType.valueOf(dominantColor);
        RecessiveColorType recessiveColorType = RecessiveColorType.valueOf(recessiveColor);

        submitAndSwitch(new MostColorRequest(dominantColorType, recessiveColorType));
    }
}
