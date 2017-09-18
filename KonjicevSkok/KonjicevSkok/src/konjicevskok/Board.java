package konjicevskok;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Vector;

public class Board extends JFrame {
    private JPanel slovaPanel;
    private JButton[][] polja;
    private JLabel rezultatL;
    private JButton vratiBt;

    private int tekuceI;
    private int tekuceJ;

    private Stack<Integer> potezi = new Stack<Integer>();
    private Container content;

    private int brPoteza;
    private int ukupanBrPoteza;


    public Board(String naslov) {
        super(naslov);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Greska");
        }
        content = getContentPane();
        setJMenuBar(createMenu(this));
        content.add(rezultatPanel(), BorderLayout.SOUTH);
        content.add(severPanel(), BorderLayout.NORTH);
        content.add(slovaPanel, BorderLayout.CENTER);
    }

    private JPanel rezultatPanel() {
        JPanel rezultatPanel = new JPanel();
        rezultatL = new JLabel();
        rezultatL.setPreferredSize(new Dimension(400, 30));
        rezultatPanel.add(rezultatL);

        vratiBt = new JButton("VRATI");
        vratiBt.setEnabled(false);
        vratiBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!potezi.empty()) {
                    polja[tekuceI][tekuceJ].setBackground(Color.YELLOW);
                    tekuceJ = potezi.pop();
                    tekuceI = potezi.pop();
                    brPoteza--;
                    polja[tekuceI][tekuceJ].setBackground(Color.RED);
                    String rezultat = rezultatL.getText();
                    int duzina = rezultat.length();
                    rezultatL.setText(rezultat.substring(0, duzina - 3));
                }
            }
        });
        rezultatPanel.add(vratiBt);
        return rezultatPanel;
    }

    public void initGame() {

        int brVrsta = 0;
        String slova[] = null;
        Vector<String> slovaVector = new Vector<String>();
        for (String l : Level.level) {
            String linija = l;
            brVrsta++;
            slova = linija.split(";");
            for (String s : slova)
                slovaVector.add(s);
        }
        int brKolona = slova.length;

        slovaPanel.setLayout(new GridLayout(brVrsta, brKolona));
        polja = new JButton[brVrsta][brKolona];
        for (int i = 0; i < polja.length; i++)
            for (int j = 0; j < polja[i].length; j++) {
                String slovo = slovaVector.remove(0);
                polja[i][j] = new JButton(slovo);
                if (slovo.trim().length() == 0)
                    polja[i][j].setVisible(false);
                else {
                    ukupanBrPoteza++;
                    polja[i][j].setBackground(Color.YELLOW);
                    final int I = i;
                    final int J = j;
                    polja[i][j].addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (polja[I][J].getBackground().equals(Color.YELLOW) &&
                                    (Math.abs(I - tekuceI) == 2 && Math.abs(J - tekuceJ) == 1 ||
                                            Math.abs(I - tekuceI) == 1 && Math.abs(J - tekuceJ) == 2)) {
                                polja[tekuceI][tekuceJ].setBackground(Color.GREEN);
                                potezi.push(tekuceI);
                                potezi.push(tekuceJ);
                                brPoteza++;

                                polja[I][J].setBackground(Color.RED);
                                tekuceI = I;
                                tekuceJ = J;
                                rezultatL.setText(rezultatL.getText() + polja[I][J].getText());

                                boolean kraj = brPoteza == ukupanBrPoteza;
                                if (kraj) {
                                    rezultatL.setForeground(Color.GREEN);
                                    vratiBt.setEnabled(false);
                                }
                            } else
                                JOptionPane.showMessageDialog(polja[I][J], "Ne mozete skociti tu");
                        }
                    });
                }
                slovaPanel.add(polja[i][j]);
            }

        // prvo slovo u prvom redu treba da bude crveno
        for (int j = 0; j < polja[0].length; j++)
            if (polja[0][j].isVisible()) {
                polja[0][j].setBackground(Color.red);
                rezultatL.setText(polja[0][j].getText());
                tekuceI = 0;
                tekuceJ = j;
                brPoteza = 1;
                break;
            }

    }


    private JPanel severPanel() {
        JPanel severPanel = new JPanel();
        slovaPanel = new JPanel();
        initGame();
        slovaPanel.validate();
        vratiBt.setEnabled(true);

        return severPanel;
    }

    private JMenuBar createMenu(JFrame frame) {

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Board");

        JMenuItem newGame = new JMenuItem("New Board");
        JMenuItem help = new JMenuItem("Help");

        gameMenu.add(newGame);
        gameMenu.add(help);

        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                content.removeAll();
                content.add(severPanel(), BorderLayout.NORTH);
                content.add(slovaPanel, BorderLayout.CENTER);
                content.add(rezultatPanel(), BorderLayout.SOUTH);
                revalidate();
                repaint();
            }
        });

        help.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Cilj igre je da se klikom misa na slova skacuci na Г,pogadja data izreka. "
                        + "\n Ne smije se dva puta stati na isto polje,i sva polja moraju biti popunjena");
            }
        });

        menuBar.add(gameMenu);

        return menuBar;
    }

}