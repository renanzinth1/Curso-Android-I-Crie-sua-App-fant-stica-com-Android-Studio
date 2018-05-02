package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Button novoAluno = findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(vaiProFormulario);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.buscarAlunos();
        alunoDAO.close();

        // Converter os alunos do tipo Aluno para View, no caso, passando como parâmetro:
        // O contexto (própria Activity), um layout padrão e a fonte de dados que queremos exibir.
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);

        // Devolvendo uma instância da 'View' gerada no setContetView,
        // no caso está retornando a ListView cujo ID é lista_alunos.
        ListView listaAlunoView = findViewById(R.id.lista_alunos);

        listaAlunoView.setAdapter(adapter);
    }
}
