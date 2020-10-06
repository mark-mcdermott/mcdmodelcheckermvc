package mcdModelChecker.view.component.sharedComponents;

import mcdModelChecker.view.component.Component;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel extends Component {

    public JPanel panel;
    public GridBagConstraints buttonsPanelStyle;

    public ButtonsPanel() {
        super();

        panel = new JPanel();
        buttonsPanelStyle = new GridBagConstraints();
        buttonsPanelStyle.gridx = 1;
        buttonsPanelStyle.gridy = 0;
        panel.setBorder(BorderFactory.createEmptyBorder(7, 10, 6, 10));


        JButton analyzerButton = new JButton();
        GridBagConstraints analyzerButtonStyle = new GridBagConstraints();
        analyzerButton.setText("Analyzer");
        panel.add(analyzerButton);

        JButton testerButton = new JButton();
        GridBagConstraints testerButtonStyle = new GridBagConstraints();
        testerButton.setText("Tests");
        panel.add(testerButton);

    }


}
