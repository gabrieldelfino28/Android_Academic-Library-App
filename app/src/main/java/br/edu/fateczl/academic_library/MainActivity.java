package br.edu.fateczl.academic_library;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle b = getIntent().getExtras();
        if (b != null) {
            loadFragment(b);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void loadFragment(Bundle b) {
        String typeFrag = b.getString("tipo");
        assert typeFrag != null;
        switch (typeFrag) {
            case "Home":
                fragment = new HomeFragment();
                break;
            case "Aluno":
                fragment = new AlunoFragment();
                break;
            case "Livro":
                fragment = new LivroFragment();
                break;
            case "Revista":
                fragment = new RevistaFragment();
                break;
            case "Aluguel":
                fragment = new AluguelFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        Bundle bundle = new Bundle();
        Intent i = new Intent(this, MainActivity.class);

        if (itemID == R.id.item_home) {
            bundle.putString("tipo", "Home");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_aluno) {
            bundle.putString("tipo", "Aluno");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_livro) {
            bundle.putString("tipo", "Livro");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_revista) {
            bundle.putString("tipo", "Revista");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_aluguel) {
            bundle.putString("tipo", "Aluguel");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}