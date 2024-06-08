package br.edu.fateczl.academic_library;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.fateczl.academic_library.control.AluguelController;
import br.edu.fateczl.academic_library.control.AlunoController;
import br.edu.fateczl.academic_library.control.CollectionController;
import br.edu.fateczl.academic_library.control.ICollection;
import br.edu.fateczl.academic_library.control.IController;
import br.edu.fateczl.academic_library.control.factory.IAluguelFactory;
import br.edu.fateczl.academic_library.model.Aluguel;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;
import br.edu.fateczl.academic_library.model.Livro;
import br.edu.fateczl.academic_library.persistence.AluguelDAO;
import br.edu.fateczl.academic_library.persistence.AlunoDAO;

public class AluguelFragment extends Fragment implements IFragOperations<Aluguel> {

    private View view;

    private EditText inDataRet, inDataDev;
    private Button btnInsert, btnUpdate, btnDelete, btnList, btnGet;
    private Spinner spExemplar, spAluno;
    private TextView tvListarAluguel;
    private ICollection collection;
    private IController<Aluguel> aluguelCont;
    private IAluguelFactory factory;

    private List<Exemplar> exemplars;

    private List<Aluno> alunos;

    public AluguelFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluguel, container, false);
        aluguelCont = new AluguelController(new AluguelDAO(view.getContext()));
        collection = new CollectionController();
        factory = new AluguelController(new AluguelDAO(view.getContext()));

        inDataRet = view.findViewById(R.id.inDataRetAluguel);
        inDataDev = view.findViewById(R.id.inDataDevAluguel);
        spExemplar = view.findViewById(R.id.spExemplar);
        spAluno = view.findViewById(R.id.spAluno);
        fillSpinner();

        tvListarAluguel = view.findViewById(R.id.tvListarAluguel);
        tvListarAluguel.setMovementMethod(new ScrollingMovementMethod());

        btnInsert = view.findViewById(R.id.btnInsertAluguel);
        btnUpdate = view.findViewById(R.id.btnUpdateAluguel);
        btnDelete = view.findViewById(R.id.btnDeleteAluguel);
        btnList = view.findViewById(R.id.btnListAluguel);
        btnGet = view.findViewById(R.id.btnBuscarAluguel);

        btnInsert.setOnClickListener(op -> operacaoInserir());
        btnUpdate.setOnClickListener(op -> operacaoAtualizar());
        btnDelete.setOnClickListener(op -> operacaoDeletar());
        btnList.setOnClickListener(op -> operacaoListar());
        btnGet.setOnClickListener(op -> operacaoBuscar());

        return view;
    }

    @Override
    public void operacaoInserir() {
        int pos1 = spExemplar.getSelectedItemPosition();
        int pos2 = spAluno.getSelectedItemPosition();
        if (pos1 > 0 && pos2 > 0) {
            try {
                checkFields();
                Aluguel aluguel = mountObject();
                aluguelCont.inserir(aluguel);
                Toast.makeText(view.getContext(), getString(R.string.insertM), Toast.LENGTH_LONG).show();
            } catch (Exception err) {
                Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um Exemplar e um Aluno!", Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoAtualizar() {
        int pos1 = spExemplar.getSelectedItemPosition();
        int pos2 = spAluno.getSelectedItemPosition();
        if (pos1 > 0 && pos2 > 0) {
            try {
                checkFields();
                Aluguel aluguel = mountObject();
                aluguelCont.atualizar(aluguel);
                Toast.makeText(view.getContext(), getString(R.string.updateM), Toast.LENGTH_LONG).show();
            } catch (Exception err) {
                Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um Exemplar e um Aluno!", Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoDeletar() {
        int pos1 = spExemplar.getSelectedItemPosition();
        int pos2 = spAluno.getSelectedItemPosition();
        if (pos1 > 0 && pos2 > 0) {
            try {
                checkFields();
                Aluguel aluguel = mountObject();
                aluguelCont.remover(aluguel);
                Toast.makeText(view.getContext(), getString(R.string.deleteM), Toast.LENGTH_LONG).show();
            } catch (Exception err) {
                Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(view.getContext(), "Selecione um Exemplar e um Aluno!", Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoBuscar() {
        try {
            checkForQuery();
            alunos = collection.findAlunos(view.getContext());
            exemplars = collection.findExemplares(view.getContext());
            Aluguel aluguel = mountObject();
            Aluguel al = aluguelCont.buscar(aluguel);
            fillFields(al);
            if (notFound()) {
                cleanFields();
                throw new Exception(String.format(getString(R.string.findErr), "Aluguel"));
            }
        } catch (Exception err) {
            Log.e("err:SQL", err.getMessage());
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void operacaoListar() {
        try {
            List<Aluguel> alugueis = aluguelCont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Aluguel al : alugueis) {
                buffer.append(al.toString()).append("\n");
            }
            tvListarAluguel.setText(buffer.toString());
        } catch (Exception err) {
            Log.e("err:SQL", err.getMessage());
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    private void fillSpinner() {
        try {
            fillSpinnerExemplar();
            fillSpinnerAluno();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fillSpinnerExemplar() throws Exception {
        exemplars = collection.findExemplares(view.getContext());
        Exemplar ex = new Livro();
        ex.setCodigo(0);
        ex.setNome("Selecione um Exemplar");
        ex.setQtdPaginas(0);
        exemplars.add(0, ex);
        ArrayAdapter ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, exemplars);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spExemplar.setAdapter(ad);
    }

    private void fillSpinnerAluno() throws Exception {
        alunos = collection.findAlunos(view.getContext());
        Aluno al = new Aluno();
        al.setRA(0);
        al.setNome("Selecione um Aluno");
        al.setEmail("");
        alunos.add(0, al);
        ArrayAdapter ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, alunos);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAluno.setAdapter(ad);
    }

    @Override
    public void fillFields(Aluguel aluguel) {
        inDataRet.setText(aluguel.getDataRetirada());
        inDataDev.setText(aluguel.getDataDevolucao());
        int pos = 1;
        for (Aluno l : alunos) {
            if (l.getRA() == aluguel.getAluno().getRA()) spAluno.setSelection(pos);
            else pos += 1;
        }
        int p = 1;
        for (Exemplar ex : exemplars) {
            if (ex.getCodigo() == aluguel.getExemplar().getCodigo()) spExemplar.setSelection(p);
            else p += 1;
        }
        if (pos > alunos.size()) spAluno.setSelection(0);
        if (p > exemplars.size()) spExemplar.setSelection(0);
    }

    @Override
    public void cleanFields() {
        inDataRet.setText("");
        inDataDev.setText("");
        spAluno.setSelection(0);
        spExemplar.setSelection(0);
    }

    @Override
    public void checkFields() throws Exception {
        if (inDataRet.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inDataDev.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
    }

    private void checkForQuery() throws Exception {
        if (inDataRet.getText().toString().isEmpty()) throw new Exception("Data Retirada Vazia!");
        /*
        if (spExemplar.getSelectedItemPosition() == 0) throw new Exception("Escolha um Exemplar!");
        if (spAluno.getSelectedItemPosition() == 0) throw new Exception("Escolha um Aluno!");
         */
    }

    @Override
    public Aluguel mountObject() {
        String dataRet = inDataRet.getText().toString();
        String dataDev = inDataDev.getText().toString();
        Aluno aluno = (Aluno) spAluno.getSelectedItem();
        Exemplar ex = (Exemplar) spExemplar.getSelectedItem();
        return factory.AluguelFactory(aluno, ex, dataRet, dataDev);
    }

    @Override
    public boolean notFound() {
        return inDataDev.getText().toString().isEmpty();
    }
}