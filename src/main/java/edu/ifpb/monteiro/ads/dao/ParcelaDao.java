package edu.ifpb.monteiro.ads.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import edu.ifpb.monteiro.ads.banco.ConexaoDB;
import edu.ifpb.monteiro.ads.model.Divida;
import edu.ifpb.monteiro.ads.model.Parcela;

/**
 * 
 * @author Andre Luis
 * 
 *         e-mail: tr.andreluis@gmail.com
 *
 */

public class ParcelaDao {

	private Connection conexao = ConexaoDB.getConnection();

	public Integer salvar(Parcela parcela) {

		String sql = "INSERT INTO parcelas(id_divida, valor_parcela, data_parcela, paga, atrasada)"
				+ " VALUES (?,?,?,?,?)";

		try {

			// Instanciando o PreparedStatement solicitando o retorno das chaves
			// (ID) geradas.
			PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, parcela.getDividaParcela().getId());
			statement.setDouble(2, parcela.getValorParcela());
			statement.setDate(3, Date.valueOf(parcela.getDataParcela()));
			statement.setBoolean(4, parcela.getPaga());
			statement.setBoolean(5, parcela.getAtrasada());

			statement.execute();

			// Obtendo ResultSet com as chaves geradas.
			ResultSet keys = statement.getGeneratedKeys();

			// Se alguma chave tiver sido criada com o execute() anterior
			if (keys.next()) {
				return keys.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public Parcela buscarPorID(Integer idParcela) {

		DividaDao daoDivida = new DividaDao();

		String sql = "SELECT * FROM parcelas WHERE id = (?)";

		Parcela parcela = null;

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);

			statement.setInt(1, idParcela);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				parcela = new Parcela();
				parcela.setId(result.getInt("id"));

				Integer idDivida = result.getInt("id_divida");

				Divida divida = daoDivida.buscarPorID(idDivida);

				parcela.setDividaParcela(divida);
				parcela.setValorParcela(result.getDouble("valor_parcela"));
				parcela.setDataParcela(result.getDate("data_parcela").toLocalDate());
				parcela.setPaga(result.getBoolean("paga"));
				parcela.setAtrasada(result.getBoolean("atrasada"));

			}

			statement.close();
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parcela;

	}

	public ArrayList<Parcela> buscarTodos() {

		DividaDao daoDivida = new DividaDao();

		ArrayList<Parcela> dividas = new ArrayList<>();

		String sql = "SELECT * FROM parcelas";

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			Parcela parcela;

			while (result.next()) {

				parcela = new Parcela();
				parcela.setId(result.getInt("id"));

				Integer idDivida = result.getInt("id_divida");

				Divida divida = daoDivida.buscarPorID(idDivida);

				parcela.setDividaParcela(divida);
				parcela.setValorParcela(result.getDouble("valor_parcela"));
				parcela.setDataParcela(result.getDate("data_parcela").toLocalDate());
				parcela.setPaga(result.getBoolean("paga"));

				if (LocalDate.now().isAfter(parcela.getDataParcela())) {
					parcela.setAtrasada(true);
				}

				else {
					parcela.setAtrasada(false);
				}

				dividas.add(parcela);

			}

			statement.close();
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dividas;

	}

	public ArrayList<Parcela> buscarPorDivida(Integer idDivida) {

		DividaDao daoDivida = new DividaDao();

		ArrayList<Parcela> dividas = new ArrayList<>();

		String sql = "SELECT * FROM parcelas WHERE id_divida = (?)";

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setInt(1, idDivida);

			ResultSet result = statement.executeQuery();

			Parcela parcela;

			while (result.next()) {

				parcela = new Parcela();
				parcela.setId(result.getInt("id"));

				idDivida = result.getInt("id_divida");

				Divida divida = daoDivida.buscarPorID(idDivida);

				parcela.setDividaParcela(divida);
				parcela.setValorParcela(result.getDouble("valor_parcela"));
				parcela.setDataParcela(result.getDate("data_parcela").toLocalDate());
				parcela.setPaga(result.getBoolean("paga"));

				// Verifica se a parcela esta em atraso de acordo com a data
				// atual e a da parcela
				if (LocalDate.now().isAfter(parcela.getDataParcela())) {
					parcela.setAtrasada(true);
				}

				else {
					parcela.setAtrasada(false);
				}

				dividas.add(parcela);

			}

			statement.close();
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dividas;

	}

	public void atualizar(Parcela parcela) {

		String sql = "UPDATE parcelas SET id_divida = ?, valor_parcela = ?, data_parcela = ?,"
				+ " paga = ?, atrasada = ? WHERE id = (?)";

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setDouble(1, parcela.getDividaParcela().getId());
			statement.setDouble(2, parcela.getValorParcela());
			statement.setDate(3, Date.valueOf(parcela.getDataParcela()));
			statement.setBoolean(4, parcela.getPaga());
			statement.setBoolean(5, parcela.getAtrasada());
			statement.setInt(6, parcela.getId());

			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void excluirPorDivida(Integer idDivida) {

		String sql = "DELETE FROM parcelas WHERE id_divida = (?)";

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setInt(1, idDivida);

			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void excluir(Integer idParcela) {

		String sql = "DELETE FROM parcelas WHERE id = (?)";

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setInt(1, idParcela);

			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
