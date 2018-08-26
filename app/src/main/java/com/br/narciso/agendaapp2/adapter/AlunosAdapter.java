package com.br.narciso.agendaapp2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.narciso.agendaapp2.ListaAlunosActivity;
import com.br.narciso.agendaapp2.R;
import com.br.narciso.agendaapp2.modelo.Aluno;

import java.util.List;

public class AlunosAdapter extends BaseAdapter {
    
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return this.alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Aluno aluno = alunos.get(position);

        //Inflando o list_item.xml para trazer o layout com imagem, nome e telefone do aluno
        LayoutInflater inflater = LayoutInflater.from(context);

        //Foi feito isso pensando na questão de performance, caso tenha 1 milhão de itens na lista, o android irá inflando
        //os itens no decorrer em que o usuário for rolando a tela e não irá inflar tudo de uma vez.
        // Aqui reaproveitamos a convertView caso ela exista.
        View view = convertView;
        if(convertView == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView campoNome = view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();

        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
