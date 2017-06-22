package de.zeitner.android.games.luek.listener;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import de.zeitner.android.games.luek.Karte;
import de.zeitner.android.games.luek.Spiel;

/**
 * Diese Klasse ist ein Listener und "horcht" auf eine 
 * Fingertipp-Aktion. Bei einem Ereignis hat es die Aufgabe
 * die Karte umzudrehen.
 * 
 * @author Eray Soenmez
 * @since 18.09.2014 
 * @version 1.0
 *
 */
public class CardTouchListener implements OnTouchListener {

	/*
	 * Attributes
	 */

	/** Spiel-Objekt */
	private Spiel spiel;
	
	/** Karten-Objekt */
	private Karte karte;

	/*
	 * Getter & Setter
	 */
	
	private Spiel getSpiel() {
		return spiel;
	}

	private void setSpiel(Spiel spiel) {
		this.spiel = spiel;
	}
	
	private Karte getKarte() {
		return this.karte;
	}

	private void setKarte(Karte karte) {
		this.karte = karte;
	}
	
	/**
	 * Maximal Konstruktor 
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 * 
	 * @param spiel				Spiel-Objekt
	 * @param karte 			Karten-Objekt
	 */
	public CardTouchListener(Spiel spiel, Karte karte) {
		this.setSpiel(spiel);
		this.setKarte(karte);
	}
	
	/**
	 * Standard Konstruktor 
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 * 
	 */
	public CardTouchListener(){
		this(new Spiel(), new Karte());
	}

	/**
	 * Beruehrt der Benutzer das Obejekt, wird beim loslassen die Karte
	 * umgedreht und deaktiviert, sodass man sie nicht nochmal umdrehen kann.
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 * 
	 * @param v		 			aktuelles View Objekt
	 * @param event		 		aktuelles MotionEvent Objekt
	 */
	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			this.getSpiel().checkCard(this.getKarte());
//			this.getKarte().getImageButton().setEnabled(false);
//			this.getKarte().getImageButton().setClickable(false);
			return true;
		}
		return false;
	}
	
}
