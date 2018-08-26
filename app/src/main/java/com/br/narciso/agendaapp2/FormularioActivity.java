package com.br.narciso.agendaapp2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaExtractor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.br.narciso.agendaapp2.DAO.AlunoDAO;
import com.br.narciso.agendaapp2.helper.FormularioHelper;
import com.br.narciso.agendaapp2.modelo.Aluno;

import java.io.File;
import java.io.IOException;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        //Recebendo o aluno que foi passado pela intent por outra activity
        Intent intent = (Intent) getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null)
            helper.preencheFormulario(aluno);

        Button botaoFoto = findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Action inplícita para chamar o recurso para tirar foto do celular
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String nomeFoto = "foto-" + System.currentTimeMillis();
                caminhoFoto = getExternalFilesDir(null) + "/" + nomeFoto + ".jpg";
                File arquivoFoto = new File(caminhoFoto);

                //Falta falar sobre o MediaStore.EXTRA_OUTPUT
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intent, CODIGO_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                    helper.carregaImagem(caminhoFoto);
            }
        }
    }

    // Método responsável por chamar o menu criado pelo programador na Activity.
    //Na implementação do método, utilize o MenuInflater para transformar o XML de menu que criamos anteriormente,
    // no menu da Activity:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Há duas formas de implementação

        /* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu); */

        // ou essa
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Método responsável por dar a ação de um item de menu da Acitivy
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                Aluno aluno = helper.pegaAluno();
                AlunoDAO dao = new AlunoDAO(this);

                // Testando se o aluno ja existe ou não...
                // Se o id for diferente de nulo, então existe o aluno, então apenas modifica
                // Se não, insere no banco
                if (aluno.getId() != null) {
                    dao.altera(aluno);
                    Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " alterado com sucesso!", Toast.LENGTH_SHORT).show();

                } else {
                    dao.insere(aluno);
                    Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                }

                dao.close();

                // Finalizando a intent
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}