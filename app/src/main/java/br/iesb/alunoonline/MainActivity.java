package br.iesb.alunoonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* COMO ATRIBUR VALOR NO TEXTVIEW.
        TextView txt = findViewById(R.id.txtName);
        txt.setText("Nome: Darlan");
        */

        //CARREGAR OS DADOS DO PERFIL NA TELA.
        mDatabase = FirebaseDatabase.getInstance().getReference("/iesb/alunos");
        ValueEventListener alunoListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Aluno aluno = dataSnapshot.getValue(Aluno.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(alunoListener);






        //BOTÃO EDITAR PERFIL.
        Button bt = (Button) findViewById(R.id.btnEditarPerfil);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(MainActivity.this, PerfilActivity.class);
                startActivity(t);
            }
        });

        //BOTÃO LOCALIZAÇÃO
        Button btnLocal = findViewById(R.id.btnLocal);
        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(t);
            }
        });

    }
}
