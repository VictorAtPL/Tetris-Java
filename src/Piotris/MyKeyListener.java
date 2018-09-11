package Piotris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa nasluchiwacza klawiszy. Obsluguje klawisze strzalek i spacje. Po nacisnieciu klawisza nastepuja zmiany w planszy gry.
 * @author Piotr Podbielski
 * Created by Piotr on 13.06.2016.
 */
public class MyKeyListener implements KeyListener {
    /**
     * aktualna gra
     */
    private Gra gra;

    /**
     * Konstruktor przypisujacy gre
     * @param gra aktualna gra
     */
    public MyKeyListener(Gra gra) {
        this.gra = gra;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Akcja wywolana po wcisnieciu klawisza
     * @param e zdarzenie nacisniecia klawisza
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (gra.getWatekStatus() != null && gra.getPlansza().getBloczek() != null) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (gra.getPlansza().czyMoznaPrzesunacBloczek(-1)) {
                        gra.getPlansza().getBloczek().setJ(gra.getPlansza().getBloczek().getJ() - 1);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (gra.getPlansza().czyMoznaPrzesunacBloczek(1)) {
                        gra.getPlansza().getBloczek().setJ(gra.getPlansza().getBloczek().getJ() + 1);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    gra.getTimerGry().stop();
                    if (gra.getPlansza().czyMoznaOpuscicBloczek()) {
                        Bloczek bloczek = gra.getPlansza().getBloczek();
                        bloczek.setI(bloczek.getI() + 1);
                    }
                    else {
                        gra.getPlansza().przeniesBloczekDoPlanszy();
                        gra.getPlansza().losujBloczek();
                    }
                    gra.getTimerGry().start();
                    break;
                case KeyEvent.VK_SPACE:
                    gra.getTimerGry().stop();
                    while (gra.getPlansza().czyMoznaOpuscicBloczek()) {
                        Bloczek bloczek = gra.getPlansza().getBloczek();
                        bloczek.setI(bloczek.getI() + 1);
                    }
                    gra.getPlansza().przeniesBloczekDoPlanszy();
                    gra.getPlansza().losujBloczek();
                    gra.getTimerGry().restart();
                    break;
                case KeyEvent.VK_UP:
                    if (gra.getPlansza().czyMoznaObrocicKlocek()) {
                        gra.getPlansza().obrocKlocek();
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
