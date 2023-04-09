package fr.m2_cyu_indexation.ui.sub_panels.request.impls;

import fr.m2_cyu_indexation.engine.business.request.similarity.SimilarityRequest;
import fr.m2_cyu_indexation.ui.GuiPreferences;
import fr.m2_cyu_indexation.ui.MainWindow;
import fr.m2_cyu_indexation.ui.sub_panels.request.AbstractRequestFormPanel;
import fr.m2_cyu_indexation.ui.sub_panels.request.RequestPanel;
import fr.m2_cyu_indexation.ui.sub_panels.results.ResultPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Aldric Vitali Silvestre
 */
public class SimilarityRequestPanel extends AbstractRequestFormPanel {

    public static final Font FONT = GuiPreferences.BASE_FONT.deriveFont(AbstractRequestFormPanel.FORM_DIMENSIONS.width / 2.0f / 40.0f);

    private JTextField textField;

    public SimilarityRequestPanel(MainWindow context) {
        super(context);
    }

    @Override
    public void initLayout() {
        setLayout(new GridLayout(5, 2));
        int size = RequestPanel.CENTER_DIMENSIONS.width / 10;
        setBorder(BorderFactory.createEmptyBorder(size, size, size, size));

        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
        JLabel prompt = new JLabel("Image name : ", SwingConstants.RIGHT);
        prompt.setFont(FONT);
        add(prompt);

        textField = new JTextField();
        add(textField);
        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
    }

    @Override
    public void submitForm() {
        SimilarityRequest request = new SimilarityRequest(textField.getText());
        submitAndSwitch(request);
    }
}
