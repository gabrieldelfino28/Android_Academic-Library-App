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

import br.edu.fateczl.academic_library.control.AlunoController;
import br.edu.fateczl.academic_library.control.IController;
import br.edu.fateczl.academic_library.control.factory.IAlunoFactory;
import br.edu.fateczl.academic_library.model.Aluno;


public class AlunoFragment extends Fragment implements IFragOperations<Aluno> {

    private View view;

    private EditText inCodigoAluno, inNomeAluno, inEmailAluno;
    private Button btnInsert, btnUpdate, btnDelete, btnList, btnGet;
    private TextView tvListarAluno;
    private IController<Aluno> alunoC;
    private IAlunoFactory factory;

    public AlunoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluno, container, false);
        alunoC = new AlunoController();
        factory = new AlunoController();

        inCodigoAluno = view.findViewById(R.id.inCodAluno);
        inNomeAluno = view.findViewById(R.id.inNomeAluno);
        inEmailAluno = view.findViewById(R.id.inEmailAluno);

        tvListarAluno = view.findViewById(R.id.tvListarAluno);
        tvListarAluno.setMovementMethod(new ScrollingMovementMethod());

        btnInsert = view.findViewById(R.id.btnInsertAluno);
        btnUpdate = view.findViewById(R.id.btnUpdateAluno);
        btnDelete = view.findViewById(R.id.btnDeleteAluno);
        btnList = view.findViewById(R.id.btnListAluno);
        btnGet = view.findViewById(R.id.btnBuscarAluno);


        return view;
    }

    public void operacaoInserir() {
        try {
            checkFields();
            Aluno aluno = mountObject();
            alunoC.inserir(aluno);
            Toast.makeText(view.getContext(), getString(R.string.insertM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    public void operacaoAtualizar() {
        try {
            checkFields();
            Aluno aluno = mountObject();
            alunoC.atualizar(aluno);
            Toast.makeText(view.getContext(), getString(R.string.updateM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    public void operacaoDeletar() {
        try {
            checkFields();
            Aluno aluno = mountObject();
            alunoC.remover(aluno);
            Toast.makeText(view.getContext(), getString(R.string.deleteM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    public void operacaoBuscar() {
        try {
            if (inCodigoAluno.getText().toString().isEmpty()) throw new Exception("Código Vazio!");
            Aluno aluno = mountObject();
            aluno = alunoC.buscar(aluno);
            if (isFound()) {
                fillFields(aluno);
            } else {
                cleanFields();
                throw new Exception(String.format(getString(R.string.findErr), "Aluno"));
            }
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void operacaoListar() {
        try {
            List<Aluno> alunos = alunoC.listar();
            StringBuilder buffer = new StringBuilder();
            for (Aluno al : alunos) {
                buffer.append(al.toString()).append("\n");
            }
            tvListarAluno.setText(buffer.toString());
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void fillFields(Aluno aluno) {
        inCodigoAluno.setText(String.valueOf(aluno.getRA()));
        inNomeAluno.setText(aluno.getNome());
        inEmailAluno.setText(aluno.getEmail());
    }

    @Override
    public void cleanFields() {
        inCodigoAluno.setText("");
        inNomeAluno.setText("");
        inEmailAluno.setText("");
    }

    @Override
    public void checkFields() throws Exception {
        if (inCodigoAluno.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
        if (inNomeAluno.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
        if (inEmailAluno.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
    }

    @Override
    public Aluno mountObject() {
        int ra = Integer.parseInt(inCodigoAluno.getText().toString());
        String nome = inNomeAluno.getText().toString();
        String email = inEmailAluno.getText().toString();
        return factory.AlunoFactory(ra, nome, email);
    }

    @Override
    public boolean isFound() {
        return !(inNomeAluno.getText().toString().isEmpty() && inEmailAluno.getText().toString().isEmpty());
    }
}