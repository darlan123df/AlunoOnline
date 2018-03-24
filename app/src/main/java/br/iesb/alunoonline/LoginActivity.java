package br.iesb.alunoonline;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtEmail;
    private EditText txtSenha;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //LOGIN POR E-MAIL.
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

        //LOGIN PELO GOOGLE.
        ImageView btGoogle =  findViewById(R.id.btnLoginGoogle);
        btGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });


        //ABRE A TELA DE CADASTRO
        TextView Cadastrar = findViewById(R.id.LinkCadastro);
        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(LoginActivity.this, FormularioActivity.class);
                startActivity(t);
            }
        });
        //ABRE A TELA DE AUTERAR SENHA.
        TextView Recuperar = findViewById(R.id.LinkRecSenha);
        Recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
                startActivity(t);
            }
        });

    }
    //AUTENTICAÇÃO GOOGLE.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

            }
        }
    }
    //AUTENTICAÇÃO GOOGLE.
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent t = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(t);

                            //PEGA O E-MAIL USADO PARA LOGAR NO APP
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(LoginActivity.this,"Bem vindo ao Google.",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this,"Erro de login.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    //FUNÇÃO LOGIN
    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //FUNÇÃO LOGIN.
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
