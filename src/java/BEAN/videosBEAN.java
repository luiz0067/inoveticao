/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import POJO.videosPOJO;

/**
 *
 * @author PC
 */
public class videosBEAN {
    private Integer ID;
    private Integer Id_Categoria;
    private String Titulo;
    private String titulo_Categoria;
    private String Descricao;
    private String Src;
    

    /**
     * @return the Titulo
     */
    
    public videosBEAN(){
        this.ID=null;
        this.Titulo=null;
        this.Id_Categoria=null;
        this.Descricao=null;
        this.Src=null;                
    }
      public videosBEAN(videosPOJO videos){
        this.ID=videos.getID();
        this.Id_Categoria=videos.getId_Categoria();
        this.Titulo=videos.getTitulo();
        this.Descricao=videos.getDescricao();
        this.Src=videos.getSrc();   
        this.titulo_Categoria=videos.getTitulo_Categoria();
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
        if(
                (Descricao!=null)
                &&
                (Descricao.length()>=3)
                &&
                (Descricao.length()<=50)
          )      
        this.Descricao = Descricao;
            else
                throw new Exception("Descrição da Vídeo Inválido.");
    }
    /**
     * @return the Src
     */
    public String getSrc() {
        if (Src!=null)
            Src=Src.trim();
        return Src;
    }

    /**
     * @param Src the Src to set
     */
    public void setSrc(String Src)throws Exception {
            this.Src = Src;
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
            throw new Exception("Id_Categoria da Vídeo Inválido.");
        }
    }
    public void Clear(){
        this.ID=0;
        this.Titulo="";
        this.Id_Categoria=0;
        this.Descricao="";
        this.Src="";  
    }

    /**
     * @return the titulo_Categoria
     */
    public String getTitulo_Categoria() {
        return titulo_Categoria;
    }
}
