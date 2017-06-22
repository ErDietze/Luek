package de.zeitner.android.games.luek;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Is class is made to read from and write to the harddisk.
 * Especially for wrting and reading a config-file like an ini-file.
 * 
 * @author Eray Soenmez
 * @since 30.09.2014 
 * @version 1.2
 *
 */
public class Storage {
	
	/*
	 * Attributes
	 */
	
	/* for debugging only */
	private final static String TAG = "Storage";
	
	/** The path of the file */
	private String path;
	
	/** Current View Object */
	private View myView;
	
	/*
	 * Getter & Setter
	 */

	private View getMyView() {
		return myView;
	}

	private void setMyView(View myView) {
		this.myView = myView;
	}
	
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Max Constructor 
	 * 
	 * @author Eray Soenmez
	 * @since 30.09.2014 
	 * @version 1.2
	 * 
	 * @param View 			myView
	 */
	public Storage(View myView, String path){
		this.setMyView(myView);
		this.setPath(path);
		/* for debugging only */
		//Log.d(TAG, "getFilesDir(): " + this.getMyView().getContext().getFilesDir());
	}
	
	public Storage(View myView){
		this(myView, "config.txt");
	}
	
	/**
	 * Standard Constructor 
	 * 
	 * @author Eray Soenmez
	 * @since 30.09.2014 
	 * @version 1.2
	 * 
	 */
	public Storage(){
		this(new View(null));
	}
	
	/**
	 * This method returns a container (HashMap) which
	 * contains a key and a value. It has the values
	 * of the config-file in it and can be gained by
	 * calling the key.
	 * 
	 * @author Eray Soenmez
	 * @since 30.09.2014 
	 * @version 1.2
	 * 
	 * @return HashMap		map
	 */
	public HashMap<String, String> getHashMap(){
		String[] lines = readFromFile().split("\r\n");
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(int i = 0; i < lines.length; i++){
			String[] pairs = lines[i].split("=");
			if(pairs.length > 1){
				map.put(pairs[0], pairs[1]);
			}
		}
		
		return map;
	}
	
	/**
	 * This method writes the given string into the
	 * pre-defined file (PATH).
	 * 
	 * @author Eray Soenmez
	 * @since 30.09.2014 
	 * @version 1.2
	 * 
	 * @param String		s
	 */
	public void writeToFile(String s) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = this.getMyView().getContext().openFileOutput(this.getPath(), Context.MODE_PRIVATE | Context.MODE_APPEND);
			osw = new OutputStreamWriter(fos);
			osw.write(s + "\r\n");
		} catch (Throwable t) {
			// FileNotFoundException, IOException
			Log.e(TAG, "save()", t);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					Log.e(TAG, "osw.close()", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					Log.e(TAG, "fos.close()", e);
				}
			}
		}
	}
	
	/**
	 * This method replaces text with the 
	 * given string in the pre-defined file (PATH).
	 * 
	 * @author Eray Soenmez
	 * @since 30.09.2014 
	 * @version 1.2
	 * 
	 * @param String 		search - String which should be replaced
	 * @param String		replace - String which replaces the found string 
	 */
	public void replaceFileString(String search, String replace) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		String text = this.readFromFile();
		try {
			fos = this.getMyView().getContext().openFileOutput(this.getPath(), Context.MODE_PRIVATE);
			osw = new OutputStreamWriter(fos);
			text = text.replaceAll(search, replace);
			osw.write(text);
		} catch (Throwable t) {
			// FileNotFoundException, IOException
			Log.e(TAG, "save()", t);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					Log.e(TAG, "osw.close()", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					Log.e(TAG, "fos.close()", e);
				}
			}
		}
	}
	
	/**
	 * This method reads from the pre-defined file (PATH).
	 * 
	 * @author Eray Soenmez
	 * @since 30.09.2014
	 * @version 1.2
	 * 
	 * @return String		s
	 */
	public String readFromFile() {
		StringBuilder sb = new StringBuilder();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = this.getMyView().getContext().openFileInput(this.getPath());
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String s;

			while ((s = br.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append("\r\n");
				}
				sb.append(s);
			}
		} catch (Throwable t) {
			// FileNotFoundException, IOException
			Log.e(TAG, "load()", t);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					Log.e(TAG, "br.close()", e);
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					Log.e(TAG, "isr.close()", e);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					Log.e(TAG, "fis.close()", e);
				}
			}
		}
				
		return sb.toString();
    }
}
