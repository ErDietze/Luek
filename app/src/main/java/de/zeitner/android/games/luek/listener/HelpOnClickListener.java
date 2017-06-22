package de.zeitner.android.games.luek.listener;

import android.view.View;
import android.view.View.OnClickListener;
import de.zeitner.android.games.luek.R;
import de.zeitner.android.games.luek.Spiel;

public class HelpOnClickListener implements OnClickListener {

	Spiel spiel;

	private Spiel getSpiel() {
		return spiel;
	}

	private void setSpiel(Spiel spiel) {
		this.spiel = spiel;
	}

	public HelpOnClickListener(Spiel s) {
		this.setSpiel(s);
	}
	
	public HelpOnClickListener() {
		this(new Spiel());
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.iv_repeatsound: {
				this.getSpiel().getGesuchteKarte().getMpSprache().start();
				break;
			}
			case R.id.iv_questionmark: {
				for(int i = 0; i < this.getSpiel().getKarten().size(); i++){
					if(this.getSpiel().getKarten().get(i) != this.getSpiel().getGesuchteKarte()){
						this.getSpiel().getKarten().get(i).getImageButton().setAlpha(0.25f);
						this.getSpiel().getKarten().get(i).getImageButton().setEnabled(false);
						this.getSpiel().getKarten().get(i).getImageButton().setPressed(false);
						this.getSpiel().setCheat(1);
					}
				}
				break;
			}
		}
	}

}
