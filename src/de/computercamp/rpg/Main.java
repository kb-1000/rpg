package de.computercamp.rpg;

import de.computercamp.rpg.entities.Player;
import de.computercamp.rpg.entities.items.Item;
import de.computercamp.rpg.resources.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

public class Main {
    private static JButton closeButton;
    private static JFrame jf;
    private static JTextArea ta;
    private static JComboBox<Locale> selectLanguageComboBox;
    private static MapBuilder mapBuilder = new MapBuilder();

    public static void main(String[] args) {
        jf = new JFrame("");
        ta = new JTextArea(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height - 5);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Main.class.getClassLoader().getResourceAsStream("de/computercamp/rpg/resources/fonts/NotoSansMono-Regular.ttf"));
            font = font.deriveFont(40f);
            ta.setFont(font);
        } catch (FontFormatException | IOException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            JOptionPane.showMessageDialog(jf, stringWriter.getBuffer(), "Error while reading font", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        ta.addKeyListener(new KeyHandler());
        ta.setBackground(Color.black);
        ta.setForeground(Color.white);
        ta.setEditable(false);
        ta.setAutoscrolls(false);
        ta.setFocusable(true);
        closeButton = new JButton(Messages.closeProgram);
        closeButton.setBackground(Color.red);
        closeButton.setForeground(Color.white);
        closeButton.addActionListener(new CloseHandler());
        closeButton.setText(Messages.closeProgram);
        JPanel panel = new JPanel();
        Locale[] languageList = {Locale.GERMAN, Locale.ENGLISH};
        JComboBox<Locale> selectLanguageComboBox = new JComboBox<Locale>(languageList);
        if (Locale.getDefault().equals(Locale.GERMAN))
            selectLanguageComboBox.setSelectedIndex(0);
        else
            selectLanguageComboBox.setSelectedIndex(1);
        selectLanguageComboBox.addActionListener(new SelectLanguageHandler());
        selectLanguageComboBox.setBackground(Color.green);
        selectLanguageComboBox.setForeground(Color.black);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        JPanel closepanel = new JPanel();
        closepanel.setLayout(new FlowLayout());
        closepanel.add(closeButton);
        closepanel.add(selectLanguageComboBox);
        panel.add(closepanel);
        jf.getContentPane().add(BorderLayout.CENTER, panel);
        jf.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        jf.setUndecorated(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        renderGame();
    }

    public static void clearConsole() {
        ta.setText("");
    }

    public static void consoleWrite(String text) {
        ta.setText(ta.getText() + text + "\n");
    }

    public static void consoleClearAndWrite(String text) {
        clearConsole();
        consoleWrite(text);
    }

    static class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    mapBuilder.getPlayer().up();
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    mapBuilder.getPlayer().left();
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    mapBuilder.getPlayer().down();
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    mapBuilder.getPlayer().right();
                    break;
            }
            renderGame();
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }

    private static void renderGame() {
        consoleClearAndWrite(mapBuilder.getMap().render());
        mapBuilder.getPlayer().printInventory();
    }
    

    static class CloseHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            jf.dispose();
        }
    }

    static class SelectLanguageHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(selectLanguageComboBox)) {
                Messages.changeLanguage((Locale) selectLanguageComboBox.getSelectedItem());
            }
        }

    }
}
