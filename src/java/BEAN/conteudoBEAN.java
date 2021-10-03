package BEAN;

import POJO.conteudoPOJO;
import POJO.videosPOJO;
import java.util.Date;
public class conteudoBEAN {
    private Integer ID; 
    private Integer Id_paginas; 
    private String Titulo;
    private String Conteudo;
    private Date Inicio;
    private Date Fim;
    public conteudoBEAN(){
        this.ID=null;
        this.Id_paginas=null;
        this.Titulo=null;
        this.Conteudo=null; 
        this.Inicio=null;
        this.Fim=null;                       
    }
    public conteudoBEAN(conteudoPOJO conteudo){
        this.ID=conteudo.getID();
        this.Titulo=conteudo.getTitulo();
        this.Inicio=conteudo.getInicio();
        this.Fim=conteudo.getFim();
        this.Conteudo=conteudo.getConteudo();                
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
            throw new Exception("Código da conteúdo inválido.");
        }
    }  
    public void setID(int ID) throws Exception{
        this.ID=new Integer(ID);
    }
    public String getId_paginasStr(){
        if(this.Id_paginas==null)
            return null;
        else
            return this.Id_paginas.toString();
    }
    public int getId_paginas(){     
        return this.Id_paginas.intValue();
    } 
    public void setId_paginas(String Id_paginas) throws Exception{
        try{
            this.Id_paginas=new Integer(Integer.parseInt(Id_paginas));
        }
        catch (Exception erro){
            throw new Exception("Código da Página inválido.");
        }
    }  
    public void setId_paginas(int Id_paginas) throws Exception{
        this.Id_paginas=new Integer(Id_paginas);
    }    
    public String getTitulo() {
        return Titulo;
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
                throw new Exception("Titulo da Conteúdo inválido.");
    }
    public Date getFim() {
        return Fim;
    }
    public String getFimStr(String formato) {
        return Until.functions.DateToString(Fim, formato);
    }
    public void setFim(String Fim,String formato)throws Exception {
        try{
            this.Fim = Until.functions.StringToDate(Fim, formato);
        }
        catch(Exception erro){
            throw new Exception("Data inicio inválida");
        }
    }
    public void setFim(Date Fim)throws Exception {
        this.Fim = Fim;
    }
    public String getConteudo() {
        return Conteudo;
    }
    public void setConteudo(String Conteudo)throws Exception {
            this.Conteudo = Conteudo;
    }
    public Date getInicio() {
        return Inicio;
    }
    public String getInicioStr(String formato) {
        return Until.functions.DateToString(Inicio, formato);
    }
    public void setInicio(String Inicio,String formato)throws Exception {
        try{
            this.Inicio = Until.functions.StringToDate(Inicio, formato);
        }
        catch(Exception erro){
            throw new Exception("Data inicio inválida");
        }
    }
    public void setInicio(Date Inicio)throws Exception {
        this.Inicio = Inicio;
    }
    public void Clear(){
        this.ID=0;
        this.Titulo="";
        this.Inicio=null;
        this.Fim=null;
        this.Conteudo="";  
    }
}
