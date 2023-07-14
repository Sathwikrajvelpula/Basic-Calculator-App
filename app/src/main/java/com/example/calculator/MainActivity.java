package com.example.calculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.Object.*;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView resu_tv,solun_tv;
    MaterialButton btnC,btnBrackop,btnBrackcl, btndiv,btnmul,btnadd,btnsub,btneql,btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    MaterialButton btnac,btndot,btnsq,btnsqrt,btnpown,btnfac,btnlog,btninv;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resu_tv = findViewById(R.id.result_tv);
        solun_tv = findViewById(R.id.solution_tv);
        btnsq = findViewById(R.id.btn_sqr);
        btnsqrt = findViewById(R.id.btn_sqroot);
        btnfac = findViewById(R.id.btn_fac);
        btnlog = findViewById(R.id.btn_log);
        btnpown = findViewById(R.id.btn_pown);
        btninv = findViewById(R.id.btn_inv);



        assinId(btn0,R.id.btn_0);assinId(btn1,R.id.btn_1);assinId(btn2,R.id.btn_2);
        assinId(btn3,R.id.btn_3);assinId(btn4,R.id.btn_4);assinId(btn5,R.id.btn_5);
        assinId(btn6,R.id.btn_6);assinId(btn7,R.id.btn_7);assinId(btn8,R.id.btn_8);
        assinId(btn9,R.id.btn_9);assinId(btnC,R.id.btn_c);assinId(btnBrackop,R.id.btn_ob);
        assinId(btnBrackcl,R.id.btn_cb);assinId(btndiv,R.id.btn_div);assinId(btndot,R.id.btn_dot);
        assinId(btnmul,R.id.btn_mul);assinId(btnsub,R.id.btn_sub);assinId(btnadd,R.id.btn_add);
        assinId(btnac,R.id.btn_ac);assinId(btneql,R.id.btn_equal);

        btnsqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (solun_tv.getText().toString() != "") {
                    String val = solun_tv.getText().toString();
                    double re = Math.sqrt(Double.parseDouble(val));
                    resu_tv.setText(String.valueOf(re));
                    solun_tv.setText("√" + val);
                }
                else{
                    resu_tv.setText("Invalid syntax");
                }
            }

        });
        btnsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (solun_tv.getText().toString() != "") {
                    double va = Double.parseDouble(solun_tv.getText().toString());
                    double sqe = va*va;
                    resu_tv.setText(String.valueOf(sqe));
                    solun_tv.setText(va+"²");
                }
                else{
                    resu_tv.setText("Invalid syntax");
                }
            }
        });
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (solun_tv.getText().toString() != "") {
                    String val = solun_tv.getText().toString();
                    double num1 = Double.parseDouble(val);
                    resu_tv.setText(Math.log10(num1) + "");
                    solun_tv.setText("log(" + val + ")");
                }
                else{
                    solun_tv.setText("Enter value :");
                }
            }
        });
        btnfac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (solun_tv.getText().toString() != "") {
                    double vafac = Double.parseDouble(solun_tv.getText().toString());
                    double count = 1;
                    for (double i = vafac; i >= 2; i--) {
                        count = count * i;
                    }
                    resu_tv.setText(String.valueOf(count));
                    solun_tv.setText(vafac + "!");
                }
                else{
                    resu_tv.setText("Invalid syntax");
                }
            }
        });
        btnpown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (solun_tv.getText().toString() != "") {
                    double va = Double.parseDouble(solun_tv.getText().toString());
                    double sqe = va * va * va;
                    resu_tv.setText(String.valueOf(sqe));
                    solun_tv.setText(va + "^3");
                }
                else{
                    resu_tv.setText("Invalid syntax");
                }
            }
        });
        btninv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (solun_tv.getText().toString() != "") {
                    double va = Double.parseDouble(solun_tv.getText().toString());
                    double sqe = 1 / va;
                    resu_tv.setText(String.valueOf(sqe));
                    solun_tv.setText("1/" + va);
                }
                else{
                    resu_tv.setText("Invalid syntax");
                }
            }
        });


    }
    void assinId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton btn = (MaterialButton) view;
        String btntxt = btn.getText().toString();
        String data = solun_tv.getText().toString();

        if(btntxt.equals("B")){
            if(data!="") {
                data = data.substring(0, data.length() - 1);
            }
            else{
                solun_tv.setText("0");
                resu_tv.setText("0");
            }
        }else{
            data = data+btntxt;
        }
        if(btntxt.equals("AC")){
            solun_tv.setText("");
            resu_tv.setText("0");
            return;
        }
        if(btntxt.equals("=")){
            solun_tv.setText(resu_tv.getText());
            return;
        }

        solun_tv.setText(data);

        String finres = getRes(data);
        if(!finres.equals("Error")){
            resu_tv.setText(finres);
        }

    }
    String getRes(String dataa){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finres = context.evaluateString(scriptable,dataa,"javascript",1,null).toString();
            return finres;
        }catch (Exception e){
            return "Error";
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to exit");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}