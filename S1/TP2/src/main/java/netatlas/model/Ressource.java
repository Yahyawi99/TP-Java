package netatlas.model;

public class Ressource {
  protected Long id;
  protected String name;
  protected Enums.RessourceType type;

  public Ressource() {
  }

  public Ressource(String name, Enums.RessourceType type) {
    this.name = name;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Enums.RessourceType getType() {
    return type;
  }

  public void setType(Enums.RessourceType type) {
    this.type = type;
  }
}
