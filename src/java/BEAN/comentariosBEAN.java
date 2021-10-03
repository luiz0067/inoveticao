/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;
import POJO.comentariosPOJO;
import java.util.Date;


public class comentariosBEAN {
    private Integer ID;
    private String Titulo;
    private String Descricao;
    private Boolean Exibir;
    private Date Data_Cadastro;
    private Integer id_menu;
    

    /**
     * @return the Titulo
     */
    
    public comentariosBEAN(){
        this.ID=null;
        this.Titulo=null;
        this.Exibir=null;
        this.Descricao=null;
        this.Data_Cadastro=null; 
        this.id_menu=null;
    }                       
      public comentariosBEAN(comentariosPOJO comentario){
        this.ID=comentario.getID();
        this.Exibir=comentario.getExibir();
        this.Titulo=comentario.getTitulo();
        this.Descricao=comentario.getDescricao();
        this.Data_Cadastro=comentario.getData_Cadastro();   
        this.id_menu=comentario.getid_menu();
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
    public String getTitulo() {
        return Titulo;
    }
    
    /**
     * @param Titulo the Titulo to set
     */
    public void setTitulo(String Titulo)throws Exception {
        if(
                (Titulo!=null)
                &&
                (Titulo.length()>=3)
                &&
                (Titulo.length()<=50)
                
          )
        this.Titulo = Titulo;
            else
                throw new Exception("Titulo da Vídeo Inválido.");
    }
    /**
     * @return the Descricao
     */
    public String getDescricao() {
        return Descricao;
    }

    /**
     * @param Descricao the Descricao to set
     */
    public void setDescricao(String Descricao)throws Exception {
        this.Descricao = Descricao;
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
    public void setData_Cadastro(Date Data_Cadastro) {
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
     * @return the Exibir
     */
    public boolean getExibir(){
        return this.Exibir.booleanValue();
    }
    public void setExibir(boolean Exibir){       
        this.Exibir = Exibir;
    }
    public String getExibirStr(){
        if((Exibir!=null)&&(Exibir.booleanValue()))
            return "true";
        else if(Exibir!=null)
            return "false";
        else
            return null;
    }
    public void setExibir(String exibir) throws Exception{
        this.Exibir = ((exibir!=null)&&(exibir.equals("true")));
    }
    
     public String getid_menuStr(){
        if(this.id_menu==null)
            return null;
        else
            return this.id_menu.toString();
    }
    public int getid_menu(){     
        return this.id_menu.intValue();
    } 
    public void setid_menu(String id_menu) throws Exception{
        try{
            this.id_menu=new Integer(Integer.parseInt(id_menu));
        }
        catch (Exception erro){
            //throw new Exception(erro.getMessage);
            throw new Exception("Código da Vídeo Inválido.");
        }
    } 
    public void Clear(){
        this.ID=0;
        this.Titulo="";
        this.Exibir=false;
        this.Descricao="";
        this.Data_Cadastro=null;  
        this.id_menu=0;
    }

    /**
     * @return the titulo_Categoria
     */
   
}
