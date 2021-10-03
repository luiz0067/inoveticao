/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;
import POJO.contatoPOJO;
import java.util.Date;


public class contatoBEAN {
    private Integer ID;
    private String Nome;
    private String Email;
    private Boolean Ativo;
    private Date Data_Cadastro;
    

    /**
     * @return the Nome
     */
    
    public contatoBEAN(){
        this.ID=null;
        this.Nome=null;
        this.Ativo=null;
        this.Email=null;
        this.Data_Cadastro=null;                
    }                       
      public contatoBEAN(contatoPOJO comentario){
        this.ID=comentario.getID();
        this.Ativo=comentario.getAtivo();
        this.Nome=comentario.getNome();
        this.Email=comentario.getEmail();
        this.Data_Cadastro=comentario.getData_Cadastro();   
    }
    public String getIDStr(){
        if(this.ID==null)
            return null;
        else
            return this.ID.toString();
    }
    public int getID(){     
        return this.ID.intValue();
    } 
    public void setID(String ID) throws Exception{
        try{
            this.ID=new Integer(Integer.parseInt(ID));
        }
        catch (Exception erro){
            //throw new Exception(erro.getMessage);
            throw new Exception("Código da Vídeo Inválido.");
        }
    }       
    public String getNome() {
        return Nome;
    }
    
    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome)throws Exception {
        if(
                (Nome!=null)
                &&
                (Nome.length()>=3)
                &&
                (Nome.length()<=50)
                
          )
        this.Nome = Nome;
            else
                throw new Exception("Nome da Vídeo Inválido.");
    }
    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email)throws Exception {
        if(
                (Email!=null)
                &&
                (Email.length()>=3)
                &&
                (Email.length()<=50)
          )      
        this.Email = Email;
            else
                throw new Exception("Descrição da Vídeo Inválido.");
    }
    /**
     * @return the Data_Cadastro
     */
    public Date getData_Cadastro() {
        return Data_Cadastro;
    }

    /**
     * @param Data_Cadastro the Data_Cadastro to set
     */
    public void setData_Cadastro(java.util.Date Data_Cadastro) {
            this.Data_Cadastro = Data_Cadastro;
    }
    public String getData_CadastroStr(String formato) {
        return Until.functions.DateToString(Data_Cadastro, formato);
    }
    public String getData_CadastroStr() {
        return Until.functions.DateToString(Data_Cadastro,"dd/MM/yyyy");
    }
    

    /**
     * @param Data_Cadastro the Data_Cadastro to set
     */
    public void setData_Cadastro(String Data_Cadastro) throws Exception{ 
        try{
            this.Data_Cadastro = Until.functions.StringToDate(Data_Cadastro);
        }
        catch(Exception erro){
           throw new Exception(erro.getMessage());
        }
    }    
    /**
     * @return the Ativo
     */
    public boolean getAtivo(){
        return this.Ativo.booleanValue();
    }
    public void setAtivo(boolean Ativo){       
        this.Ativo = Ativo;
    }
    public String getAtivoStr(){
        if((Ativo!=null)&&(Ativo.booleanValue()))
            return "true";
        else if(Ativo!=null)
            return "false";
        else
            return null;
    }
    public void setAtivo(String Ativo) throws Exception{
        this.Ativo = ((Ativo!=null)&&(Ativo.equals("true")));
    }
    public void Clear(){
        this.ID=0;
        this.Nome="";
        this.Ativo=false;
        this.Email="";
        this.Data_Cadastro=null;  
    }

    /**
     * @return the Nome_Categoria
     */
   
}
