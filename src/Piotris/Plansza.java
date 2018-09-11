package Piotris;

import Piotris.Bloczki.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

/**
 * Klasa odpowiada za plansze gry. Jej rysowanie i losowanie bloczkow, przemieszczanie ich, przepisywanie do tablicy stanu.
 * @author Piotr Podbielski
 * Created by Piotr on 13.06.2016.
 */
public class Plansza extends JComponent {
    /**
     * Zmienna okreslajaca liczbe kolumn planszy
     */
    private int kolumn;

    /**
     * Zmienna okreslajaca liczbe wierszy planszy
     */
    private int wierszy;

    /**
     * Dlugosc boku elementu klocka
     */
    private int dlugoscBokuElementu;

    /**
     * Bloczek spadajacy na planszy
     */
    private Bloczek bloczek = null;

    /**
     * Stan planszy
     */
    private Vector<Vector<ElementBloczku>> stan = null;

    /**
     * Konstruktor ktory ustawia liczbe wierszy i kolumn oraz dlugoscBokuElementu w zaleznosci od szerokosci planszy oraz pozycje w zaleznosci od szerokosci aplikacji.
     * @param kolumn Liczba kolumn
     * @param wierszy Liczba wierszy
     */
    public Plansza(int kolumn, int wierszy) {
        setLayout(null);
        this.kolumn = kolumn;
        this.wierszy = wierszy;

        resetujPlansze();

        setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.black));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dlugoscBokuElementu = getWidth() / kolumn;
                if (dlugoscBokuElementu % 2 == 1) {
                    dlugoscBokuElementu--;
                }
                setSize(dlugoscBokuElementu * kolumn + 2, dlugoscBokuElementu * wierszy + 1);
                setLocation(getRootPane().getWidth() / 2 - getWidth() / 2 - 1, getY());
            }
        });
    }

    /**
     * Losuje bloczek i ustawia go jako aktualny
     */
    public void losujBloczek() {
        Random randomGenerator = new Random();
        switch (randomGenerator.nextInt(7)) {
            case 0:
                bloczek = new O(dlugoscBokuElementu);
                break;
            case 1:
                bloczek = new J(dlugoscBokuElementu);
                break;
            case 2:
                bloczek = new T(dlugoscBokuElementu);
                break;
            case 3:
                bloczek = new S(dlugoscBokuElementu);
                break;
            case 4:
                bloczek = new I(dlugoscBokuElementu);
                break;
            case 5:
                bloczek = new L(dlugoscBokuElementu);
                break;
            case 6:
                bloczek = new Z(dlugoscBokuElementu);
                break;
        }

        int new_j = kolumn / 2 - bloczek.getSzerokosc() / 2;
        bloczek.setJ(new_j);
        add(bloczek);
    }

    /**
     * Sprawdza czy mozna przesunac bloczek
     * @param x jak x = -1 to w lewo, jak x = 1 to w prawo
     * @return true jak mozna, false jak nie mozna
     */
    public boolean czyMoznaPrzesunacBloczek(int x) {
        Vector<Vector<Boolean>> obrocony_bloczek = bloczek.pobierzBloczek();

        for (int i = 0; i < obrocony_bloczek.size(); i++) {
            for (int j = 0; j < obrocony_bloczek.get(i).size(); j++) {
                if (obrocony_bloczek.get(i).get(j)) {
                    if (x == -1) {
                        if (bloczek.getJ() + j - 1 < 0) {
                            return false;
                        }
                        if (stan.get(bloczek.getI() + i).get(bloczek.getJ() + j - 1) != null) {
                            return false;
                        }
                    }
                    else {
                        if (bloczek.getJ() + j + 1 >= kolumn) {
                            return false;
                        }
                        if (stan.get(bloczek.getI() + i).get(bloczek.getJ() + j + 1) != null) {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }

    /**
     * Zwraca bloczek
     * @return bloczek
     */
    public Bloczek getBloczek() {
        return bloczek;
    }

    /**
     * Sprawdza czy mozna opuscic bloczek
     * @return true jak mozna, false jak nie mozna
     */
    public boolean czyMoznaOpuscicBloczek() {
        Vector<Vector<Boolean>> obrocony_bloczek = bloczek.pobierzBloczek();

        for (int i = 0; i < obrocony_bloczek.size(); i++) {
            for (int j = 0; j < obrocony_bloczek.get(i).size(); j++) {
                if (obrocony_bloczek.get(i).get(j)) {
                    if (bloczek.getI() + i + 1 >= wierszy) {
                        return false;
                    }
                    if (stan.get(bloczek.getI() + i + 1).get(bloczek.getJ() + j) != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Przenosi bloczek do planszy (tablicy stanu)
     */
    public void przeniesBloczekDoPlanszy() {
        Vector<Vector<Boolean>> obrocony_bloczek = bloczek.pobierzBloczek();

        for (int i = 0; i < obrocony_bloczek.size(); i++) {
            for (int j = 0; j < obrocony_bloczek.get(i).size(); j++) {
                if (obrocony_bloczek.get(i).get(j)) {
                    stan.get(bloczek.getI() + i).set(bloczek.getJ() + j, new ElementBloczku(bloczek.getI() + i, bloczek.getJ() + j, bloczek.getColor(), dlugoscBokuElementu));
                    add(stan.get(bloczek.getI() + i).get(bloczek.getJ() + j));
                }
            }
        }

        remove(bloczek);
        bloczek = null;
    }

    /**
     * Sprawdza czy mozna obrocic klocek
     * @return true jak mozna, false jak nie mozna
     */
    public boolean czyMoznaObrocicKlocek() {
        Vector<Vector<Boolean>> obrocony_bloczek = bloczek.pobierzObroconyBloczek();

        int temp_j = bloczek.getJ();
        if (temp_j < 0) {
            temp_j = 0;
        }
        else if (bloczek.getJ() + bloczek.getSzerokosc() >= kolumn) {
            temp_j -= kolumn - (bloczek.getJ() + bloczek.getSzerokosc());
        }

        for (int i = 0; i < obrocony_bloczek.size(); i++) {
            for (int j = 0; j < obrocony_bloczek.get(i).size(); j++) {
                if (obrocony_bloczek.get(i).get(j)) {
                    if (temp_j + j >= kolumn) {

                        bloczek.setJ(bloczek.getJ() - 1);
                        boolean czyPoPrzesunieciuWLewo = czyMoznaObrocicKlocek();
                        bloczek.setJ(bloczek.getJ() + 1);
                        return czyPoPrzesunieciuWLewo;
                    }

                    if (stan.get(bloczek.getI() + i).get(temp_j + j) != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Obraca klocek
     */
    public void obrocKlocek() {
        if (bloczek.getJ() < 0) {
            bloczek.setJ(0);
        }

        if (bloczek.getJ() + bloczek.getSzerokosc() > kolumn) {
            int ile_w_lewo = bloczek.getSzerokosc() + bloczek.getJ() - kolumn;
            bloczek.setJ(bloczek.getJ() - ile_w_lewo);
        }

        bloczek.obroc();
    }

    /**
     * Sprawdza czy sa wypelnione linie, usuwa je i zwraca ich liczbe
     * @param punkty aktualny stan punktow
     * @return nowy stan punktow
     */
    public int sprawdzPunkty(int punkty) {
        boolean flaga;
        for (int i = 0; i < stan.size(); i++) {
            flaga = true;
            for (int j = 0; j < kolumn && flaga; j++) {
                if (stan.get(i).get(j) == null) {
                    flaga = false;
                }
            }

            if (flaga == false) {
                continue;
            }

            punkty++;

            for (int k = 0; k < kolumn; k++) {
                if (stan.get(i).get(k) != null) {
                    remove(stan.get(i).get(k));
                }
                stan.get(i).set(k, null);
            }

            for (int j = i; j > 0; j--) {
                stan.set(j, stan.get(j - 1));

                for (int k = 0; k < kolumn; k++) {
                    if (stan.get(j).get(k) != null) {
                        stan.get(j).get(k).setI(stan.get(j).get(k).getI() + 1);
                    }
                }
            }

            stan.set(0, new Vector<>(kolumn));
            for (int j = 0; j < kolumn; j++) {
                stan.get(0).add(null);
            }

        }
        return punkty;
    }

    /**
     * Sprawdza czy koniec gry
     * @return true jak koniec, false jak nie koniec
     */
    public boolean czyKoniecGry() {
        if (bloczek.getI() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Resetuje plansze do stanu wyjsciowego
     */
    public void resetujPlansze() {
        if (bloczek != null) {
            remove(bloczek);
        }

        usunElementyBloczku();

        for (Component component: getComponents()
                ) {
            if (component instanceof Bloczek) {
                remove(component);
            }
        }

        bloczek = null;

        repaint();
    }

    public void usunElementyBloczku() {
        for (Component component: getComponents()
                ) {
            if (component instanceof Bloczek == false) {
                remove(component);
            }
        }

        stan = new Vector<>(wierszy);
        for (int i = 0; i < wierszy; i++) {
            stan.add(new Vector<ElementBloczku>(kolumn));
        }
        for (Vector<ElementBloczku> wiersz: stan
                ) {
            for (int i = 0; i < kolumn; i++) {
                wiersz.add(null);
            }
        }
    }

    /**
     * Zmienia bloczek
     * @param bloczek nowy bloczek
     */
    public void setBloczek(Bloczek bloczek) {
        this.bloczek = bloczek;
    }
}
