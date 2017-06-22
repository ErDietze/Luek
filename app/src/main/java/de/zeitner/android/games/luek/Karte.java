package de.zeitner.android.games.luek;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;

/**
 * Die Klasse Karte definiert eine Spielkarte auf dem Feld.
 * Die Attribute der Karte beschreiben diese ziemlich genau.
 * 
 * @author Eray Soenmez
 * @since 18.09.2014 
 * @version 1.0
 *
 */
public class Karte {
	
	/*
	 * Attribute
	 */
	
	private final static String TAG = "Karte";
	
	/** Eine Schaltflaeche, welche die Karte wiederspiegelt */
    private ImageButton imagebutton;
    
    /** ID der Karte */
    private View myView;
    
    /** ID der Karte */
    private int id;
    
    /** Hoehe des Bildes */
    private int hoehe;
    
    /** Breite des Bildes */
    private int breite;
    
    /** Resource-ID der Vorderseite der Karte */
    private Drawable bildVorderseite;
        
    /** Resource-String der Sprachdatei der Karte */
    private String sprachdatei;
    
    /** Resource-String der Bilddatei der Karte */
    public String bilddatei;
        
    /** MediaPlayer fuer die Sprachausgabe der Karte */
    private MediaPlayer mpSprache;
    
    /** Dazugehoeriger Text der Karte */
    private String description;
    
    /*
     * Getter & Setter
     */
    
    public ImageButton getImageButton() {
        return this.imagebutton;
    }

    private void setImageButton(ImageButton imagebutton) {
        this.imagebutton = imagebutton;
    }

	private View getMyView() {
		return myView;
	}

	private void setMyView(View myView) {
		this.myView = myView;
	}

	public int getId() {
        return this.id;
    }

    private void setId(int id) {
        this.id = id;
    }

    private int getHoehe() {
        return this.hoehe;
    }

    private void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    private int getBreite() {
        return this.breite;
    }

    private void setBreite(int breite) {
        this.breite = breite;
    }

    public Drawable getBildVorderseite() {
        return this.bildVorderseite;
    }

    private void setBildVorderseite(Drawable bild) {
        this.bildVorderseite = bild;
    }

    public String getSprachdatei() {
		return this.sprachdatei;
	}

	private void setSprachdatei(String sprachdatei) {
		this.sprachdatei = sprachdatei;
	}
	
	public MediaPlayer getMpSprache() {
		return mpSprache;
	}

	private void setMpSprache(MediaPlayer mpSprache) {
		this.mpSprache = mpSprache;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
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
	 * Maximal Konstruktor
	 * Die Karte benoetigt ein bereit erstelltes Objekt vom Typ ImageButton um damit weiter arbeiten zu koennen.
	 * Die weiteren Parameter sind dazu da um den ImageButton weiter zu spezifizieren (id, hoehe, breite, ...)
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 * 
	 * @param imagebutton 		Das ImageButton Objekt
	 * @param id 				Die ID der Karte
	 * @param hoehe				Die gewuenschte Hoehe des ImageButtons
	 * @param breite			Die gewuenschte Breite des ImageButtons
	 * @param bildVorderseite	Drawable des Bildes fuer die Vorderseite der Karte
	 * @param bildRueckseite	Drawable des Bildes fuer die Rueckseite der Karte
	 * @param sprache			Der Ressource-String der Sprachdatei fuer die Karte
	 * @param geraeusch			Der Ressource-String der Geraeuschdatei fuer die Karte
	 * @param flip				Der Ressource-String der Geraeuschdatei fuer die Karte
	 * @param myView			aktuelles View-Objekt
	 */
	public Karte(ImageButton imagebutton, int id, int hoehe, int breite, String bildVorderseite, String sprache, String text, View myView){
    	super();
    	
    	/* Objekt-Attribute inizialisieren */
    	this.setImageButton(imagebutton);
    	this.setMyView(myView);
    	this.setId(id);
    	this.setHoehe(hoehe);
    	this.setBreite(breite);
    	this.bilddatei = bildVorderseite;
    	this.setBildVorderseite(loadImageFromAsset(this.getMyView(), bildVorderseite));
    	this.setSprachdatei(sprache);
    	this.setDescription(text);
    	
    	/* Karten-Attribute (ImageButton) setzen */
    	this.getImageButton().setId(this.getId());
        this.getImageButton().setImageDrawable(this.getBildVorderseite());

        this.getImageButton().setMaxHeight(this.getHoehe());
        this.getImageButton().setMaxWidth(this.getBreite());
        
        this.getImageButton().setMinimumHeight(this.getHoehe());
        this.getImageButton().setMinimumWidth(this.getBreite());
        
        this.getImageButton().setAdjustViewBounds(true);
        this.getImageButton().setScaleType(ScaleType.FIT_CENTER);
        
        
        /* MediaPlayer Einstellungen */        
    	this.setMpSprache(new MediaPlayer());
		try {
			AssetFileDescriptor afd = this.getImageButton().getContext().getAssets().openFd(this.getSprachdatei());
			getMpSprache().setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
			getMpSprache().prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Standard Konstruktor
	 * Hier wird der Maximal-Konstruktor mit Dummywerten aufgerufen
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 */
    public Karte (){
    	this(new ImageButton(null),0,100,100,null,"sprachdatei","description",new View(null));
    }
    
    @Override
    public String toString() {
    	return "Card ID: " + this.getId();
    }
    
    /**
     * Vergleichsfunktion.
     * 2 Objekte des selben Typs sind erst gleich, wenn beide die selbe ID haben. 
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 * 
	 * @param karte 		Karten Objekt zum Vergleichen
	 */
    public boolean equals(Karte karte) {
    	boolean result = false;
    	    	
    	if(karte.getId() == this.getId()){
    		result = true;
    	}
    	return result;
    }
    
    /**
     * Typische clone() Methode
	 * 
	 * @author Eray Soenmez
	 * @since 18.09.2014 
	 * @version 1.0
	 */
// 	  @Override
//    protected Object clone() throws CloneNotSupportedException {
//    	return new Karte(this.getImageButton(), this.getId(), this.getHoehe(), this.getBreite(), this.getBildVorderseite(), this.getSprachdatei(), this.getDescription(), this.getMyView());
//    }
 
}