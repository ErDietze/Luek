package de.zeitner.android.games.luek;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import de.zeitner.android.games.luek.fragment.StatsFragment;
import de.zeitner.android.games.luek.listener.CardDragListener;
import de.zeitner.android.games.luek.listener.LuekCardTouchListener;

/**
 * Die Klasse Spiel ist zustaendig fuer die komplette Verwaltung.
 * D.h. Bilder auswaehlen, Karten erstellen, mischen, Karten aufs
 * Spielfeld platzieren sowie die Auswertung.
 * 
 * @author Eray Soenmez
 * @since 27.10.2014 
 * @version 1.0
 */
public class LuekSpiel {

	/*
	 * Attribute
	 */
	
	final String TAG = "Spiel";

	/** Liste der zu anzeigenden Karten */
	private ArrayList<Karte> karten;
	
	/** Liste der zu anzeigenden Karten */
	private ArrayList<Karte> topKarten;
	
	/** Liste der zu anzeigenden Karten */
	private ArrayList<Karte> bottomKarten;
	
	/** Liste der Reihen */
	private ArrayList<TableRow> reihen;
	
	/** Liste der Reihen */
	private ArrayList<TableRow> topReihen;
	
	/** Liste der Reihen */
	private ArrayList<TableRow> bottomReihen;
	
	/** Liste der geloesten Karten */
	private ArrayList<Karte> geloesteKarten;
	
	/** Unser TableLayout (Uebergabeparameter) */
	private TableLayout layout;

	/** Die Anzahl der Reihen */
	private int anzahlReihen;

	/** Die Anzahl der Spalten (Uebergabeparameter) */
	private int anzahlSpalten;

	/** Die Anzahl der Karten (Uebergabeparameter) */
	private int anzahlKarten;
	
	/** Die maximale Anzahl der zu loesenden Karten (Benutzerdefiniert) */
	private int anzahlMaxLoesungen;
		
	/** Current View Object */
	private View myView;
	
	/** The current card which have to found */
	private Karte gesuchteKarte;
	
	private Stats stats;
	
	private String packageName;
	
	private int cheat = 0;
	
	/*
	 * Getter & Setter
	 */

	public ArrayList<Karte> getKarten() {
		return this.karten;
	}

	private void setKarten(ArrayList<Karte> karten) {
		this.karten = karten;
	}

	private ArrayList<Karte> getTopKarten() {
		return topKarten;
	}

	private void setTopKarten(ArrayList<Karte> topKarten) {
		this.topKarten = topKarten;
	}

	private ArrayList<Karte> getBottomKarten() {
		return bottomKarten;
	}

	private void setBottomKarten(ArrayList<Karte> bottomKarten) {
		this.bottomKarten = bottomKarten;
	}

	private ArrayList<TableRow> getReihen() {
		return this.reihen;
	}

	private void setReihen(ArrayList<TableRow> reihen) {
		this.reihen = reihen;
	}

	private ArrayList<TableRow> getTopReihen() {
		return topReihen;
	}

	private void setTopReihen(ArrayList<TableRow> topReihen) {
		this.topReihen = topReihen;
	}

	private ArrayList<TableRow> getBottomReihen() {
		return bottomReihen;
	}

	private void setBottomReihen(ArrayList<TableRow> bottomReihen) {
		this.bottomReihen = bottomReihen;
	}

	public ArrayList<Karte> getGeloesteKarten() {
		return geloesteKarten;
	}

	private void setGeloesteKarten(ArrayList<Karte> geloesteKarten) {
		this.geloesteKarten = geloesteKarten;
	}

	private TableLayout getLayout() {
		return this.layout;
	}

	private void setLayout(TableLayout layout) {
		this.layout = layout;
	}

	private int getAnzahlReihen() {
		return this.anzahlReihen;
	}

	private void setAnzahlReihen(int anzahlReihen) {
		this.anzahlReihen = anzahlReihen;
	}

	private int getAnzahlSpalten() {
		return anzahlSpalten;
	}

	private void setAnzahlSpalten(int anzahlSpalten) {
		this.anzahlSpalten = anzahlSpalten;
	}

	private int getAnzahlKarten() {
		return anzahlKarten;
	}

	private void setAnzahlKarten(int anzahlKarten) {
		this.anzahlKarten = anzahlKarten;
	}
	
	public int getAnzahlMaxLoesungen() {
		return anzahlMaxLoesungen;
	}

	public void setAnzahlMaxLoesungen(int anzahlMaxLoesungen) {
		this.anzahlMaxLoesungen = anzahlMaxLoesungen;
	}

	public View getMyView() {
		return myView;
	}

	private void setMyView(View myView) {
		this.myView = myView;
	}
	
	public Karte getGesuchteKarte() {
		return gesuchteKarte;
	}

	private void setGesuchteKarte(Karte gesuchteKarte) {
		this.gesuchteKarte = gesuchteKarte;
	}
	
	private String getPackageName() {
		return packageName;
	}

	private void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	private int getCheat() {
		return cheat;
	}

	public void setCheat(int cheat) {
		if(cheat == 1 || cheat == 0){
			this.cheat = cheat;
		}
	}

	/**
	 * Maximal-Konstruktor Besonderheit: Da die Karten- und Spaltenanzahl
	 * bekannt sind, werden die Reihen ausgerechnet. Die MainActivity erlaubt 8
	 * Reihen.
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 * 
	 * @param layout 			Layout der Activity
//	 * @param anzahlKarten 		Die Anzahl der gewuenschten Karten
	 * @param anzahlSpalten		Die Anzahl der gewuenschten Spalten
	 */
	public LuekSpiel(TableLayout layout, int anzahlReihen, int anzahlSpalten, View myView, String contentpack) {
		this.setKarten(new ArrayList<Karte>());
		this.setTopKarten(new ArrayList<Karte>());
		this.setBottomKarten(new ArrayList<Karte>());
		this.setReihen(new ArrayList<TableRow>());
		this.setTopReihen(new ArrayList<TableRow>());
		this.setBottomReihen(new ArrayList<TableRow>());
		this.setGeloesteKarten(new ArrayList<Karte>());
		this.setLayout(layout);
		this.setAnzahlReihen(anzahlReihen);
		//this.setAnzahlReihen((int)Math.ceil(anzahlKarten / (anzahlSpalten * 1.0)));
		this.setAnzahlSpalten(anzahlSpalten);
		this.setAnzahlKarten(this.getAnzahlReihen() * this.getAnzahlSpalten());
		this.setMyView(myView);
		this.setAnzahlMaxLoesungen(10);
//		this.stats = new Stats(this.getMyView(), contentpack + "-stats.txt");
		this.setPackageName(contentpack);
		erstelleKarten();
		erstelleReihen();
	}
	
	public void start(){
		/* Spielfeld wird aufgebaut */
	
		if(this.getGeloesteKarten().size() < this.getAnzahlMaxLoesungen()){
			erstelleRaster();
		} else {
			  StatsFragment myStatsFragment = new StatsFragment();
		      Bundle infos = new Bundle();
		      infos.putString("Package", this.getPackageName());
			  FragmentManager fragmentManager = ((Activity) this.getMyView().getContext()).getFragmentManager();
			  myStatsFragment.setArguments(infos);
			  if(fragmentManager.findFragmentById(R.layout.stats_fragment) == null){
			        FragmentTransaction transaction = fragmentManager.beginTransaction();
			        transaction.replace(R.id.MainActivityRelativeLayout, myStatsFragment);
			        //transaction.addToBackStack("StatsFragment");
			        transaction.commit();
			  }
			  return;
		}
		
		scaleCards();
	}
		
	public void reset(){

		int reihe = 0;
		for (int i = 0; i < this.getAnzahlKarten(); i++) {
			this.getReihen().get(reihe).removeView(this.getKarten().get(i).getImageButton());

			if (i % this.getAnzahlSpalten() == this.getAnzahlSpalten() - 1) {
				reihe++;
			}
		}

		for(int i = 0; i < this.getKarten().size(); i++){
				this.getKarten().get(i).getImageButton().setAlpha(1f);
				this.getKarten().get(i).getImageButton().setEnabled(true);
				this.getKarten().get(i).getImageButton().setPressed(true);
		}

		this.setGesuchteKarte(null);
		Collections.shuffle(this.getKarten());
	}

	/**
	 * Standard Konstruktor Hier wird der Maximal-Konstruktor mit Dummywerten
	 * aufrufen
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 * 
	 */
	public LuekSpiel() {
		this(new TableLayout(null), 2, 3, new View(null), "defaultpack");
	}
	
	/**
	 * This methods grabs the file (text) from the assets set
	 * and returns the content as String. 
	 * 
	 * @author Eray Soenmez
	 * @since 16.10.2014 
	 * @version 1.3
	 * 
	 * @param myView			Current View
	 * @param filename			Fullpath with filename.extension
	 * @return String			text of the file
	 */
	public String loadDataFromAsset(View myView, String filename){
	  	String text = "";
        try {
            InputStream is = myView.getContext().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        }
        catch (IOException ex) {
            Log.v(TAG, ex.getMessage());
        }
		return text;
	}
	
	/**
	 * This methods grabs the file (image) from the assets set
	 * and returns as Drawable. 
	 * 
	 * @author Eray Soenmez
	 * @since 16.10.2014 
	 * @version 1.3
	 * 
	 * @param myView			Current View
	 * @param filename			Fullpath with filename.extension
	 * @return Drawable			image
	 */
	public Drawable loadImageFromAsset(View myView, String filename){
	  	Drawable image = null;
	  	 try {
	            InputStream ims = myView.getContext().getAssets().open(filename);
	            image = Drawable.createFromStream(ims, null);
	        }
	        catch(IOException ex) {
	            Log.v(TAG, ex.getMessage());
	        }
	  	 return image;
	}

	/**
	 * Je nach Anzahl der Karten, werden ImageButtons erstellt und diese an die
	 * Klasse Karte uebergeben, damit daraus richtige Karten werden. Zusaetzlich
	 * werden diese in der KartenListe (ArrayList) hinzugefuegt.
	 * Zusaetzlich werden die Karten gemischt.
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 * 
	 */
	public void erstelleKarten() {
		
		/* read config file of the choosen contentpack */
		String file = this.loadDataFromAsset(getMyView(), "contentpacks/numbers 0-9/de/config.txt");

		/* Split it and add the values into a hashmap to use it like an INI-file */
		HashMap<String, String> cpackConfig = new HashMap<String, String>();
		String[] lines = file.split("\r\n");
		
		/* add left and right of = to a hashmap */
		for(int i = 0; i < lines.length; i++){
			String[] pairs = lines[i].split("=");
			cpackConfig.put(pairs[0], pairs[1]);
		}
		
		/* make pairs between picture, sound */
		ArrayList<String> data = new ArrayList<String>();
		for(int i = 0; i < cpackConfig.size(); i++){
			if(cpackConfig.get("pic"+(i+1)) != null && cpackConfig.get("match"+(i+1)) != null)
				data.add(cpackConfig.get("pic"+(i+1))+","+cpackConfig.get("match"+(i+1)));
		}
		Collections.shuffle(data);
				
		/* Placeholder for the front picture */
		String pic = "";
		
		/* Placeholder for the name-sound file */
		String match = "";
		
		/* Placeholder for the description */
		String description = "";
				
		for (int i = 0; i < this.getAnzahlKarten() && i < data.size(); i++) {
			ImageButton button = new ImageButton(this.getLayout().getContext(), null);
			ImageButton button2 = new ImageButton(this.getLayout().getContext(), null);
			
			pic = "contentpacks/" + this.getPackageName() + "/de/gfx/front/" + data.get(i).split(",")[0];
			match = "contentpacks/" + this.getPackageName() + "/de/gfx/front/" + data.get(i).split(",")[1];
			
			Karte k = new Karte(button, 100 + i, 100, 100, pic, "", "", this.getMyView());
			Karte k2 = new Karte(button2, 100 + i, 100, 100, match, "", "", this.getMyView());
			
			k.getImageButton().setTag("TopImage");
			k2.getImageButton().setTag("BottomImage");
			
			//k2.getImageButton().setAlpha(0.75f);
			k2.getImageButton().setPressed(false);
			
			k.getImageButton().setOnTouchListener(new LuekCardTouchListener());
			k2.getImageButton().setOnDragListener(new CardDragListener());
					   	        
	        this.getTopKarten().add(k);
	        this.getBottomKarten().add(k2);
		}
		
		Collections.shuffle(this.getTopKarten());
		Collections.shuffle(this.getBottomKarten());
	}

	/**
	 * Hier werden die tableRows zur ReihenListe (ArrayList) hinzugefuegt je
	 * nach dem wieviele notig sind. Die Anzahl der benoetigten Reihen wird
	 * berechnet (Anzahl der Karten durch Anzahl der Spalten + aufrunden) Die
	 * Reihen muessen schon in der MainActivity existieren.
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014
	 * @version 1.0
	 */
	private void erstelleReihen() {
		for (int i = 0; i < this.getAnzahlReihen(); i++) {
			try {
				this.getTopReihen().add((TableRow) this.getLayout().findViewById(R.id.class.getField("CardRowTop" + (i + 1)).getInt(0)));
				this.getBottomReihen().add((TableRow) this.getLayout().findViewById(R.id.class.getField("CardRowBottom" + (i + 1)).getInt(0)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Die Karten werden den jeweiligen Reihen hinzugefuegt. Erst wenn eine
	 * Reihen komplett voll ist, wird die naechste benutzt.
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 */
	private void erstelleRaster() {
		ArrayList<Karte> angezeigteKarten = new ArrayList<Karte>();
		int reihe = 0;
		int i;
		for (i = 0; i < this.getAnzahlKarten(); i++) {
			this.getTopReihen().get(reihe).addView(this.getTopKarten().get(i).getImageButton());

			if (i % this.getAnzahlSpalten() == this.getAnzahlSpalten() - 1) {
				reihe++;
			}
		}
		
		reihe = 0;
		for (i = 0; i < this.getAnzahlKarten(); i++) {
			this.getBottomReihen().get(reihe).addView(this.getBottomKarten().get(i).getImageButton());

			if (i % this.getAnzahlSpalten() == this.getAnzahlSpalten() - 1) {
				reihe++;
			}
		}
	}
		
	/**
	 * This method scales the ImageView object to the given dp value
	 * 	 
	 * @author Eray Soenmez
	 * @since 15.10.2014
	 * @version 1.3
	 * 
//	 * @param ImageView			view
//	 * @param int				boundBoxInDp
	 */
	private void scaleImage(ImageView view, int boundBoxInDp) {
	    /* Get the ImageView and its bitmap */
	    Drawable drawing = view.getDrawable();
	    Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

	    /* Get current dimensions */
	    int width = bitmap.getWidth();
	    int height = bitmap.getHeight();

	    /* 
	     * Determine how much to scale: the dimension requiring less scaling is
	     * closer to the its side. This way the image always stays inside your
	     * bounding box AND either x/y axis touches it. 
	     */
	    float xScale = ((float) boundBoxInDp) / width;
	    float yScale = ((float) boundBoxInDp) / height;
	    float scale = (xScale <= yScale) ? xScale : yScale;

	    /* Create a matrix for the scaling and add the scaling data */
	    Matrix matrix = new Matrix();
	    matrix.postScale(scale, scale);

	    /* Create a new bitmap and convert it to a format understood by the ImageView */
	    Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width , height , matrix, true);
		BitmapDrawable result = new BitmapDrawable(scaledBitmap);
	    width = scaledBitmap.getWidth();
	    height = scaledBitmap.getHeight();

	    /* Apply the scaled bitmap */
	    view.setImageDrawable(result);
	    
	    view.setAdjustViewBounds(true);

	    /* Now change ImageView's dimensions to match the scaled image */
	    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
	    params.width = width;
	    params.height = height;
	    view.setLayoutParams(params);
	}
	
	private void scaleCards() {
		DisplayMetrics metrics = this.getLayout().getResources().getDisplayMetrics();

		final double YSIZE = metrics.heightPixels / metrics.ydpi;
		final double XSIZE = metrics.widthPixels / metrics.xdpi;

		final double SCREENSIZE = Math.sqrt(XSIZE * XSIZE + YSIZE * YSIZE);
		
		final double BASESCALE = 12.0;
		final double SCALE = 1.0d * (SCREENSIZE / BASESCALE);
		
		int value = 0;
//		
		for (int i = 0; i < this.getAnzahlKarten(); i++) {
	        
			/* For Devices (Smartphones) below 6.99 inches and 1000 pixels (height and width) */
	        if(SCREENSIZE < 6.99 && metrics.heightPixels > 1000 && metrics.widthPixels > 1000){
		        value = (int) (100*SCALE*7);
	        }
			
	        /* For Devices (Tablets) over 6.99 inch and 1000 pixels (height and width) */
	        if(SCREENSIZE >= 6.99 && metrics.heightPixels > 1000 && metrics.widthPixels > 1000){
		        value = (int) (120*SCALE*4.5);
	        }
	        
	        /* For Devices (Smartphones) below 5.98 inch and 1000 pixels (height and width) */
	        if(SCREENSIZE < 5.98 && metrics.heightPixels < 1000 && metrics.widthPixels < 1000){
		        value = (int) (100*SCALE*5.98);
	        }
	        
			scaleImage(this.getTopKarten().get(i).getImageButton(), value);
			scaleImage(this.getBottomKarten().get(i).getImageButton(), value);
		}
	}
	
	/**
	 * Das Spiel kann Karten wenden. Diese Methode wird durch ein Ereignis aufgerufen.
	 * Sie ueberprueft, ob die umzudrehende Karte wirklich umgedreht werden soll.
	 * Generell duerfen alle Karten umgedreht werden, welche die Rueckseite zeigen.
	 * 
	 * Aussnahme: Wenn bereits 2 Karten offen sind, darf man eine offene Karte auswaehlen
	 * um damit weiter zu machen.
	 * 
	 * Kartenpaare, die bereits geloest worden sind, kann man nicht mehr umdrehen.
	 * 
	 * @author Eray Soenmez
	 * @since 19.09.2014 
	 * @version 1.0
	 * 
	 * @param karte 		Ein Karten-Objekt welches gewendet werden soll.
	 */
	public void checkCard(Karte karte){	
		
		Log.v(TAG, karte.bilddatei);
		this.stats.rehash();
		String cardpicname = getGesuchteKarte().bilddatei.split("/")[5];
		
		int correct = Integer.valueOf(this.stats.content.get(cardpicname).split("#")[0]);
		int wrong = Integer.valueOf(this.stats.content.get(cardpicname).split("#")[1]);
		
		Log.v(TAG, cardpicname + "Richtig: " + correct + " | Falsch: " + wrong);
		
		if(this.getGesuchteKarte().getSprachdatei().equals(karte.getSprachdatei())){
			this.getGeloesteKarten().add(karte);
			MediaPlayer resourcePlayer = MediaPlayer.create(this.getLayout().getContext(), R.raw.right);
			resourcePlayer.start();
			
			if(this.getCheat() == 0){
				this.stats.storage.replaceFileString(
						cardpicname + "=" + correct + "#" + wrong, 
						cardpicname + "=" + (correct+1) + "#" + wrong
				);
			}
			
			try {
				Thread.sleep(1300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//this.reset();
			this.start();
		} else {
			MediaPlayer resourcePlayer = MediaPlayer.create(this.getLayout().getContext(), R.raw.wrong);
			resourcePlayer.start();
			
			this.stats.storage.replaceFileString(
					cardpicname + "=" + correct + "#" + wrong, 
					cardpicname + "=" + correct + "#" + (wrong+1)
			);
			
			karte.getImageButton().setAlpha(0.25f);
			karte.getImageButton().setEnabled(false);
			karte.getImageButton().setPressed(false);
		}
		
		this.setCheat(0);
	}
}
