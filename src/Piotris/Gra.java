package Piotris;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Klasa gra odpowiada za gre, jej start i koniec. Za wyswietlanie panelow i przyciskow z tekstami.
 * @author Piotr Podbielski
 */
public class Gra extends JPanel implements Runnable {
    /**
     * Margines dla pola tekstowego
     */
    private static final int MARGINES = 15;

    /**
     * Czy odbyla juz sie gra
     */
    private boolean czyByloGrane = false;

    /**
     * Poziom obecnej gry
     */
    private POZIOM poziom;

    /**
     * Liczba punktow w koncu gry
     */
    private JLabel liczbaPunktow;

    /**
     * Enumerat dla wyboru poziomu
     */
    private enum POZIOM {
        STALA_SZYBKOSC,
        PRZYSPIESZANIE
    }

    /**
     * Zmienna przechowujaca tekst dodatkowy zalezny od stanu gry
     */
    private JTextArea dodatkowyTekst;

    /**
     * Zmienna przechowujaca menu
     */
    private JPanel menu;

    /**
     * Koniec gry
     */
    private JPanel koniecGry;

    /**
     * Zmienna przechowujaca poprzednia wartosc dodatkowegoTekstu
     */
    private String staraWartoscDodatkowyTekst = null;

    /**
     * Zmienna przechowujaca plansze gry
     */
    private Plansza plansza = null;

    /**
     * Czas uspienia (co ile bloczek ma spadac w dol)
     */
    private int sleepTime = 1000;

    /**
     * Timer dla gry (opuszczanie bloczku)
     */
    private Timer timerGry;

    /**
     * Watek dla gry
     */
    private Thread thread;

    /**
     * Punkty w grze (zbitych linii)
     */
    private int punkty;

    /**
     * Co ile zdobytych linii czyscic plansze
     */
    private int wyczyscCo;

    /**
     * Dolna granica wyczysc co
     */
    private static final int LOSUJ_OD = 5;

    /**
     * Gorna granica wyczysc co
     */
    private static final int LOSUJ_DO = 10;

    /**
     * Warstwy w Swingu
     */
    private JLayeredPane jLayeredPane = new JLayeredPane();

    /**
     * Button zakoncz
     */
    private JButton zakoncz;

    /**
     * Button przyspieszanie
     */
    private JButton przyspieszanie;

    /**
     * Button stala szybkosc
     */
    private JButton stala_szybkosc;

    /**
     * Konstruktor klasy gra, ustawia panel, teksty, buttony oraz ich listenery.
     *
     */
    public Gra() {
        super(null);
        setBackground(Color.white);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                setSize(getRootPane().getWidth(), getRootPane().getHeight());

                JTextArea tekstWstepny = new JTextArea("To jest gra Piotris (bazuje na Tetrisie). Jest ona projektem zaliczeniowym z przedmiotu JTP. (c) 2016, Piotr Podbielski");
                tekstWstepny.setFont(new Font("Tahoma", Font.PLAIN, 14));
                tekstWstepny.setDisabledTextColor(Color.black);
                tekstWstepny.setBounds(Gra.MARGINES, Gra.MARGINES, getWidth() - 2 * Gra.MARGINES, (int)(0.1 * getHeight()) - 2 * Gra.MARGINES);
                tekstWstepny.setEnabled(false);
                add(tekstWstepny);

                dodatkowyTekst = new JTextArea("Proszę wybrać poziom z menu.");
                dodatkowyTekst.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
                dodatkowyTekst.setDisabledTextColor(Color.black);
                dodatkowyTekst.setBounds(Gra.MARGINES, (int)(0.1 * getHeight()), getWidth() - 2 * Gra.MARGINES, (int)(0.1 * getHeight()) - 2 * Gra.MARGINES);
                dodatkowyTekst.setEnabled(false);
                add(dodatkowyTekst);

                menu = new JPanel(new FlowLayout());
                menu.setOpaque(false);
                menu.setVisible(true);
                menu.setBounds(getWidth() / 2 - 100, (int)(0.2 * getHeight()) - 1 * Gra.MARGINES, 200, 100);

                stala_szybkosc = new JButton("Stała szybkość");
                stala_szybkosc.setFocusable(false);
                stala_szybkosc.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        odpalGre(POZIOM.STALA_SZYBKOSC);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        staraWartoscDodatkowyTekst = dodatkowyTekst.getText();
                        dodatkowyTekst.setText("Szybkość spadania klocka będzie stała.");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        dodatkowyTekst.setText(staraWartoscDodatkowyTekst);
                    }
                });
                menu.add(stala_szybkosc);

                przyspieszanie = new JButton("Przyspieszanie");
                przyspieszanie.setFocusable(false);
                przyspieszanie.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        odpalGre(POZIOM.PRZYSPIESZANIE);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        staraWartoscDodatkowyTekst = dodatkowyTekst.getText();
                        dodatkowyTekst.setText("Wraz z rosnącą liczbą punktów klocek będzie spadał coraz szybciej.");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        dodatkowyTekst.setText(staraWartoscDodatkowyTekst);
                    }
                });
                menu.add(przyspieszanie);

                zakoncz = new JButton("Zakończ program");
                zakoncz.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.exit(0);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        staraWartoscDodatkowyTekst = dodatkowyTekst.getText();
                        dodatkowyTekst.setText("Kliknij, aby wyłączyć aplikację.");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        dodatkowyTekst.setText(staraWartoscDodatkowyTekst);
                    }
                });
                menu.add(zakoncz);

                add(menu);

                koniecGry = new JPanel(new FlowLayout());
                JLabel informacja = new JLabel("Koniec gry!");
                koniecGry.add(informacja);

                liczbaPunktow = new JLabel("Zbitych linii: ");
                koniecGry.add(liczbaPunktow);

                koniecGry.setVisible(true);
                koniecGry.setBounds(getWidth() / 2 - 75, (int)(0.3 * getHeight()) - 1 * Gra.MARGINES, 150, 150);
                koniecGry.setBorder(new LineBorder(Color.black));
                koniecGry.setBackground(new Color(128, 128, 128, 64));

                jLayeredPane.setBounds(0, 0, getWidth(), getHeight());
                add(jLayeredPane);

                validate();
            }
        });
    }

    /**
     * Resetowanie gry, czyli ustawienie wartosci do stanu poczatkowego.
     */
    private void resetujGre() {
        punkty = 0;

        poziom = null;

        plansza.resetujPlansze();

        sleepTime = 1000;
    }

    /**
     * Wlaczenie gry razem
     * @param poziom tryb gry
     */
    public void odpalGre(POZIOM poziom) {
        if (!czyByloGrane) {
            remove(menu);
            menu = null;
            koniecGry.add(stala_szybkosc);
            koniecGry.add(przyspieszanie);
            koniecGry.add(zakoncz);

            plansza = new Plansza(10, 20);
            plansza.setBounds(getRootPane().getWidth() / 2 - 100, (int)(0.2 * getRootPane().getHeight()) - 1 * Gra.MARGINES, 200, 100);
            jLayeredPane.add(plansza, new Integer(1));
            czyByloGrane = true;
        }
        else {
            jLayeredPane.remove(koniecGry);
            resetujGre();
        }
        Random randomGenerator = new Random();
        wyczyscCo = LOSUJ_OD + randomGenerator.nextInt(LOSUJ_DO - LOSUJ_OD) + 1;

        this.poziom = poziom;

        dodatkowyTekst.setText("Liczba zbitych linii: 0, czysci co: " + wyczyscCo);
        repaint();

        Timer timer = new Timer(25, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();

        thread = new Thread(this);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                thread.start();
            }
        });
    }

    /**
     * Implementuje watek gry. Kazda kolejna gra to nowy watek i timer zwiazany z nia.
     */
    @Override
    public void run() {
        plansza.losujBloczek();

        timerGry = new Timer(sleepTime, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (plansza.getBloczek() != null) {
                    if (plansza.czyMoznaOpuscicBloczek()) {
                        plansza.getBloczek().setI(plansza.getBloczek().getI() + 1);
                    }
                    else if (plansza.czyKoniecGry()) {
                        timerGry.stop();
                        plansza.remove(plansza.getBloczek());
                        plansza.setBloczek(null);

                        timerGry = null;
                        dodatkowyTekst.setText("W celu ponownej gry wybierz poziom lub wyjdz z aplikacji.");
                        jLayeredPane.add(koniecGry, new Integer(2));

                        liczbaPunktow.setText("Zbitych linii: " + punkty);
                        thread.currentThread().interrupt();
                    }
                    else {
                        plansza.przeniesBloczekDoPlanszy();
                        plansza.losujBloczek();
                    }
                }
            }
        });
        timerGry.start();

        boolean run = true;
        int ostatnieCzyszczenie = 0;
        while (run) {
            try {
                int starePunkty = punkty;
                punkty = plansza.sprawdzPunkty(punkty);

                if (starePunkty < punkty) {
                    dodatkowyTekst.setText("Liczba zbitych linii: " + punkty + ", czysci co: " + wyczyscCo);

                    if (poziom == POZIOM.PRZYSPIESZANIE) {
                        int roznica = punkty - starePunkty;
                        sleepTime -= 25 * roznica;
                        timerGry.stop();
                        timerGry.setDelay(sleepTime);
                        timerGry.start();
                    }
                }

                if (ostatnieCzyszczenie != punkty && punkty % wyczyscCo == 0) {
                    plansza.usunElementyBloczku();
                    ostatnieCzyszczenie = punkty;
                }

                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                run = false;
            }
        }
    }

    /**
     * Pobiera status watku
     * @return status watku
     */
    public Thread.State getWatekStatus() {
        if (thread != null) {
            return thread.getState();
        }
        return null;
    }

    /**
     * Pobiera plansze
     * @return plansza
     */
    public Plansza getPlansza() {
        return plansza;
    }

    /**
     * Zwraca timer
     * @return timer Gry
     */
    public Timer getTimerGry() {
        return timerGry;
    }
}
