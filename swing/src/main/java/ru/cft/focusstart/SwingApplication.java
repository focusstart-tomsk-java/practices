package ru.cft.focusstart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingApplication {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenu jMenu = new JMenu("File");
        jMenu.add(exitItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        frame.setJMenuBar(jMenuBar);

        JPanel jPanel = new JPanel();
        frame.add(jPanel);

        jPanel.setLayout(new GridLayout(2, 2));

        Icon closedIcon = new ImageIcon(SwingApplication.class.getResource("/icons/closed.png"));
        Icon openIcon = new ImageIcon(SwingApplication.class.getResource("/icons/zero.png"));
        Icon minedIcon = new ImageIcon(SwingApplication.class.getResource("/icons/mined.png"));
        Dimension buttonPreferredSize = new Dimension(50, 50);

        JButton button1 = new JButton();
        button1.setIcon(closedIcon);
        button1.addActionListener(e -> button1.setIcon(openIcon));
        button1.setPreferredSize(buttonPreferredSize);
        jPanel.add(button1);

        JButton button2 = new JButton();
        button2.setIcon(closedIcon);
        button2.addActionListener(e -> button2.setIcon(openIcon));
        button2.setPreferredSize(buttonPreferredSize);
        jPanel.add(button2);

        JButton button3 = new JButton();
        button3.setIcon(closedIcon);
        button3.addActionListener(e -> button3.setIcon(minedIcon));
        button3.setPreferredSize(buttonPreferredSize);
        jPanel.add(button3);

        JButton button4 = new JButton();
        button4.setIcon(closedIcon);
        button4.addActionListener(e -> button4.setIcon(openIcon));
        button4.setPreferredSize(buttonPreferredSize);
        jPanel.add(button4);

        frame.setLayout(new BorderLayout());
        frame.add(jPanel, BorderLayout.CENTER);

        JLabel jLabel = new JLabel(new ImageIcon(SwingApplication.class.getResource("/icons/win.png")));
        frame.add(jLabel, BorderLayout.WEST);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
