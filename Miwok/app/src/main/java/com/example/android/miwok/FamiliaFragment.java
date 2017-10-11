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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamiliaFragment extends Fragment {
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

    public FamiliaFragment() {
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
        palavras.add(new Palavra("pai", "apa", R.drawable.family_father, R.raw.family_father));
        palavras.add(new Palavra("mãe", "eta", R.drawable.family_mother, R.raw.family_mother));
        palavras.add(new Palavra("filho", "angsi", R.drawable.family_son, R.raw.family_son));
        palavras.add(new Palavra("filha", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        palavras.add(new Palavra("irmão mais velho", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        palavras.add(new Palavra("irmão mais novo", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        palavras.add(new Palavra("irmã mais velha", "tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        palavras.add(new Palavra("irmã mais nova", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        palavras.add(new Palavra("avó", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        palavras.add(new Palavra("avô", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        PalavraAdapter itensAdapter = new PalavraAdapter(getActivity(), palavras, R.color.categoria_familia);

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
