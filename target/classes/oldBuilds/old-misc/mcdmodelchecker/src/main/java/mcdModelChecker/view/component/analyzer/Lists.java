package mcdModelChecker.view.component.analyzer;

import javax.swing.*;
import java.awt.*;

public class Lists {

    public Lists() {
        JLabel fileLabel = new JLabel();
        GridBagConstraints fileLabelStyle = new GridBagConstraints();
        fileLabelStyle.gridx = 1;
        fileLabelStyle.gridy = 2;
        fileLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        fileLabel.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        // fileLabel.setFont(labelFont);
        // sidebarInner.add(fileLabel, fileLabelStyle);

        // fileList = new JList();
        GridBagConstraints fileListStyle = new GridBagConstraints();
        // fileList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        // fileList.setSelectionBackground(options.getSelectedListItemColor());
        // fileList.setFont(listFont);
        fileListStyle.gridx = 1;
        fileListStyle.gridy = 3;
        fileListStyle.fill = GridBagConstraints.HORIZONTAL;

        // JScrollPane fileListScrollPane = new JScrollPane(fileList);
        // fileListScrollPane = new JScrollPane(fileList);
        // fileListScrollPane.setPreferredSize(new Dimension(150, 100));
        // sidebarInner.add(fileListScrollPane, fileListStyle);
    }

}
