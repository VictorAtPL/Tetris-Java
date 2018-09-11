package Piotris;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa odpowiadajaca za wyswietlanie juz opuszczonych bloczkow
 * @author Piotr Podbielski
 * Created by Piotr on 13.06.2016.
 */
public class ElementBloczku extends JComponent {
    /**
     * Pozycja rzednej
     */
    private int i;

    /**
     * Pozycja odcietej
     */
    private int j;

    /**
     * Kolor
     */
    private Color color;

    /**
     * Dlugosc boku
     */
    private int dlugoscBokuElementu;

    /**
     * Konstruktor ustawiajacy rozmiar elementu bloczku
     * @param i pozycja rzednej
     * @param j pozycja odcietej
     * @param color kolor
     * @param dlugoscBokuElementu dlugosc boku
     */
    public ElementBloczku(int i, int j, Color color, int dlugoscBokuElementu) {
        this.i = i;
        this.j = j;
        this.color = color;
        this.dlugoscBokuElementu = dlugoscBokuElementu;

        setSize(dlugoscBokuElementu, dlugoscBokuElementu);
    }

    /**
     * Rysuje element bloczku w odpowiednim kolorze
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
        setLocation(getJ() * dlugoscBokuElementu + 1, getI() * dlugoscBokuElementu);

        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Zwraca rzedna
     * @return rzedna
     */
    public int getI() {
        return i;
    }

    /**
     * Zwraca odcieta
     * @return odcieta
     */
    public int getJ() {
        return j;
    }

    /**
     * Zmiena rzedna
     * @param i nowa rzedna
     */
    public void setI(int i) {
        this.i = i;
    }
}
