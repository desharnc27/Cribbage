package graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import mainpackage.CribbageException;
import mainpackage.GeneralMeths;
import mainpackage.SuitedMeths;
import mainpackage.UserDiscardDecisioning;
import mainpackage.UserReport;

/**
 *
 * @author desharnc27
 */
public class MyFrame extends JFrame {

    boolean darkTheme = true;

    JMenuBar barMenu;
    JMenu fileMenu;
    JMenuItem inputItem;
    //JMenu algoMenu;
    //JRadioButtonMenuItem naifData;
    //JRadioButtonMenuItem dankData;
    ButtonGroup bugr;
    JPanel northTexts = new JPanel();

    JTextField handEntry = new JTextField();

    JTextArea content = new JTextArea();
    private JMenu themeMenu;
    private JRadioButtonMenuItem darkChoice;
    private JRadioButtonMenuItem lightChoice;
    private ButtonGroup bgrTh;

    private String userCommand = "";
    private UserReport userReport = null;

    private boolean dataUse = true;
    private boolean considerCrib = true;
    private boolean considerPeg = false;

    private JButton incFontButton;
    private JButton decFontButton;

    private Color textColor;
    private Color bckgColor;
    private Color butBarColor;
    private JPanel seminorth;

    String handEntryDefaultText = "Enter your hand here";

    //Sizes
    Font contentFont = new Font("monospaced", Font.BOLD, 20);
    Font buttonFont = new Font("monospaced", Font.BOLD, 5);

    private int heiNorth0 = 60;
    private int heiNorth1 = 60;
    private int heiButt = 40;

    //private int heiButText=40;
    private JMenuItem aboutItem;

    public MyFrame() {
        setLayout(new BorderLayout());
        setMenuBar();
        setNorth();
        setContent();
        applyTheme();

    }

    private void applyTheme() {
        if (darkTheme) {
            textColor = Color.YELLOW;
            bckgColor = Color.BLACK;
            butBarColor = Color.MAGENTA;
        } else {
            bckgColor = Color.LIGHT_GRAY;
            textColor = Color.BLACK;
            butBarColor = Color.DARK_GRAY;
        }
        handEntry.setBackground(bckgColor);
        content.setBackground(bckgColor);
        handEntry.setForeground(textColor);
        content.setForeground(textColor);
        handEntry.setCaretColor(textColor);
        seminorth.setBackground(butBarColor);

    }

    public void setTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;

    }

    private void setOptionsMenu() {
        JMenu optMenu = new JMenu("Options");

        JCheckBoxMenuItem useStatsItem = new JCheckBoxMenuItem("use advanced stats");
        JCheckBoxMenuItem consCribItem = new JCheckBoxMenuItem("Consider crib");
        JCheckBoxMenuItem consPegItem = new JCheckBoxMenuItem("Consider peg");

        consCribItem.setSelected(true);
        useStatsItem.setSelected(true);
        consPegItem.setSelected(false);

        considerCrib = consCribItem.isSelected();
        dataUse = useStatsItem.isSelected();
        considerPeg = consPegItem.isSelected();

        if (FrameMain.pegAvailable() == false) {
            consPegItem.setEnabled(false);
            //TODO: note in actual state display?
        }

        ActionListener optionAL = (ActionEvent ae) -> {
            Object src = ae.getSource();
            if (src == useStatsItem) {
                dataUse = useStatsItem.isSelected();
                if (userReport != null && (considerCrib || considerPeg)) {
                    executeAlgo();
                }
            } else if (src == consCribItem) {
                considerCrib = consCribItem.isSelected();
                System.out.println("considerCrib" + considerCrib);

                refreshContent();

            } else if (src == consPegItem) {
                considerPeg = consPegItem.isSelected();
                System.out.println("considerPeg" + considerPeg);
                refreshContent();
            }
            /*if (!considerCrib&&!considerPeg){
               //With neither crib stats nor peg stats are used, advanced data is useless
               useStatsItem.setSelected(false);
               dataUse=false;
               
           }*/

        };
        useStatsItem.addActionListener(optionAL);
        consCribItem.addActionListener(optionAL);
        consPegItem.addActionListener(optionAL);
        optMenu.add(useStatsItem);
        optMenu.add(consCribItem);
        optMenu.add(consPegItem);
        barMenu.add(optMenu);
    }

    private void setMenuBar() {
        barMenu = new JMenuBar();

        fileMenu = new JMenu("File");
        inputItem = new JMenuItem("input-guide");

        fileMenu.add(inputItem);
        inputItem.addActionListener((ActionEvent e) -> {

            displayInputGuide();

        });
        barMenu.add(fileMenu);

        aboutItem = new JMenuItem("About");

        fileMenu.add(aboutItem);
        aboutItem.addActionListener((ActionEvent e) -> {
            displayAbout();

        });
        barMenu.add(fileMenu);

        /*algoMenu = new JMenu("Data");
        naifData = new JRadioButtonMenuItem("naif");
        dankData = new JRadioButtonMenuItem("dank");
        bugr = new ButtonGroup();
        bugr.add(naifData);
        bugr.add(dankData);
        algoMenu.add(naifData);
        algoMenu.add(dankData);
        
        ActionListener dataAL=(ActionEvent ae) -> {
            if (ae.getSource()==naifData && dataUse){
                dataUse=false;
            }else if (ae.getSource()==dankData && !dataUse){
                dataUse=true;
            }else
                return;
            //if (!areaUse=='v')
            //    return;
            if (!userCommand.equals(""))
                executeAlgo();
            repaint();
        };
        naifData.addActionListener(dataAL);
        dankData.addActionListener(dataAL);
        barMenu.add(algoMenu);*/
        themeMenu = new JMenu("Theme");
        darkChoice = new JRadioButtonMenuItem("Dark");
        lightChoice = new JRadioButtonMenuItem("Light");

        bgrTh = new ButtonGroup();
        bgrTh.add(darkChoice);
        bgrTh.add(lightChoice);
        themeMenu.add(darkChoice);
        themeMenu.add(lightChoice);

        ActionListener themeAL = (ActionEvent ae) -> {
            if (ae.getSource() == darkChoice && !darkTheme) {
                darkTheme = true;
            } else if (ae.getSource() == lightChoice && darkTheme) {
                darkTheme = false;
            } else {
                return;
            }
            applyTheme();
            repaint();
        };

        darkChoice.addActionListener(themeAL);
        lightChoice.addActionListener(themeAL);

        if (bgrTh.getSelection() == darkChoice);

        barMenu.add(themeMenu);

        setOptionsMenu();
        setJMenuBar(barMenu);
    }

    private void setNorth() {
        northTexts = new JPanel();
        northTexts.setLayout(new BorderLayout());
        setInput();
        setButtonBar();
        northTexts.setPreferredSize(new Dimension(2000, heiNorth0 + heiNorth1));
        add(northTexts, BorderLayout.NORTH);
    }

    private void setInput() {

        handEntry = new JTextField();

        northTexts.add(handEntry, BorderLayout.NORTH);

        handEntry.setMinimumSize(new Dimension(2000, heiNorth0));
        handEntry.setText(handEntryDefaultText);
        handEntry.setEditable(true);

        handEntry.addActionListener((ActionEvent e) -> {
            userCommand = handEntry.getText();

            executeAlgo();
        });

    }

    private void setContent() {

        content = new JTextArea("Initialized and ready \n ... and TWADOOOOO\n upensmie");
        content.setFont(contentFont);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setEditable(false);
        add(content, BorderLayout.CENTER);
    }

    private void refreshContent() {
        if (userReport == null) {
            return;
        }
        String text = String.join("\n", userReport.computeStringArr(considerCrib, considerPeg));
        content.setText(text);
    }

    private void executeAlgo() {
        content.setText("It should display the" + (dataUse ? "dankDat" : "naivDat") + " algo");
        String text;
        try {
            userReport = SuitedMeths.printDiscardReport(new String[]{userCommand}, dataUse);
            refreshContent();
        } catch (CribbageException ex) {
            text = ex.getErrorMessage();
            content.setText(text);
        }

    }

    private void setButtonBar() {
        incFontButton = new JButton("+");
        decFontButton = new JButton("-");
        //incFontButton.setFont(buttonFont);
        //decFontButton.setFont(buttonFont);

        ActionListener fontSizeAL = (ActionEvent ae) -> {
            System.out.println("Oh, yo 1");
            int newSize = contentFont.getSize();
            if (ae.getSource() == incFontButton) {
                newSize++;
            } else if (ae.getSource() == decFontButton) {
                newSize--;
                System.out.println("Oh, yo 2");
            }
            contentFont = new Font("monospaced", Font.BOLD, newSize);
            content.setFont(contentFont);

        };
        incFontButton.setPreferredSize(new Dimension(50, heiButt));
        decFontButton.setPreferredSize(new Dimension(50, heiButt));
        incFontButton.addActionListener(fontSizeAL);
        decFontButton.addActionListener(fontSizeAL);
        seminorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        seminorth.setPreferredSize(new Dimension(200, heiNorth1));
        northTexts.add(seminorth, BorderLayout.CENTER);
        seminorth.add(incFontButton);
        seminorth.add(decFontButton);

    }
    private void display(String filename){
        String text= GeneralMeths.fileToString(filename);
        //String text =StandardCharsets.readFile("test.txt", StandardCharsets.UTF_8);
        content.setText(text);
        handEntry.setText(handEntryDefaultText);
        userReport = null;
    }
    private void displayAbout() {
        display("about_pl.txt");
    }
    private void displayInputGuide() {
        display("inputUserGuide.txt");
    }
}
