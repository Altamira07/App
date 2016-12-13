package com.example.luis.app.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.luis.app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReportGenerateActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner spnPeriod;
    Button btnMax,btnMin,btnGeneReport;
    DatePickerDialog.OnDateSetListener datepicker;
    Calendar calendar;
    TextView txvMaxima,txvMinima;
    String period ="week";
    boolean periodOrDate;
    int btnDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_generate);
        spnPeriod = (Spinner) findViewById(R.id.spnPeriodTime);
        ArrayAdapter arrayPeriod = ArrayAdapter.createFromResource(this,R.array.spnPeriod,android.R.layout.simple_spinner_item);
        arrayPeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPeriod.setAdapter(arrayPeriod);
        spnPeriod.setOnItemSelectedListener(this);
        btnMax = (Button) findViewById(R.id.btnFechaMaxima);
        btnMax.setOnClickListener(this);
        btnMin = (Button) findViewById(R.id.btnFechaMinima);
        btnMin.setOnClickListener(this);
        btnGeneReport = (Button) findViewById(R.id.btnGenerarReporte);
        btnGeneReport.setOnClickListener(this);
        txvMaxima = (TextView) findViewById(R.id.txvFechaMaxima);
        txvMinima = (TextView) findViewById(R.id.txvMinima);
        calendar = Calendar.getInstance();
        datepicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                cargarFecha();
            }
        };
    }
    public  void checkPeriod (View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        periodOrDate = checked;
    }
    private  void cargarFecha()
    {
        if (btnDate == R.id.btnFechaMaxima){
            cargarTexview(txvMaxima);
        } else if (btnDate == R.id.btnFechaMinima){
            cargarTexview(txvMinima);
        }

    }
    public void cargarTexview(TextView txv)
    {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txv.setText(sdf.format(calendar.getTime()));
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i)
        {
            case 0:
                period = "week";
                break;
            case 1:
                period = "month";
                break;
            case 2:
                period ="last_month";
                break;
            case 3:
                period = "year";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void cargarPicker()
    {
        new DatePickerDialog(this, datepicker, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnFechaMaxima:
                btnDate = R.id.btnFechaMaxima;
                cargarPicker();
                break;
            case R.id.btnFechaMinima:
                btnDate = R.id.btnFechaMinima;
                cargarPicker();
                break;
            case R.id.btnGenerarReporte:
                Intent i = new Intent(this,ReportActivity.class);
                i.putExtra("period",period);
                i.putExtra("max",txvMaxima.getText().toString());
                i.putExtra("min",txvMinima.getText().toString());

                i.putExtra("periodOrDate",periodOrDate);
                startActivity(i);
                break;
        }
    }
}
