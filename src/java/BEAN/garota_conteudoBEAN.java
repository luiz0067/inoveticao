package BEAN;

import POJO.garota_conteudoPOJO;
import POJO.videosPOJO;
import java.util.Date;
public class garota_conteudoBEAN {
    private Integer id; 
    private Integer id_garotapg; 
    private String titulo;
    private String garota_conteudo;
    private Date inicio;
    private Date fim;
    public garota_conteudoBEAN(){
        this.id=null;
        this.id_garotapg=null;
        this.titulo=null;
        this.garota_conteudo=null; 
        this.inicio=null;
        this.fim=null;                       
    }
    public garota_conteudoBEAN(garota_conteudoPOJO garota_conteudo){
        this.id=garota_conteudo.getid();
        this.titulo=garota_conteudo.gettitulo();
        this.inicio=garota_conteudo.getinicio();
        this.fim=garota_conteudo.getfim();
        this.garota_conteudo=garota_conteudo.getgarota_conteudo();                
    }
    public String getidStr(){
        if(this.id==null)
            return null;
        else
            return this.id.toString();
    }
    public int getid(){     
        return this.id.intValue();
    } 
    public void setid(String id) throws Exception{
        try{
            this.id=new Integer(Integer.parseInt(id));
        }
        catch (Exception erro){
            //throw new Exception(erro.getMessage);
            throw new Exception("Código da conteúdo inválido.");
        }
    }  
    public void setid(int id) throws Exception{
        this.id=new Integer(id);
    }
    public String getid_garotapgStr(){
        if(this.id_garotapg==null)
            return null;
        else
            return this.id_garotapg.toString();
    }
    public int getid_garotapg(){     
        return this.id_garotapg.intValue();
    } 
    public void setid_garotapg(String id_garotapg) throws Exception{
        try{
            this.id_garotapg=new Integer(Integer.parseInt(id_garotapg));
        }
        catch (Exception erro){
            throw new Exception("Código da Página inválido.");
        }
    }  
    public void setid_garotapg(int id_garotapg) throws Exception{
        this.id_garotapg=new Integer(id_garotapg);
    }    
    public String gettitulo() {
        return titulo;
    }
    public void settitulo(String titulo)throws Exception {
        if(
                (titulo!=null)
                &&
                (titulo.length()>=3)
                &&
                (titulo.length()<=50)
                
          )
        this.titulo = titulo;
            else
                throw new Exception("titulo da Conteúdo inválido.");
    }
    public Date getfim() {
        return fim;
    }
    public String getfimStr(String formato) {
        return Until.functions.DateToString(fim, formato);
    }
    public void setfim(String fim,String formato)throws Exception {
        try{
            this.fim = Until.functions.StringToDate(fim, formato);
        }
        catch(Exception erro){
            throw new Exception("Data inicio inválida");
        }
    }
    public void setfim(Date fim)throws Exception {
        this.fim = fim;
    }
    public String getgarota_conteudo() {
        return garota_conteudo;
    }
    public void setgarota_conteudo(String garota_conteudo)throws Exception {
            this.garota_conteudo = garota_conteudo;
    }
    public Date getinicio() {
        return inicio;
    }
    public String getinicioStr(String formato) {
        return Until.functions.DateToString(inicio, formato);
    }
    public void setinicio(String inicio,String formato)throws Exception {
        try{
            this.inicio = Until.functions.StringToDate(inicio, formato);
        }
        catch(Exception erro){
            throw new Exception("Data inicio inválida");
        }
    }
    public void setinicio(Date inicio)throws Exception {
        this.inicio = inicio;
    }
    public void Clear(){
        this.id=0;
        this.titulo="";
        this.inicio=null;
        this.fim=null;
        this.garota_conteudo="";  
    }
}
