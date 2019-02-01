package educacion.trax.quicktrade2;



public class Favorito {
    private String Nombre;

    public Favorito() {
    }

    public Favorito(String nom) {
        this.Nombre = nom;
    }

    public String getNom() {
        return Nombre;
    }

    public void setNom(String key) {
        this.Nombre = key;
    }
}
