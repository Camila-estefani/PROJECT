package camila.quispe.controller;

import camila.quispe.model.Computadora;
import camila.quispe.model.TipoEquipo;
import camila.quispe.service.ComputadoraService;
import camila.quispe.service.TipoEquipoService;

import java.util.List;

public class ComputadoraController {

    private final ComputadoraService compService;
    private final TipoEquipoService tipoService;

    public ComputadoraController() {
        this.compService = new ComputadoraService();
        this.tipoService = new TipoEquipoService();
    }

    public List<Computadora> listarComputadoras() {
        return compService.listarComputadoras();
    }

    public List<TipoEquipo> listarTiposEquipo() {
        return tipoService.listarTipos();
    }

    public boolean guardarComputadora(Computadora c) {
        return compService.agregarComputadora(c);
    }

    public boolean actualizarComputadora(Computadora c) {
        return compService.actualizarComputadora(c);
    }

    public boolean eliminar(int codigo) { // Llama al método de eliminación física
        return compService.eliminar(codigo);
    }
}