package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrasesFragment extends Fragment {
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    /*
    * Chamada Assíncrona(Async Callback), para caso precisarmos clicar em outro áudio, não ser preciso o
    * anterior terminar para o próximo iniciar.
    * */
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    /*
    *  Callback para esperar o estado do Audio Focus.
    * */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //Queremos passar o audio
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //Queremos reproduzir o audio
                mMediaPlayer.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //Queremos parar o audio
                releaseMediaPlayer();
            }
        }
    };


    public FrasesFragment() {
        // Required empty public constructor
    }

    /**
     * Inflamos(Criamos) a view com o layout de lista_palavras.xml
     * Definimos um arraylist do tipo palavra e inserimos: tradução, palavra em Miwok, imagem e o áudio.
     * Quando o PalavraAdapter é criado ele passa o contexto, o ArrayList de palavras e a cor de fundo.
     * No setOnItemClickListener é criado um mediaPlayer e informado qual áudio será tocado, preparando o áudio.
     * Recuperamos a palavra clicada com .get(position) e passamos a posição do item.
     * A variável resultado recebe se obtivemos sucesso no pedido do Audio Focus. Caso sim iremos reproduzir o áudio.
     * E por fim retornamos a view.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return rootView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lista_palavras, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Palavra> palavras = new ArrayList<>();
        palavras.add(new Palavra("onde você está indo?", "minto wuksus", R.raw.phrase_where_are_you_going));
        palavras.add(new Palavra("qual o seu nome?", "tinne oyaase'ne", R.raw.phrase_what_is_your_name));
        palavras.add(new Palavra("meu nome é...", "oyaaset...", R.raw.phrase_my_name_is));
        palavras.add(new Palavra("como está se sentindo?", "michekses?", R.raw.phrase_how_are_you_feeling));
        palavras.add(new Palavra("estou me sentindo bem", "kuchi achit", R.raw.phrase_im_feeling_good));
        palavras.add(new Palavra("você está vindo?", "eenes'aa?", R.raw.phrase_are_you_coming));
        palavras.add(new Palavra("sim, estou indo", "hee' eenem", R.raw.phrase_yes_im_coming));
        palavras.add(new Palavra("estou indo", "eenem", R.raw.phrase_im_coming));
        palavras.add(new Palavra("vamos", "yoowutis", R.raw.phrase_lets_go));
        palavras.add(new Palavra("venha aqui", "enni'nem", R.raw.phrase_come_here));

        PalavraAdapter itensAdapter = new PalavraAdapter(getActivity(), palavras, R.color.categoria_frases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itensAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Palavra palavraClicada = palavras.get(position);

                releaseMediaPlayer();

                int resultado = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (resultado == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(), palavraClicada.getReferenciaAudio());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });

        return rootView;
    }

    /*
    * Interrompe o áudio caso o usuário saia da aplicação
    * */
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /*
    * Se o mediaplayer for diferente de null, limpa a mémoria e libera o objeto.
    * E por fim libera também o Audio Focus.
    * */
    private void releaseMediaPlayer(){
        if (mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
