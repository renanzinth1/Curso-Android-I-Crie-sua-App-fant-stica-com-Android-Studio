package com.br.narciso.agendaapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.br.narciso.agendaapp2.DAO.AlunoDAO;
import com.br.narciso.agendaapp2.modelo.Aluno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView alunosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        carregaLista();

        alunosView = findViewById(R.id.lista_alunos);


        //Comando serve para recuperar o objeto de um item com um clique simples na lista
        alunosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long id) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(position);
                Intent goToForm = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                goToForm.putExtra("aluno", aluno);
                startActivity(goToForm);
            }
        });

        Button novoAluno = findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToForm = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(goToForm);
            }
        });

        //Registrar o contexto de menu, passando a lista
        registerForContextMenu(alunosView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    // Método usado para quando pressionar algum item da lista, exibir algumas opções.
    // Mas para esse método funcionar, é necessário chamar o método registerForContextMenu(view) dentro de onCreate,
    // passando a view desejada.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem remover = menu.add("Remover");

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) alunosView.getItemAtPosition(info.position);

        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.remover(aluno);

                carregaLista();
                return false;
            }
        });
    }

    //método usado para recuperar lista de alunos direto do banco
    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();

        // Devolvendo uma instância da 'View' gerada no setContetView,
        // no caso está retornando a ListView cujo ID é lista_alunos.
        ListView alunosView = findViewById(R.id.lista_alunos);

        // Converter os alunos do tipo Aluno para View, no caso, passando como parâmetro:
        // O contexto (própria Activity), um layout padrão e a fonte de dados que queremos exibir.
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);

        alunosView.setAdapter(adapter);
    }
}