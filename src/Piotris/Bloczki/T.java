package Piotris.Bloczki;

import Piotris.Bloczek;

import java.awt.*;
import java.util.Vector;

/**
 * Typ bloczka wynikajacy z gry Tetris - Tetromino
 * @author Piotr Podbielski
 * Created by Piotr on 13.06.2016.
 */
public class T extends Bloczek {
    /**
     * Konstruktor ustalacy wektory wygladu bloczka dla konkretnego obrotu
     * @param dlugoscBokuElementu dlugosc boku elementu bloczka
     */
    public T(int dlugoscBokuElementu) {
        super(3, 3, Color.gray, dlugoscBokuElementu);

        obrot_1 = new Vector<>(3);
        obrot_1.add(new Vector<>(3));
        obrot_1.add(new Vector<>(3));
        obrot_1.add(new Vector<>(3));
        obrot_1.get(0).addElement(false);
        obrot_1.get(0).addElement(true);
        obrot_1.get(0).addElement(false);
        obrot_1.get(1).addElement(true);
        obrot_1.get(1).addElement(true);
        obrot_1.get(1).addElement(true);
        obrot_1.get(2).addElement(false);
        obrot_1.get(2).addElement(false);
        obrot_1.get(2).addElement(false);

        obrot_2 = new Vector<>(3);
        obrot_2.add(new Vector<>(3));
        obrot_2.add(new Vector<>(3));
        obrot_2.add(new Vector<>(3));
        obrot_2.get(0).addElement(false);
        obrot_2.get(0).addElement(true);
        obrot_2.get(0).addElement(false);
        obrot_2.get(1).addElement(false);
        obrot_2.get(1).addElement(true);
        obrot_2.get(1).addElement(true);
        obrot_2.get(2).addElement(false);
        obrot_2.get(2).addElement(true);
        obrot_2.get(2).addElement(false);

        obrot_3 = new Vector<>(3);
        obrot_3.add(new Vector<>(3));
        obrot_3.add(new Vector<>(3));
        obrot_3.add(new Vector<>(3));
        obrot_3.get(0).addElement(false);
        obrot_3.get(0).addElement(false);
        obrot_3.get(0).addElement(false);
        obrot_3.get(1).addElement(true);
        obrot_3.get(1).addElement(true);
        obrot_3.get(1).addElement(true);
        obrot_3.get(2).addElement(false);
        obrot_3.get(2).addElement(true);
        obrot_3.get(2).addElement(false);

        obrot_4 = new Vector<>(3);
        obrot_4.add(new Vector<>(3));
        obrot_4.add(new Vector<>(3));
        obrot_4.add(new Vector<>(3));
        obrot_4.get(0).addElement(false);
        obrot_4.get(0).addElement(true);
        obrot_4.get(0).addElement(false);
        obrot_4.get(1).addElement(true);
        obrot_4.get(1).addElement(true);
        obrot_4.get(1).addElement(false);
        obrot_4.get(2).addElement(false);
        obrot_4.get(2).addElement(true);
        obrot_4.get(2).addElement(false);
    }
}
