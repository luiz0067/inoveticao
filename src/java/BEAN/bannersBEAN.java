/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import POJO.bannersPOJO;
import POJO.fotosPOJO;

/**
 *
 * @author PC
 */
public class bannersBEAN {
    private Integer ID;
    private Integer Id_Categoria;
    private String Titulo;
    private String Descricao;
    private String Src;
    

    /**
     * @return the Titulo
     */
    
    public bannersBEAN(){
        this.ID=null;
        this.Titulo=null;
        this.Id_Categoria=null;
        this.Descricao=null;
        this.Src=null;                
    }
    public bannersBEAN(bannersPOJO fotos){
        this.ID=fotos.getID();
        this.Titulo=fotos.getTitulo();
        this.Id_Categoria=fotos.getId_Categoria();
        this.Descricao=fotos.getDescricao();
        this.Src=fotos.getSrc();                
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
            throw new Exception("Código da foto inválida.");
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
                throw new Exception("Titulo da foto inválida.");
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
        if(
                (Descricao!=null)
                &&
                (Descricao.length()>=3)
                &&
                (Descricao.length()<=50)
          )      
        this.Descricao = Descricao;
            else
                throw new Exception("Descrição da foto inválida.");
    }
    /**
     * @return the Src
     */
    public String getSrc() {
        return Src;
    }

    /**
     * @param Src the Src to set
     */
    public void setSrc(String Src)throws Exception {
        if(
                (Src!=null)
                &&
                (Src.length()>=3)
                &&
                (Src.length()<=50)
          )       
            this.Src = Src;
        else
            throw new Exception ("Src da foto inválida");
    }
    /**
     * @return the Id_Categoria
     */
    public int getId_Categoria(){
        return this.Id_Categoria.intValue();
    }
    public void setId_Categoria(int Id_Categoria){       
        this.Id_Categoria = Id_Categoria;
    }
    public String getId_CategoriaStr(){
        if(Id_Categoria==null)
            return null;
        else
            return this.Id_Categoria.toString();
    }
    public void setId_Categoria(String ID) throws Exception{
        try{
            this.Id_Categoria=new Integer(Integer.parseInt(ID));
        }
        catch (Exception erro){
            throw new Exception("Id_Categoria da foto inválida.");
        }
    }
    public void Clear(){
        this.ID=0;
        this.Titulo="";
        this.Id_Categoria=0;
        this.Descricao="";
        this.Src="";   
    }
}
