package Piotris;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa glowna aplikacji wyswietlajaca okno na ekranie i inicjalizujaca gre
 * @author Piotr Podbielski
 */
public class Aplikacja extends JFrame {
    /**
     * Stala okreslajaca szerokosc okna
     */
    private static final int WIDTH = 800;
    /**
     * Stala okreslajaca wysokosc okna
     */
    private static final int HEIGHT = 600;

    /**
     * Konstruktor klasy aplikacja wywolujacy konstruktor klasy nadrzednej JFrame
     * @param title Tytul okna
     * @throws HeadlessException
     */
    public Aplikacja(String title) throws HeadlessException {
        super(title);
    }

    /**
     * Metoda wywolywana po odpaleniu aplikacji. Tworzy okno aplikacji, inicjalizuje gre i dodaje ja do okna. Dodaje tez nasluchiwanie klawiszy.
     * @param args Argumenty przekazane aplikacji podczas jej odpalania
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Aplikacja aplikacja = new Aplikacja("Piotris");
                aplikacja.setSize(Aplikacja.WIDTH, Aplikacja.HEIGHT);
                aplikacja.setLocation(200, 200);
                aplikacja.setResizable(false);
                aplikacja.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                aplikacja.setVisible(true);
                Gra gra = new Gra();
                aplikacja.addKeyListener(new MyKeyListener(gra));
                aplikacja.add(gra);

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
