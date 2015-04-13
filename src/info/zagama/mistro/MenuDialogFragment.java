package info.zagama.mistro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;



public class MenuDialogFragment extends DialogFragment {


	static MenuDialogFragment newInstance() {
        return new MenuDialogFragment();
    }
	
	
	private AlertDialog  mMenuDialog;
	Bundle info;
	private Intent music;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
    
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.menu_dialog, null, false);
       
        music = new Intent();
		music.setClass(getActivity(), MusicService.class);
        
        ImageButton btnew = (ImageButton) view.findViewById(R.id.iBnew);
        ImageButton btexit = (ImageButton) view.findViewById(R.id.iBexit);
        ImageButton bthow = (ImageButton) view.findViewById(R.id.iBhow);
        btnew.setOnClickListener( new ImageButton.OnClickListener()
        {
        	public void onClick(View v) {
        		
        		Intent gotoRound = new Intent();
        		gotoRound.setClass(getActivity(), Player.class);
    			startActivity(gotoRound);
    			getActivity().finish();
        	}


        	});
        
        btexit.setOnClickListener( new ImageButton.OnClickListener()
        {
        	public void onClick(View v) {
        		
        		getActivity().stopService(music);
        		Intent gotoRound = new Intent();
        		gotoRound.setClass(getActivity(), ThankU.class);
    			startActivity(gotoRound);
        	}


        	});
        
        bthow.setOnClickListener( new ImageButton.OnClickListener()
        {
        	public void onClick(View v) {
        		AlertDialogFragment cdf = AlertDialogFragment.newInstance();
        		cdf.show(getFragmentManager(), "alertdialog");
        	}


        	});
         
        
        mMenuDialog = new AlertDialog.Builder(getActivity())
     	.setView(view)
        .create();	
		
		return mMenuDialog;
		
		
	}
	


}
