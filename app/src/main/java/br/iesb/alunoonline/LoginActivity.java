package br.iesb.alunoonline;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtEmail;
    private EditText txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);


        Button bt = (Button) findViewById(R.id.btnLogin);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtEmail.getText().toString().equals("") || txtSenha.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"E-mail ou senha é obrigado preencher!",Toast.LENGTH_LONG).show();
                }else{
                    login();
                }
            }
        });
        TextView Cadastrar = findViewById(R.id.LinkCadastro);
        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(LoginActivity.this, FormularioActivity.class);
                startActivity(t);
            }
        });

        TextView Recuperar = findViewById(R.id.LinkRecSenha);
        Recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
                startActivity(t);
            }
        });

    }
    private void login(){
        mAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this,"Não foi possivel efetuar login.",Toast.LENGTH_LONG).show();
                            }else{
                                Intent t = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(t);
                            }



                    }
                });
    }
}
