package com.mobile.projetoone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class form_login extends AppCompatActivity {

    private TextView text_view_create_account;
    private EditText edit_email, edit_pass;
    private Button btn_login;
    private ProgressBar progress_bar;
    String[] mensagens = {"Preencha todos os campos!"};
    /*private Button btn_pass;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        InicializarComponentes();
        getSupportActionBar().hide();


        text_view_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(form_login.this, form_cadastro.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit_email.getText().toString();
                String pass = edit_pass.getText().toString();

                if(email.isEmpty() || pass.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    AutenticarUsuario(v, email, pass);
                }

            }
        });
        /*btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerSenha();
            }
        });*/
    }
    private void AutenticarUsuario(View v,String email, String pass){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progress_bar.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    FormMain();
                                }
                            }, 3000);
                        } else {
                            String erro;
                            try {
                                throw task.getException();
                            } catch (Exception e) {
                                erro = "Erro ao autenticar usuário!";
                            }
                            Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.RED);
                            snackbar.setTextColor(Color.WHITE);
                            snackbar.show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
        if(userAtual != null){
            FormMain();
        }
    }
    /*private void VerSenha() {
        if(edit_pass.getInputType() == 129) {
            edit_pass.setInputType(128);
            btn_pass.setText("Ocultar");
        }else{
            edit_pass.setInputType(129);
            btn_pass.setText("Mostrar");
        }
    }*/

    private void FormMain(){
        Intent intent = new Intent(form_login.this, form_main.class);
        startActivity(intent);
        finish();
    }

    private void InicializarComponentes(){
        text_view_create_account = findViewById(R.id.textview_create_account);
        edit_email = findViewById(R.id.edit_email);
        edit_pass = findViewById(R.id.edit_pass);
        btn_login = findViewById(R.id.btn_login);
        progress_bar = findViewById(R.id.progress_bar);
    }
}