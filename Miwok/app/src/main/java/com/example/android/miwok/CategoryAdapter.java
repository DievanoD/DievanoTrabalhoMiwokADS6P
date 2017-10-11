package com.example.android.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Diêvano on 06/10/2017.
 */
public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;

    /**
     * Construtor que manterá cada estado do adapter a cada deslizada.
     *
     * @param context
     * @param fm
     */
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Retorna o Fragment para sua devida posição
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new NumbersFragment();
        }else if (position == 1){
            return new FamiliaFragment();
        }else if (position == 2){
            return new CoresFragment();
        }else {
            return new FrasesFragment();
        }
    }

    /**
     * Faz o retorno do número total de páginas.
     * @return 4
     */
    @Override
    public int getCount() {
        return 4;
    }


    /**
     * Retornamos o título da categoria apropriada por página.
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.numerosTextView);
        }else if (position == 1){
            return mContext.getString(R.string.familiaTextView);
        }else if (position == 2){
            return mContext.getString(R.string.coresTextView);
        }else {
            return mContext.getString(R.string.frasesTextView);
        }
    }
}
