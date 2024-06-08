package br.edu.fateczl.academic_library;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.fateczl.academic_library.control.IController;
import br.edu.fateczl.academic_library.control.RevistaController;
import br.edu.fateczl.academic_library.control.factory.IRevistaFactory;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Revista;

public class RevistaFragment extends Fragment implements IFragOperations<Revista>{

    private View view;
    private EditText inCodigoRevista, inNomeRevista, inQtdPagRevista, inISSNRevista;
    private Button btnInsert, btnUpdate, btnDelete, btnList, btnGet;
    private TextView tvListarRevista;
    private IController<Revista> revistaCont;
    private IRevistaFactory factory;

    public RevistaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_revista, container, false);
        revistaCont =  new RevistaController();
        factory = new RevistaController();

        inCodigoRevista = view.findViewById(R.id.inCodRevista);
        inNomeRevista = view.findViewById(R.id.inNomeRevista);
        inQtdPagRevista = view.findViewById(R.id.inQtdPagRevista);
        inISSNRevista = view.findViewById(R.id.inISSNRevista);

        tvListarRevista = view.findViewById(R.id.tvListarRevista);
        tvListarRevista.setMovementMethod(new ScrollingMovementMethod());

        btnInsert = view.findViewById(R.id.btnInsertRevista);
        btnUpdate = view.findViewById(R.id.btnUpdateRevista);
        btnDelete = view.findViewById(R.id.btnDeleteRevista);
        btnList = view.findViewById(R.id.btnListRevista);
        btnGet = view.findViewById(R.id.btnBuscarRevista);

        return view;
    }

    @Override
    public void operacaoInserir() {
        try {
            checkFields();
            Revista revista = mountObject();
            revistaCont.inserir(revista);
            Toast.makeText(view.getContext(), getString(R.string.insertM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoAtualizar() {
        try {
            checkFields();
            Revista revista = mountObject();
            revistaCont.inserir(revista);
            Toast.makeText(view.getContext(), getString(R.string.updateM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoDeletar() {
        try {
            checkFields();
            Revista revista = mountObject();
            revistaCont.inserir(revista);
            Toast.makeText(view.getContext(), getString(R.string.deleteM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoBuscar() {
        try {
            if (inCodigoRevista.getText().toString().isEmpty()) throw new Exception("Codigo Vazio!");
            Revista revista = mountObject();
            revista = revistaCont.buscar(revista);
            if (isFound()) {
                fillFields(revista);
            } else {
                cleanFields();
                throw new Exception(String.format(getString(R.string.findErr), "Revista"));
            }
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void operacaoListar() {
        try {
            List<Revista> revistas = revistaCont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Revista revista : revistas) {
                buffer.append(revista.toString()).append("\n");
            }
            tvListarRevista.setText(buffer.toString());
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void fillFields(Revista revista) {
        inCodigoRevista.setText(String.valueOf(revista.getCodigo()));
        inNomeRevista.setText(revista.getNome());
        inQtdPagRevista.setText(String.valueOf(revista.getQtdPaginas()));
        inISSNRevista.setText(revista.getISSN());
    }

    @Override
    public void cleanFields() {
        inCodigoRevista.setText("");
        inNomeRevista.setText("");
        inQtdPagRevista.setText("");
        inISSNRevista.setText("");
    }

    @Override
    public void checkFields() throws Exception {
        if (inCodigoRevista.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inNomeRevista.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inQtdPagRevista.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inISSNRevista.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
    }

    @Override
    public Revista mountObject() {
        int codRevista = Integer.parseInt(inCodigoRevista.getText().toString());
        String nome = inNomeRevista.getText().toString();
        int qtdPag = Integer.parseInt(inQtdPagRevista.getText().toString());
        String ISSN = inISSNRevista.getText().toString();

        Exemplar exemplar = factory.ExemplarFactory(codRevista, nome, qtdPag);
        return factory.RevistaFactory(exemplar, ISSN);
    }

    @Override
    public boolean isFound() {
        if (inNomeRevista.getText().toString().isEmpty()
                && inQtdPagRevista.getText().toString().isEmpty()
                && inISSNRevista.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }
}