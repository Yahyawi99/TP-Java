package bean;

public class Employee {
    private int Matricule;
    private String Nom;
    private String Prenom;
    private Date dateEmbaucheD;
    private String sexe;
    private static String[] specialite;

    public Employee() {

    }

    public Employee(int Matricule, String Nom, String Prenom, Date dateEmbaucheD, String sexe) {
        this.Matricule = Matricule;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.dateEmbaucheD = dateEmbaucheD;
        this.sexe = sexe;

    }

    // Getters
    public int getMatricule() {
        return Matricule;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public Date getDateEmbaucheD() {
        return dateEmbaucheD;
    }

    public String getSexe() {
        return sexe;
    }

    // Setters
    public void setMatricule(int Matricule) {
        this.Matricule = Matricule;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public void setDateEmbaucheD(Date dateEmbaucheD) {
        this.dateEmbaucheD = dateEmbaucheD;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    //
    public static String[] getSpecialite() {
        return specialite;
    }

    public static void setSpecialite(String[] newSpecialite) {
        specialite = newSpecialite;
    }

    @Override
    public String toString() {
        return "Employe@" + Matricule;
    }
}