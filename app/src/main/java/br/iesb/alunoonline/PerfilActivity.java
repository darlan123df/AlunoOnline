package br.iesb.alunoonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class PerfilActivity extends AppCompatActivity {

    private EditText txtName1;
    private EditText txtMatricula1;
    private EditText txtCurso1;
    private EditText txtCampus1;
    private EditText txtInteresses1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtName1 = findViewById(R.id.txtName);
        txtMatricula1 = findViewById(R.id.txtMatricula);
        txtCurso1 = findViewById(R.id.txtCurso);
        txtCampus1 = findViewById(R.id.txtCampus);
        txtInteresses1 = findViewById(R.id.txtInteresses);

        mAuth = FirebaseAuth.getInstance();

        Button bt = (Button) findViewById(R.id.btnGravar);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravar();
                Toast.makeText(PerfilActivity.this,"Gavado com Sucesso!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        Button btS = (Button) findViewById(R.id.btnSair);
        btS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void gravar(){
        FirebaseUser user = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Aluno aluno = new Aluno();

        aluno.email = user.getEmail();
        aluno.nome = txtName1.getText().toString();
        aluno.matricula = Integer.parseInt(txtMatricula1.getText().toString());
        aluno.curso = txtCurso1.getText().toString();
        aluno.campus = txtCampus1.getText().toString();
        String ints = txtInteresses1.getText().toString();

        for(String s : ints.split(",")) {
            Interesses it = new Interesses();
            it.id = UUID.randomUUID().toString();
            it.tag = s;
            aluno.interesses.add(it);

        }
        DatabaseReference novoAluno2 = database.getReference("iesb/alunos" + UUID.randomUUID().toString());
        novoAluno2.setValue(aluno);

    }
}
