package br.com.alura.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);
    }

    /*
    Método responsável por chamar o menu criado pelo programador na Activity.
    Na implementação do método, utilize o MenuInflater para transformar o XML de menu que criamos anteriormente
    no menu da Activity:
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Há duas formas de implementação
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        //A anterior ou essa
        //getMenuInflater().inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*
    Método responsável por dar a ação de um item de menu da Acitivy.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAluno();

                AlunoDAO dao = new AlunoDAO(this);
                dao.insere(aluno);
                dao.close();

                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
