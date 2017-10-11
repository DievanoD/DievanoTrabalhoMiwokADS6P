package com.example.android.miwok;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /**
    * @param savedInstanceState
    * Seta o activity_main no conteúdo da activity.
    * O viewPager habilitará o deslizar entre os fragments.
    * Depois temos um adapter para sabermos qual fragment é mostrado na tela.
    * Setamos o adapter no viewPager.
    * TabLayout mostra as abas para a navegação entre fragments.
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // Conecte o layout da aba com o view pager. Isto irá
        // 1. Atualizar o layout da aba quando o view pager for deslizado
        // 2. Atualizar o view pager quando uma aba for selecionada
        // 3. Definir os nomes da aba do layout da aba com os títulos do ad
        // chamando onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
    }
}
