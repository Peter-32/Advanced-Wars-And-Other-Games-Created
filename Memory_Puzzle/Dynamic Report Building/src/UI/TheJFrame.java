package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Peter on 8/11/2016.
 */
public class TheJFrame extends JFrame {

    TheJFrame() {
        this.setSize(1200,800);

        Toolkit tk = Toolkit.getDefaultToolkit();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        this.setTitle("Dynamic SQL.Dynamic_Addition.Report Building");


        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        JPanel panel1 = new JPanel();

        panel1.setLayout(new GridBagLayout());

        JLabel label1 = new JLabel("Account ID");

        addComp(panel1, label1, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);

        JPanel panel2 = new JPanel();

        panel2.setLayout(new GridBagLayout());

        JLabel label2 = new JLabel("Account ID2");

        addComp(panel2, label2, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH);

        ImageIcon icon = new ImageIcon("file:./resources/Login Rounded-48.png");

        tabbedPane.addTab("Tab 1", icon, panel1, "Does nothing");

        tabbedPane.addTab("Tab 2", icon, panel2, "Does twice as much nothing");

        //tabbedPane.addTab("Tab 3", icon, panel3, "Still does nothing");




        this.add(tabbedPane);

        // show the JFrame

        this.setVisible(true);
    }

    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch) {

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = xPos;
        gridBagConstraints.gridy = yPos;
        gridBagConstraints.gridwidth = compWidth;
        gridBagConstraints.gridheight = compHeight;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.anchor = place;
        gridBagConstraints.fill = stretch;

        thePanel.add(comp, gridBagConstraints);

    } // END OF addComp METHOD
}
