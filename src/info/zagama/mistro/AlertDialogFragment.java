package info.zagama.mistro;



import info.zagama.mistro.R;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;



public class AlertDialogFragment extends DialogFragment {


	static AlertDialogFragment newInstance() {
        return new AlertDialogFragment();
    }
	
	
	private AlertDialog mAlertDialog;
	private SharedPreferences profile;
	Bundle info;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		profile = this.getActivity().getSharedPreferences("profile",0);
    
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog, null, false);
       
		
         
        
		mAlertDialog = new AlertDialog.Builder(getActivity())
     	.setTitle("提示")
     	.setView(view)
     	.setPositiveButton("了解", new DialogInterface.OnClickListener () {
             public void onClick(DialogInterface dialog, int which) {
            	
         		profile.edit()
         		.putInt("have_play",1)
         		.commit();
         		
         		int have_play = profile.getInt("have_play", 0);
        		
             }
		})
        .create();	
		
		return mAlertDialog;
		
		
	}
	


}