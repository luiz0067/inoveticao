/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import POJO.menuPOJO;

/**
 *
 * @author PC
 */
public class menuBEAN {
    private Integer ID;
    private String Titulo;

public menuBEAN(){
    this.ID=null;
    this.Titulo=null;   
}
public menuBEAN(menuPOJO POJO){
    this.ID=POJO.getID();
    this.Titulo=POJO.getTitulo();
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
            throw new Exception("Código de menu inválido.");
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
                throw new Exception("O Titulo do menu deve ter entre 3 e 50 letras.");
    }
    public void Clear(){
        this.ID=0;
        this.Titulo="";
    }
}
