package mcdModelChecker.view.component.sharedComponents;

import mcdModelChecker.view.component.Component;

import javax.swing.*;
import java.awt.*;

public class MainContainers extends Component {

    public JPanel mainPanel = new JPanel();
    public JPanel sidebarInner;

    public MainContainers() {
        super();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // frame.add(mainPanel, BorderLayout.NORTH);

        JPanel sidebarWrapper = new JPanel();
        sidebarWrapper.setLayout(new GridBagLayout());
        GridBagConstraints sidebarWrapperStyle = new GridBagConstraints();
        sidebarWrapperStyle.anchor = GridBagConstraints.NORTH;
        sidebarWrapperStyle.weighty = 1;
        mainPanel.add(sidebarWrapper, BorderLayout.WEST);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebarWrapper.add(sidebar, sidebarWrapperStyle);

        sidebarInner = new JPanel();
        sidebarInner.setLayout(new GridBagLayout());
        sidebar.add(sidebarInner, BorderLayout.NORTH);

        JPanel sidebarPaddingLeft = new JPanel();
        GridBagConstraints sidebarPaddingLeftStyle = new GridBagConstraints();
        sidebarPaddingLeftStyle.gridx = 0;
        sidebarPaddingLeftStyle.gridy = 0;
        sidebarInner.add(sidebarPaddingLeft, sidebarPaddingLeftStyle);

        JPanel sidebarPaddingRight = new JPanel();
        GridBagConstraints sidebarPaddingRightStyle = new GridBagConstraints();
        sidebarPaddingRightStyle.gridx = 2;
        sidebarPaddingRightStyle.gridy = 0;
        sidebarInner.add(sidebarPaddingRight, sidebarPaddingRightStyle);

    }


}
