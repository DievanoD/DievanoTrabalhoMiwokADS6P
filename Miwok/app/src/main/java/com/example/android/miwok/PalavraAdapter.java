package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by Diêvano on 25/09/2017.
 */

public class PalavraAdapter extends ArrayAdapter<Palavra> {
    private int mCorFundo;

    /**
     * Construtor definindo a Activity, a lista de palavras e a cor de fundo.
     * @param context
     * @param palavras
     * @param corFundo
     */
    public PalavraAdapter(Activity context, ArrayList<Palavra> palavras, int corFundo) {
        super(context, 0, palavras);
        mCorFundo = corFundo;
    }

    /**
     * É feita um verificação para ver se é um view reciclada ou inflada(criada).
     * O item que está sendo construído é resgatado e as informações são setadas nos TextViews.
     * Resgatamos o componente de cada item da lista e estilizamos com a cor correta.
     * Verificamos se a palavra tem ou não imagem, se sim adicionamos a imagem com o setImageResource
     * passando o ID como parâmetro. Senão sumimos com o componente da tela.
     * No final é retornado o componente criado.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return itemListaView
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View itemListaView = convertView;

        if (itemListaView == null){
            itemListaView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, parent, false);
        }

        Palavra palavraAtual = getItem(position);

        LinearLayout layout = (LinearLayout) itemListaView.findViewById(R.id.container_global);
        layout.setBackgroundResource(mCorFundo);

        TextView miwok = (TextView) itemListaView.findViewById(R.id.miwok);
        miwok.setText(palavraAtual.getTraducaoMiwok());

        TextView padrao = (TextView) itemListaView.findViewById(R.id.padrao);
        padrao.setText(palavraAtual.getTraducaoPadrao());

        ImageView imagem = (ImageView) itemListaView.findViewById(R.id.container_imagem);
        if (palavraAtual.hasImagem()){
            imagem.setImageResource(palavraAtual.getReferenciaImagem());
            imagem.setVisibility(View.VISIBLE);
        }else{
            imagem.setVisibility(View.GONE);
        }

        return itemListaView;
    }


}
