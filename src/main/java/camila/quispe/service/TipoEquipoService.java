package camila.quispe.service;

import camila.quispe.database.ConnectionDB;
import camila.quispe.model.TipoEquipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoEquipoService {

    public List<TipoEquipo> listarTipos() {
        List<TipoEquipo> tipos = new ArrayList<>();
        String sql = "SELECT id_tipo, nombre_tipo FROM TipoEquipo ORDER BY nombre_tipo";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoEquipo tipo = new TipoEquipo();
                tipo.setIdTipo(rs.getInt("id_tipo"));
                tipo.setNombre(rs.getString("nombre_tipo"));
                tipos.add(tipo);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar tipos de equipo: " + e.getMessage());
        }
        return tipos;
    }
}