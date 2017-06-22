package de.zeitner.android.games.luek.listener;

import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.Toast;

public class CardDragListener implements OnDragListener {

    @Override
    public boolean onDrag(View v, DragEvent event) {
        final ImageButton draggedImage = (ImageButton) event.getLocalState();
        ImageButton target = (ImageButton) v;
        boolean result = false;

        int dragEvent = event.getAction();

        switch (dragEvent) {
            case DragEvent.ACTION_DROP: {

                if (target.getId() == draggedImage.getId()) {
                    result = true;

                    Toast.makeText(v.getContext(), "that's right", Toast.LENGTH_SHORT).show();
//
                    draggedImage.setAlpha(0f);

//                    draggedImage.;

//                    draggedImage.setVisibility(View.GONE);
//                    draggedImage.setVisibility(View.INVISIBLE);
//                    target.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            draggedImage.setVisibility(View.INVISIBLE);
//                        }
//                    });

                    target.setVisibility(View.INVISIBLE);
                    //draggedImage.setActivated(false);
                    //draggedImage.setEnabled(false);
                } else {
                    if (target.getId() != draggedImage.getId()) {
                        result = false;
                        Toast.makeText(v.getContext(), "that's wrong", Toast.LENGTH_SHORT).show();

                        draggedImage.post(new Runnable() {
                            @Override
                            public void run() {
                                draggedImage.setVisibility(View.VISIBLE);
                            }
                        });
                    }
//                else {
//                    Toast.makeText(v.getContext(), "this is default", Toast.LENGTH_SHORT).show();
//                    draggedImage.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            draggedImage.setVisibility(View.VISIBLE);
//                        }
//                    });

//				try{if(target.getId() == draggedImage.getId()){
//					result = true;
//					target.setAlpha(1f);
//				} }catch (Exception e){
//					result = false;
//					Toast.makeText(v.getContext(), "NOPE", Toast.LENGTH_SHORT).show();
//
//					draggedImage.post(new Runnable() {
//						@Override
//						public void run() {
//							draggedImage.setVisibility(View.VISIBLE);
//						}
//					});
                }
                break;
            }
//            case DragEvent.ACTION_DRAG_ENDED: {
//                if (draggedImage != target) {
//                    result = false;
//                    Toast.makeText(v.getContext(), "bla", Toast.LENGTH_SHORT).show();
//
//                    draggedImage.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            draggedImage.setVisibility(View.VISIBLE);
//                        }
//                    });
//                }
//                break;
//            }
            default: {
                draggedImage.post(new Runnable() {
                    @Override
                    public void run() {
                        draggedImage.setVisibility(View.VISIBLE);
                    }
                });

                break;
            }

        }
        return true;

    }
}
