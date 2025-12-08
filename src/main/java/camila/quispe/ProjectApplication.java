package camila.quispe.view;

import javax.swing.SwingUtilities;

public class ProjectApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmComputadoras().setVisible(true);
        });
    }
}