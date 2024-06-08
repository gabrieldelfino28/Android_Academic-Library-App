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

import br.edu.fateczl.academic_library.control.AluguelController;
import br.edu.fateczl.academic_library.control.CollectionController;
import br.edu.fateczl.academic_library.control.ICollection;
import br.edu.fateczl.academic_library.control.IController;
import br.edu.fateczl.academic_library.control.factory.IAluguelFactory;
import br.edu.fateczl.academic_library.model.Aluguel;
import br.edu.fateczl.academic_library.model.Aluno;
import br.edu.fateczl.academic_library.model.Exemplar;

public class AluguelFragment extends Fragment implements IFragOperations<Aluguel> {

    private View view;

    private EditText inDataRet, inCodExemplar, inCodAluno, inDataDev;
    private Button btnInsert, btnUpdate, btnDelete, btnList, btnGet;
    private TextView tvListarAluguel;
    private ICollection collection;
    private IController<Aluguel> aluguelCont;
    private IAluguelFactory factory;

    public AluguelFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluguel, container, false);
        aluguelCont = new AluguelController();
        collection = new CollectionController();
        factory = new AluguelController();

        inDataRet = view.findViewById(R.id.inDataRetAluguel);
        inCodExemplar = view.findViewById(R.id.inCodExemplar);
        inCodAluno = view.findViewById(R.id.inRA);
        inDataDev = view.findViewById(R.id.inDataDevAluguel);

        tvListarAluguel = view.findViewById(R.id.tvListarAluguel);
        tvListarAluguel.setMovementMethod(new ScrollingMovementMethod());

        btnInsert = view.findViewById(R.id.btnInsertAluguel);
        btnUpdate = view.findViewById(R.id.btnUpdateAluguel);
        btnDelete = view.findViewById(R.id.btnDeleteAluguel);
        btnList = view.findViewById(R.id.btnListAluguel);
        btnGet = view.findViewById(R.id.btnBuscarAluguel);

        btnList.setOnClickListener(op -> operacaoListar());
        return view;
    }

    @Override
    public void operacaoInserir() {
        try {
            checkFields();
            Aluguel aluguel = mountObject();
            aluguelCont.inserir(aluguel);
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
            Aluguel aluguel = mountObject();
            aluguelCont.atualizar(aluguel);
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
            Aluguel aluguel = mountObject();
            aluguelCont.remover(aluguel);
            Toast.makeText(view.getContext(), getString(R.string.deleteM), Toast.LENGTH_LONG).show();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    @Override
    public void operacaoBuscar() {
        try {
            if (inDataRet.getText().toString().isEmpty())
                throw new Exception("Data Retirada Vazia!");
            Aluguel aluguel = mountObject();
            aluguel = aluguelCont.buscar(aluguel);
            if (isFound()) {
                fillFields(aluguel);
            } else {
                cleanFields();
                throw new Exception(String.format(getString(R.string.findErr), "Aluguel"));
            }
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void operacaoListar() {
        try {
            List<Aluguel> lista = aluguelCont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Aluguel al : lista) {
                buffer.append(al.toString()).append("\n");
            }
            tvListarAluguel.setText(buffer.toString());
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void fillFields(Aluguel aluguel) {

        inDataRet.setText(aluguel.getDataRetirada());
        inDataDev.setText(aluguel.getDataDevolucao());
    }

    @Override
    public void cleanFields() {
        inDataRet.setText("");
        inCodAluno.setText("");
        inCodExemplar.setText("");
        inDataDev.setText("");
    }

    @Override
    public void checkFields() throws Exception {
        if (inDataRet.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inCodAluno.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inCodExemplar.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
        if (inDataDev.getText().toString().isEmpty()) throw new Exception("Campos Vazios!");
    }

    @Override
    public Aluguel mountObject() {
        Aluno aluno = null;
        Exemplar ex = null;
        String dataRet = null;
        String dataDev = null;

        try {
            int codAluno = Integer.parseInt(inCodAluno.getText().toString());
            int codExemplar = Integer.parseInt(inCodExemplar.getText().toString());
            aluno = collection.findAluno(codAluno);
            if (aluno == null) throw new Exception(String.format(getString(R.string.findErr), "Aluno"));
            ex = collection.findExemplar(codExemplar);
            if (ex == null) throw new Exception(String.format(getString(R.string.findErr), "Exemplar"));
            dataRet = inDataRet.getText().toString();
            dataDev = inDataDev.getText().toString();
        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        return factory.AluguelFactory(aluno, ex, dataRet, dataDev);
    }

    @Override
    public boolean isFound() {
        return !inCodAluno.getText().toString().isEmpty() &&
                !inCodExemplar.getText().toString().isEmpty() &&
                !inDataDev.getText().toString().isEmpty();
    }
}