package edu.ifpb.monteiro.ads.controller;

import edu.ifpb.monteiro.ads.dao.DevedorDao;
import edu.ifpb.monteiro.ads.dao.DividaDao;
import edu.ifpb.monteiro.ads.dao.EnderecoDao;
import edu.ifpb.monteiro.ads.model.Devedor;
import edu.ifpb.monteiro.ads.model.Divida;
import edu.ifpb.monteiro.ads.model.Endereco;
import edu.ifpb.monteiro.ads.view.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author Andre Luis
 * 
 *         e-mail: tr.andreluis@gmail.com
 *
 */

public class DevedorCadastroController {

	@FXML
	private AnchorPane root;

	@FXML
	private TextField fieldNomeDevedor;

	@FXML
	private TextField fieldCpfDevedor;

	@FXML
	private TextField fieldRuaEndereco;

	@FXML
	private TextField fieldNumeroEndereco;

	@FXML
	private TextField fieldBairroEndereco;

	@FXML
	private TextField fieldEstadoEndereco;

	@FXML
	private TextField fieldCidadeEndereco;

	@FXML
	private TextField fieldPontoReferenciaEndereco;

	@FXML
	private TextField fieldDescricaoDivida;

	@FXML
	private TextField fieldValorDivida;

	@FXML
	private DatePicker dateDivida;

	@FXML
	private DatePicker dateNascimentoDevedor;

	@FXML
	private Button botaoCadastrar;

	@FXML
	private Button botaoCancelar;

	@FXML
	public void cadastrarDevedor() {

		DividaDao daoDivida = new DividaDao();

		Divida divida = new Divida(Double.parseDouble(fieldValorDivida.getText()), dateDivida.getValue(),
				fieldDescricaoDivida.getText());

		Integer idDivida = daoDivida.salvar(divida);

		divida.setId(idDivida);

		EnderecoDao daoEndereco = new EnderecoDao();

		Endereco endereco = new Endereco(fieldRuaEndereco.getText(), fieldNumeroEndereco.getText(),
				fieldBairroEndereco.getText(), fieldCidadeEndereco.getText(), fieldEstadoEndereco.getText(),
				fieldPontoReferenciaEndereco.getText());

		Integer idEndereco = daoEndereco.salvar(endereco);

		endereco.setId(idEndereco);

		DevedorDao daoDevedor = new DevedorDao();

		Devedor devedor = new Devedor(divida, fieldNomeDevedor.getText(), fieldCpfDevedor.getText(),
				dateNascimentoDevedor.getValue(), endereco);

		daoDevedor.salvar(devedor);

		fecharStage();

		atualizar();

	}

	public void atualizar() {
		MainApp.getController().atualizarTabelaDevedorCompleta();
	}

	@FXML
	public void cancelarCadastro() {
		fecharStage();
	}

	public void preencherDesabilitarCampos(Devedor devedor) {
		
		//Campo Devedor
		fieldNomeDevedor.setText(devedor.getNome());
		fieldNomeDevedor.setEditable(false);
		fieldCpfDevedor.setText(devedor.getCpf());
		fieldCpfDevedor.setEditable(false);
		dateNascimentoDevedor.setValue(devedor.getDataNascimento());
		dateNascimentoDevedor.setEditable(false);
		
		//Campo Divida
		fieldDescricaoDivida.setText(devedor.getDivida().getDescricao());
		fieldDescricaoDivida.setEditable(false);
		fieldValorDivida.setText(devedor.getDivida().getValor().toString());
		fieldValorDivida.setEditable(false);
		dateDivida.setValue(devedor.getDivida().getDataDivida());
		dateDivida.setEditable(false);
		
		//Campo Endereco
		fieldRuaEndereco.setText(devedor.getEndereco().getRua());
		fieldRuaEndereco.setEditable(false);
		fieldNumeroEndereco.setText(devedor.getEndereco().getNumero());
		fieldNumeroEndereco.setEditable(false);
		fieldBairroEndereco.setText(devedor.getEndereco().getBairro());
		fieldBairroEndereco.setEditable(false);
		fieldCidadeEndereco.setText(devedor.getEndereco().getCidade());
		fieldCidadeEndereco.setEditable(false);
		fieldEstadoEndereco.setText(devedor.getEndereco().getEstado());
		fieldEstadoEndereco.setEditable(false);
		fieldPontoReferenciaEndereco.setText(devedor.getEndereco().getPontoReferencia());
		fieldPontoReferenciaEndereco.setEditable(false);
		
	}
	
	@FXML
	public void fecharStage() {
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

}
