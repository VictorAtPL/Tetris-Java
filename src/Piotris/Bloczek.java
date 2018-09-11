package Piotris;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Klasa odpowiada za obsluge bloczka opadajacego na planszy
 * @author Piotr Podbielski
 * Created by Piotr on 13.06.2016.
 */
public class Bloczek extends JComponent {
    /**
     * Szerokosc bloczka
     */
    private int szerokosc;

    /**
     * Wysokosc bloczka
     */
    private int wysokosc;

    /**
     * Rzedna bloczka
     */
    private int i = 0;

    /**
     * Odcieta bloczka
     */
    private int j = 0;

    /**
     * Aktualny obrot bloczka
     */
    private int obrot = 0;

    /**
     * Dlugosc boku
     */
    int dlugoscBokuElementu = 0;

    /**
     * Wektory z zapisanym wygladem dla konkretnego obrotu
     */
    protected Vector<Vector<Boolean>> obrot_1, obrot_2, obrot_3, obrot_4;

    /**
     * Kolor
     */
    private Color color;

    /**
     * Konstruktor ustawiajcy rozmiar
     * @param szerokosc szerokosc bloczka
     * @param wysokosc wysokosc bloczka
     * @param color kolor
     * @param dlugoscBokuElementu dlugosc boku
     */
    public Bloczek(int szerokosc, int wysokosc, Color color, int dlugoscBokuElementu) {
        setLayout(null);
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.color = color;
        this.dlugoscBokuElementu = dlugoscBokuElementu;

        setSize(dlugoscBokuElementu * szerokosc, dlugoscBokuElementu * wysokosc);
    }

    /**
     * Zwraca rzedna
     * @return rzedna
     */
    public int getI() {
        return i;
    }

    /**
     * Zmienia rzedna
     * @param i nowa rzedna
     */
    public void setI(int i) {
        this.i = i;
    }

    /**
     * Zwraca odcieta
     * @return odcieta
     */
    public int getJ() {
        return j;
    }

    /**
     * Zmienia odcieta
     * @param j nowa odcieta
     */
    public void setJ(int j) {
        this.j = j;
    }

    /**
     * Zwraca aktualny obrot
     * @return obrot
     */
    public int getObrot() {
        return obrot;
    }

    /**
     * Zmienia obrot
     * @param obrot nowy obrot
     */
    public void setObrot(int obrot) {
        this.obrot = obrot;
    }

    /**
     * Zwraca kolor
     * @return kolor
     */
    public Color getColor() {
        return color;
    }

    /**
     * Zwraca szerokosc
     * @return szerokosc
     */
    public int getSzerokosc() {
        return szerokosc;
    }

    /**
     * Rysuje bloczek na planszy
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
        setLocation(getJ() * dlugoscBokuElementu + 1, getI() * dlugoscBokuElementu);

        Vector<Vector<Boolean>> bloczek = pobierzBloczek();

        for (int i = 0; i < bloczek.size(); i++) {
            for (int j = 0; j < bloczek.get(i).size(); j++) {
                if (bloczek.get(i).get(j)) {
                    g.fillRect(j * dlugoscBokuElementu, i * dlugoscBokuElementu, dlugoscBokuElementu, dlugoscBokuElementu);
                }
            }
        }
    }

    /**
     * Pobiera wektory z zapisanym wygladem dla aktualnego obrotu powiekszonego o add
     * @param add o ile powiekszych obrot
     * @return wektor z zapisanym wygladem bloczka
     */
    private Vector<Vector<Boolean>> pobierzBloczek(int add) {
        switch ((obrot + add) % 4) {
            case 0:
                return obrot_1;
            case 1:
                return obrot_2;
            case 2:
                return obrot_3;
            case 3:
                return obrot_4;
        }
        return null;
    }

    /**
     * Pobiera wektory z zapisanym wygladem dla aktualnego obrotu
     * @return wektor z zapisanym wygladem bloczka
     */
    public Vector<Vector<Boolean>> pobierzBloczek() {
        return pobierzBloczek(0);
    }

    /**
     * Pobiera wektory z zapisanym wygladem dla obrotu o 90 stopni w prawo
     * @return wektor z zapisanym wygladem bloczka
     */
    public Vector<Vector<Boolean>> pobierzObroconyBloczek() {
        return pobierzBloczek(1);
    }

    /**
     * Obraca bloczek
     */
    public void obroc() {
        obrot++;
    }
}
