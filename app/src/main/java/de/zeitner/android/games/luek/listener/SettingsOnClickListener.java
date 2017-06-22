package de.zeitner.android.games.luek.listener;

import android.view.View;
import android.view.View.OnClickListener;
import de.zeitner.android.games.luek.Spiel;

public class SettingsOnClickListener implements OnClickListener {

	Spiel spiel;
	
	private Spiel getSpiel() {
		return spiel;
	}

	private void setSpiel(Spiel spiel) {
		this.spiel = spiel;
	}

	public SettingsOnClickListener(Spiel spiel) {
		this.setSpiel(spiel);
	}
	
	@Override
	public void onClick(View v) {
		// custom dialog
//		final Dialog dialog = new Dialog(v.getContext());
//		dialog.setContentView(R.layout.gamelength_dialog);
//		dialog.setTitle("Settings");
//		dialog.show();
//		
//		final NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.nP_dialog_value);
//        numberPicker.setMaxValue(this.getSpiel().getKarten().size());
//        numberPicker.setMinValue(1);
//        numberPicker.setWrapSelectorWheel(true);
//		
//		Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog_ok);
//		dialogButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				SettingsOnClickListener.this.getSpiel().setAnzahlMaxLoesungen(numberPicker.getValue());
//				SettingsOnClickListener.this.getSpiel().getGeloesteKarten().clear();
//				
//				TextView tv_progress = (TextView)SettingsOnClickListener.this.getSpiel().getMyView().findViewById(R.id.tv_progress);
//				tv_progress.setText("Card " + (SettingsOnClickListener.this.getSpiel().getGeloesteKarten().size()+1) + " of " + SettingsOnClickListener.this.getSpiel().getAnzahlMaxLoesungen());
//				
//				dialog.dismiss();
//			}
//		});
	}
}
