
package BEAN;

import BD.Conexao;
import POJO.contagemPOJO;
import java.util.Date;

public class contagemBEAN {
    private Integer ID;
    private String IP;
    private Date DataCadastro;

    public contagemBEAN(){
        this.ID=null;
        this.IP=null; 
        this.DataCadastro=null;
    }  
    public contagemBEAN(contagemPOJO POJO){
        this.ID=POJO.getID();
        this.IP=POJO.getIP();
        this.DataCadastro=POJO.getDataCadastro();
    }
    public int getID(){
            return this.ID.intValue();
    } 
    public String getIDStr(){
        if(this.ID==null)
            return null; 
        else
            return this.ID.toString();
    }    
    public void setID(String ID) throws Exception{
        try{
            this.ID=Integer.parseInt(ID);
        }
        catch (Exception erro){
            throw new Exception("Código de agenda inválido.");
        }
    }
    public String getIP() {
        return IP;
    }
     
    public void setIP(String IP)throws Exception {
        if(
                (IP!=null)
                &&
                (IP.length()>=3)
                &&
                (IP.length()<=50)
          )
        this.IP = IP;
            else
                throw new Exception("O IP da agenda deve ter entre 3 e 50 letras.");
    }    
   
    public Date getDataCadastro() {
        return DataCadastro;
    }
    public String getDataCadastroStr()throws Exception  {
        String resultado=null;
        try{
            resultado=Until.functions.DateToString(DataCadastro,"dd/MM/yyyy");
        }
        catch(Exception erro){}
        return  resultado;
    }   
    public String getDataCadastroStr(String Formato)  {
        String resultado=null;
        try{
            resultado=Until.functions.DateToString(DataCadastro,Formato);
        }
        catch(Exception erro){}
        return  resultado;
    }
    public void setDataCadastro(Date DataCadastro)throws Exception {
        if (DataCadastro!=null)
            this.DataCadastro = DataCadastro;
        else
            throw new Exception("A DataCadastro não foi preenchida"); 
    }
    public void setDataCadastro(String DataCadastro)throws Exception {
        try{
            try{
                this.DataCadastro = Until.functions.StringToDate(DataCadastro,"dd/MM/yyyy");
            }
            catch(Exception erro2){
                this.DataCadastro = Until.functions.StringToDate(DataCadastro,"yyyy-MM-dd");
            }
        }
        catch(Exception erro){
            throw new Exception("A DataCadastro não foi preenchida"); 
        }
    }
    public void Clear(){
        this.ID=0;
        this.IP="";   
        this.DataCadastro=null;
    }
}
