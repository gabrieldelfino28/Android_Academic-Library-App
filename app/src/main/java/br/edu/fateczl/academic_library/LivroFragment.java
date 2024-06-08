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
import br.edu.fateczl.academic_library.control.LivroController;
import br.edu.fateczl.academic_library.control.factory.ILivroFactory;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.persistence.LivroDAO;

public class LivroFragment extends Fragment implements IFragOperations<Livro> {
    private View view;

    private EditText inCodigoLivro, inNomeLivro, inQtdPagLivro, inISBNLivro, inEdicaoLivro;
    private Button btnInsert, btnUpdate, btnDelete, btnList, btnGet;
    private TextView tvListarLivro;
    private IController<Livro> livroCont;
    private ILivroFactory factory;

    public LivroFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_livro, container, false);
        livroCont = new LivroController(new LivroDAO(view.getContext()));
        factory = new LivroController(new LivroDAO(view.getContext()));

        inCodigoLivro = view.findViewById(R.id.inCodLivro);
        inNomeLivro = view.findViewById(R.id.inNomeLivro);
        inQtdPagLivro = view.findViewById(R.id.inQtdPagLivro);
        inISBNLivro = view.findViewById(R.id.inISBNLivro);
        inEdicaoLivro = view.findViewById(R.id.inEdicaoLivro);

        tvListarLivro = view.findViewById(R.id.tvListarLivro);
        tvListarLivro.setMovementMethod(new ScrollingMovementMethod());

        btnInsert = view.findViewById(R.id.btnInsertLivro);
        btnUpdate = view.findViewById(R.id.btnUpdateLivro);
        btnDelete = view.findViewById(R.id.btnDeleteLivro);
        btnList = view.findViewById(R.id.btnListLivro);
        btnGet = view.findViewById(R.id.btnBuscarLivro);

        btnInsert.setOnClickListener(op -> operacaoInserir());
        btnUpdate.setOnClickListener(op -> operacaoAtualizar());
        btnDelete.setOnClickListener(op -> operacaoDeletar());
        btnList.setOnClickListener(op -> operacaoListar());
        btnGet.setOnClickListener(op -> operacaoBuscar());

        return view;
    }

    @Override
    public void operacaoInserir() {
        try {
            checkFields();
            Livro livro = mountObject();
            livroCont.inserir(livro);
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
            Livro livro = mountObject();
            livroCont.atualizar(livro);
            Toast.makeText(view.getContext(), getString(R.string.updateM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoDeletar() {
        try {
            if (inCodigoLivro.getText().toString().isEmpty()) throw new Exception("Codigo Vazio!");
            Livro livro = mountObject();
            livroCont.remover(livro);
            Toast.makeText(view.getContext(), getString(R.string.deleteM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoBuscar() {
        try {
            if (inCodigoLivro.getText().toString().isEmpty()) throw new Exception("Codigo Vazio!");
            Livro livro = mountObject();
            livro = livroCont.buscar(livro);
            fillFields(livro);
            if (notFound()) {
                cleanFields();
                throw new Exception(String.format(getString(R.string.findErr), "Livro"));
            }
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void operacaoListar() {
        try {
            List<Livro> livros = livroCont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Livro l : livros) {
                buffer.append(l.toString()).append("\n");
            }
            tvListarLivro.setText(buffer.toString());
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void fillFields(Livro livro) {
        inCodigoLivro.setText(String.valueOf(livro.getCodigo()));
        inNomeLivro.setText(livro.getNome());
        inQtdPagLivro.setText(String.valueOf(livro.getQtdPaginas()));
        inISBNLivro.setText(livro.getISBN());
        inEdicaoLivro.setText(String.valueOf(livro.getEdicao()));
    }

    @Override
    public void cleanFields() {
        inCodigoLivro.setText("");
        inNomeLivro.setText("");
        inQtdPagLivro.setText("");
        inISBNLivro.setText("");
        inEdicaoLivro.setText("");
    }

    @Override
    public void checkFields() throws Exception {
        if (inCodigoLivro.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inNomeLivro.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inQtdPagLivro.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inISBNLivro.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inEdicaoLivro.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
    }

    @Override
    public Livro mountObject() {
        int codLivro = Integer.parseInt(inCodigoLivro.getText().toString());
        String nome = inNomeLivro.getText().toString();
        String ISBN = inISBNLivro.getText().toString();
        int qtdPag;
        int edicao;
        if (!inQtdPagLivro.getText().toString().isEmpty())
             qtdPag = Integer.parseInt(inQtdPagLivro.getText().toString());
        else qtdPag = 0;
        if (!inEdicaoLivro.getText().toString().isEmpty())
             edicao = Integer.parseInt(inEdicaoLivro.getText().toString());
        else edicao = 0;
        Exemplar exemplar = factory.ExemplarFactory(codLivro, nome, qtdPag);
        return factory.LivroFactory(exemplar, ISBN, edicao);
    }

    @Override
    public boolean notFound() {
        return  inNomeLivro.getText().toString().isEmpty() &&
                inQtdPagLivro.getText().toString().isEmpty() &&
                inISBNLivro.getText().toString().isEmpty() &&
                inEdicaoLivro.getText().toString().isEmpty();
    }
}