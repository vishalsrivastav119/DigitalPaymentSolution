package com.example.dpouch;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class YearPickerFragment extends DialogFragment implements OnDateSetListener {
	
	TextView spinnertextView;

	public YearPickerFragment()
	{

	}

//	public YearPickerFragment(TextView spinnerText) {
//
//		spinnertextView=spinnerText;
//	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final Calendar c=Calendar.getInstance();
		    int year = c.get(Calendar.YEAR);
		    int month=c.get(Calendar.MONTH);
		    int date=c.get(Calendar.DATE);
		    DatePickerDialog datePicker=new DatePickerDialog(getActivity(), this, year, month, date);
	      datePicker.getDatePicker().findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
	      datePicker.getDatePicker().findViewById(Resources.getSystem().getIdentifier("month", "id", "android")).setVisibility(View.GONE);

	     // Create a new instance of DatePickerDialog and return it
	        return datePicker;
	       
	        
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		Bundle bundle = this.getArguments();

		StringBuilder st =new StringBuilder(year);
		st.append(year);
		if(bundle!=null)
		{

			int  spinnerYear=bundle.getInt("spinnerYear");
			spinnertextView=(TextView) getActivity().findViewById(spinnerYear);
		}
		spinnertextView.setText(st);
		
		
		
	}

}
