package bases_de_datos;

public class validaciones {

    public validaciones() {
    }

    public boolean nombre_correcto(String nom) {
        if (nom == null || nom.isEmpty()) {
            return false;
        }
        nom = nom.toUpperCase();

        for (int i = 0; i < nom.length(); i++) {
            if (nom.charAt(i) < 'A' || nom.charAt(i) > 'Z') {
                return false;
            }
        }
        return true;
    }
    
    public boolean ID_correcto(String n){
        if (n == null || n.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) < '0' || n.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
