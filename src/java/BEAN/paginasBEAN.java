package BEAN;

import POJO.paginasPOJO;

/**
 *
 * @author PC
 */
public class paginasBEAN {
    private Integer ID;
    private String Titulo;
    private String Src;

    public paginasBEAN(){
        this.ID=null;
        this.Titulo=null;   
    }
    public paginasBEAN(paginasPOJO POJO){
        this.ID=POJO.getID();
        this.Titulo=POJO.getTitulo();
        this.Src=POJO.getSRC();
    }
    /**
     * @return the ID
     */
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
            throw new Exception("Código de página inválido.");
        }
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
            throw new Exception("O Titulo da página deve ter entre 3 e 50 letras.");
    }
    public void Clear(){
        this.ID=0;
        this.Titulo="";
        this.Src="";
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
    public void setSrc(String Src) {
        this.Src = Src;
    }
}
