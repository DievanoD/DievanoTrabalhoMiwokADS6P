package com.example.android.miwok;

/**
 * Created by Diêvano on 25/09/2017.
 */

public class Palavra {
    private String mTraducaoPadrao;
    private String mTraducaoMiwok;
    private int mReferenciaImagem = SEM_IMAGEM_FORNECIDA;
    private int mReferenciaAudio;
    private static final int SEM_IMAGEM_FORNECIDA = -1;

    /**
     * Construtor para as Activity's contendo: Palavra padrão, Palavra Miwok, Áudio
     * @param traducaoPadrao
     * @param traducaoMiwok
     * @param referenciaAudio
     */
    public Palavra(String traducaoPadrao, String traducaoMiwok, int referenciaAudio){
        mTraducaoPadrao = traducaoPadrao;
        mTraducaoMiwok = traducaoMiwok;
        mReferenciaAudio = referenciaAudio;
    }

    /**
     * Construtor para as Activity's contendo: Palavra padrão, Palavra Miwok, Imagem, Áudio
     * @param traducaoPadrao
     * @param traducaoMiwok
     * @param referenciaImagem
     * @param referenciaAudio
     */
    public Palavra(String traducaoPadrao, String traducaoMiwok, int referenciaImagem, int referenciaAudio){
        this.mTraducaoPadrao = traducaoPadrao;
        this.mTraducaoMiwok = traducaoMiwok;
        this.mReferenciaImagem = referenciaImagem;
        this.mReferenciaAudio = referenciaAudio;
    }

    /**
     * Método que verifica se possui imagem
     * @return mReferenciaImagem
     */
    public boolean hasImagem(){
        return mReferenciaImagem != SEM_IMAGEM_FORNECIDA;
    }

    /**
     *
     * @return mTraducaoPadrao
     */
    public String getTraducaoPadrao() {
        return mTraducaoPadrao;
    }

    /**
     *
     * @return mTraducaoMiwok
     */
    public String getTraducaoMiwok() {
        return mTraducaoMiwok;
    }

    /**
     *
     * @return mReferenciaImagem
     */
    public int getReferenciaImagem() {
        return mReferenciaImagem;
    }

    /**
     *
     * @return mReferenciaAudio
     */
    public int getReferenciaAudio() {
        return mReferenciaAudio;
    }
}
