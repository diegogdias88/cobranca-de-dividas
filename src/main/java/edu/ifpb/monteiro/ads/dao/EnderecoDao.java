package edu.ifpb.monteiro.ads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.ifpb.monteiro.ads.banco.ConexaoDB;
import edu.ifpb.monteiro.ads.model.Endereco;

/**
 * 
 * @author Andre Luis
 * 
 *         e-mail: tr.andreluis@gmail.com
 *
 */

public class EnderecoDao {

	private Connection conexao = ConexaoDB.getConnection();

	public Integer salvar(Endereco endereco) {

		String sql = "INSERT INTO enderecos(rua, numero, bairro, cidade, estado, ponto_referencia) VALUES (?,?,?,?,?,?)";

		try {

			// Instanciando o PreparedStatement solicitando o retorno das chaves
			// (ID) geradas.
			PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, endereco.getRua());
			statement.setString(2, endereco.getNumero());
			statement.setString(3, endereco.getBairro());
			statement.setString(4, endereco.getCidade());
			statement.setString(5, endereco.getEstado());
			statement.setString(6, endereco.getPontoReferencia());

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

	public Endereco buscarPorID(Integer idEndereco) {

		String sql = "SELECT * FROM enderecos WHERE id = (?)";

		Endereco endereco = null;

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);

			statement.setInt(1, idEndereco);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				endereco = new Endereco();
				endereco.setId(result.getInt("id"));
				endereco.setRua(result.getString("rua"));
				endereco.setNumero(result.getString("numero"));
				endereco.setBairro(result.getString("bairro"));
				endereco.setCidade(result.getString("cidade"));
				endereco.setEstado(result.getString("estado"));
				endereco.setPontoReferencia(result.getString("ponto_referencia"));

			}

			statement.close();
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return endereco;

	}

	public ArrayList<Endereco> buscarTodos() {

		ArrayList<Endereco> devedores = new ArrayList<Endereco>();

		String sql = "SELECT * FROM enderecos";

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			Endereco endereco;

			while (result.next()) {

				endereco = new Endereco();
				endereco.setId(result.getInt("id"));
				endereco.setRua(result.getString("rua"));
				endereco.setNumero(result.getString("numero"));
				endereco.setBairro(result.getString("bairro"));
				endereco.setCidade(result.getString("cidade"));
				endereco.setEstado(result.getString("estado"));
				endereco.setPontoReferencia(result.getString("ponto_referencia"));

				devedores.add(endereco);

			}

			statement.close();
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return devedores;

	}

	public void atualizar(Endereco endereco) {

		String sql = "UPDATE enderecos SET rua = ?, numero = ?, bairro = ?, cidade = ?,"
				+ " estado = ?, ponto_referencia = ?  WHERE id = (?)";

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setString(1, endereco.getRua());
			statement.setString(2, endereco.getNumero());
			statement.setString(3, endereco.getBairro());
			statement.setString(4, endereco.getCidade());
			statement.setString(5, endereco.getEstado());
			statement.setString(6, endereco.getPontoReferencia());
			statement.setInt(7, endereco.getId());

			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void excluir(Integer idEndereco) {

		String sql = "DELETE FROM enderecos WHERE id = (?)";

		try {

			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setInt(1, idEndereco);

			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
