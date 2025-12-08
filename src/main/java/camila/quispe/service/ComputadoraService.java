package camila.quispe.service;

import camila.quispe.database.ConnectionDB;
import camila.quispe.model.Computadora;
import camila.quispe.model.TipoEquipo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComputadoraService {

    // --- 1. READ: Listar todos los registros (CORREGIDO Y ORDENADO) ---
    public List<Computadora> listarComputadoras() {
        List<Computadora> lista = new ArrayList<>();
        // Ordenamos por código (c.codigo) de forma ascendente (ASC)
        String sql = "SELECT c.*, t.nombre_tipo FROM Computadora c INNER JOIN TipoEquipo t ON c.id_tipo = t.id_tipo ORDER BY c.codigo ASC";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Computadora comp = new Computadora();
                comp.setCodigo(rs.getInt("codigo"));
                comp.setMarca(rs.getString("marca"));
                comp.setModelo(rs.getString("modelo"));
                comp.setSistemaOperativo(rs.getString("sistema_operativo"));
                comp.setRamGB(rs.getInt("ram_gb"));
                comp.setAlmacenamientoGB(rs.getInt("almacenamiento_gb"));
                comp.setEstadoActivo(rs.getBoolean("estado_activo"));

                Date sqlFechaMant = rs.getDate("fecha_mantenimiento");
                if (sqlFechaMant != null) {
                    comp.setFechaMantenimiento(sqlFechaMant.toLocalDate());
                }
                comp.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());

                TipoEquipo tipo = new TipoEquipo(rs.getInt("id_tipo"), rs.getString("nombre_tipo"));
                comp.setTipoEquipo(tipo);

                lista.add(comp);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar computadoras: " + e.getMessage());
        }
        return lista;
    }

    // --- 2. CREATE: Agregar una nueva computadora ---
    public boolean agregarComputadora(Computadora c) {
        String sql = "INSERT INTO Computadora (id_tipo, marca, modelo, sistema_operativo, ram_gb, almacenamiento_gb, fecha_mantenimiento, fecha_registro, estado_activo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getTipoEquipo().getIdTipo());
            ps.setString(2, c.getMarca());
            ps.setString(3, c.getModelo());
            ps.setString(4, c.getSistemaOperativo());
            ps.setInt(5, c.getRamGB());
            ps.setInt(6, c.getAlmacenamientoGB());

            if (c.getFechaMantenimiento() != null) {
                ps.setDate(7, Date.valueOf(c.getFechaMantenimiento()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.setDate(8, Date.valueOf(c.getFechaRegistro()));
            ps.setBoolean(9, c.isEstadoActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar computadora: " + e.getMessage());
            return false;
        }
    }

    // --- 3. DELETE: Eliminación Física (SIMPLE) ---
    public boolean eliminar(int codigo) {
        String sql = "DELETE FROM Computadora WHERE codigo = ?";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al realizar eliminación física: " + e.getMessage());
            return false;
        }
    }

    // --- 4. UPDATE: Modificación de registros ---
    public boolean actualizarComputadora(Computadora c) {
        String sql = "UPDATE Computadora SET id_tipo = ?, marca = ?, modelo = ?, sistema_operativo = ?, ram_gb = ?, " +
                "almacenamiento_gb = ?, fecha_mantenimiento = ?, estado_activo = ? WHERE codigo = ?";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getTipoEquipo().getIdTipo());
            ps.setString(2, c.getMarca());
            ps.setString(3, c.getModelo());
            ps.setString(4, c.getSistemaOperativo());
            ps.setInt(5, c.getRamGB());
            ps.setInt(6, c.getAlmacenamientoGB());

            if (c.getFechaMantenimiento() != null) {
                ps.setDate(7, Date.valueOf(c.getFechaMantenimiento()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.setBoolean(8, c.isEstadoActivo());
            ps.setInt(9, c.getCodigo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar computadora: " + e.getMessage());
            return false;
        }
    }
}