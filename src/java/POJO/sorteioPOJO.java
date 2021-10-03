package POJO;
public class sorteioPOJO {
    private Integer ID;
    private String Nome;
    private String Email;
    private String Telefone;

 
    public Integer getID(){
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

 
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getEmail() {
        return Email;
    }

 
    public void setEmail(String Email) {
        this.Email = Email;
    }

   
    public String getTelefone() {
        return Telefone;
    }

    /**
     * @param Telefone the Telefone to set
     */
    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }
}
