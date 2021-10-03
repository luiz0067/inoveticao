package BEAN;

import POJO.garotapgPOJO;

/**
 *
 * @author PC
 */
public class garotapgBEAN {
    private Integer id;
    private String titulo;
    private String src;

    public garotapgBEAN(){
        this.id=null;
        this.titulo=null;   
    }
    public garotapgBEAN(garotapgPOJO POJO){
        this.id=POJO.getid();
        this.titulo=POJO.gettitulo();
        this.src=POJO.getsrc();
    }
    /**
     * @return the id
     */
    public int getid(){
            return this.id.intValue();
    } 
    public String getidStr(){
        if(this.id==null)
            return null;
        else
            return this.id.toString();
    }    
    public void setid(String id) throws Exception{
        try{
            this.id=Integer.parseInt(id);
        }
        catch (Exception erro){
            throw new Exception("Código de página inválido.");
        }
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
            throw new Exception("O titulo da página deve ter entre 3 e 50 letras.");
    }
    public void Clear(){
        this.id=0;
        this.titulo="";
        this.src="";
    }

    /**
     * @return the src
     */
    public String getsrc() {
        return src;
    }

    /**
     * @param src the src to set
     */
    public void setsrc(String src) {
        this.src = src;
    }
}
