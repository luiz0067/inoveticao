
package BEAN;

import BD.Conexao;
import POJO.agendaPOJO;
import java.util.Date;

public class agendaBEAN {
    private Integer ID;
    private String Src; 
    private String Titulo;
    private String Evento;
    private String descricao;
    private Date DataEvento;

    public agendaBEAN(){
        this.ID=null;
        this.Titulo=null; 
        this.Src=null;
        this.Evento=null;
        this.DataEvento=null;
        this.descricao=null;
    }  
    public agendaBEAN(agendaPOJO POJO){
        this.ID=POJO.getID();
        this.Titulo=POJO.getTitulo();
        this.Evento=POJO.getEvento();
        this.DataEvento=POJO.getDataEvento();
        this.Src=POJO.getSrc();
        this.descricao=POJO.getdescricao();
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
    public String getTitulo() {
        return Titulo;
    }
    public String getSrc() {
        return Src;      
    }
    public void setSrc(String Src){
        this.Src = Src;
    }  
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
                throw new Exception("O Titulo da agenda deve ter entre 3 e 50 letras.");
    }    
    public String getEvento() {
        return Evento;
    }  
    public void setEvento(String Evento)throws Exception {
        if(
                (Evento!=null)
                &&
                (Evento.length()>=3)
                &&
                (Evento.length()<=50)
          )
            this.Evento = Evento;
        else
            throw new Exception("O Evento da agenda deve ter entre 3 e 50 letras.");
    }
    public Date getDataEvento() {
        return DataEvento;
    }
    public String getDataEventoStr()throws Exception  {
        String resultado=null;
        try{
            resultado=Until.functions.DateToString(DataEvento,"dd/MM/yyyy");
        }
        catch(Exception erro){}
        return  resultado;
    }   
    public String getDataEventoStr(String Formato)  {
        String resultado=null;
        try{
            resultado=Until.functions.DateToString(DataEvento,Formato);
        }
        catch(Exception erro){}
        return  resultado;
    }
    public void setDataEvento(Date DataEvento)throws Exception {
        if (DataEvento!=null)
            this.DataEvento = DataEvento;
        else
            throw new Exception("A DataEvento não foi preenchida"); 
    }
    public void setDataEvento(String DataEvento)throws Exception {
        try{
            try{
                this.DataEvento = Until.functions.StringToDate(DataEvento,"dd/MM/yyyy");
            }
            catch(Exception erro2){
                this.DataEvento = Until.functions.StringToDate(DataEvento,"yyyy-MM-dd");
            }
        }
        catch(Exception erro){
            throw new Exception("A DataEvento não foi preenchida"); 
        }
    }
     public String getdescricao() {
        return descricao;
    }
     
     public void setdescricao(String descricao){
        this.descricao=descricao;
    }  
    public void Clear(){
        this.ID=0;
        this.Titulo="";   
        this.Evento="";
        this.Src="";
        this.DataEvento=null;
        this.descricao="";
    }
}
