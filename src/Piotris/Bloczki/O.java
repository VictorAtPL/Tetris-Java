package Piotris.Bloczki;

import Piotris.Bloczek;

import java.awt.*;
import java.util.Vector;

/**
 * Typ bloczka wynikajacy z gry Tetris - Tetromino
 * @author Piotr Podbielski
 * Created by Piotr on 13.06.2016.
 */
public class O extends Bloczek {
    /**
     * Konstruktor ustalacy wektory wygladu bloczka dla konkretnego obrotu
     * @param dlugoscBokuElementu dlugosc boku elementu bloczka
     */
    public O(int dlugoscBokuElementu) {
        super(2, 2, Color.cyan, dlugoscBokuElementu);

        obrot_1 = new Vector<>(2);
        obrot_1.add(new Vector<>(2));
        obrot_1.add(new Vector<>(2));
        obrot_1.get(0).addElement(true);
        obrot_1.get(0).addElement(true);
        obrot_1.get(1).addElement(true);
        obrot_1.get(1).addElement(true);

        obrot_2 = obrot_3 = obrot_4 = obrot_1;
    }
}
