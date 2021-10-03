/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import POJO.cidadePOJO;

/**
 *
 * @author PC
 */
public class cidadeBEAN {
    private Integer ID;
    private Integer Estado;
    private String Nome;

    /**
     * @return the Estado
     */
    public cidadeBEAN(){
        
    }
    public cidadeBEAN(cidadePOJO cidade){
        this.ID=cidade.getID();
        this.Estado=cidade.getEstado();
        this.Nome=cidade.getNome();
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
            throw new Exception("Código da cidade inválida.");
        }
    }
    public String getEstado() {
          if(this.Estado==null)
            return null;
        else
            return Estado.toString();
    }

    /**
     * @param Estado the Estado to set
     */
    public void setEstado(String Estado)throws Exception{
        try{
            this.Estado=Integer.parseInt(Estado);
        }
        catch (Exception erro){
            throw new Exception("Código da cidade inválida.");
        }
    }
        

    /**
     * @return the Nome
     */
    public String getNome() {
        return Nome;
    }

    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome)throws Exception {
        if(
                (Nome!=null)
                &&
                (Nome.length()>=3)
                &&
                (Nome.length()<=50)
          )
        this.Nome = Nome;
            else
                throw new Exception("Nome inválido.");
    }
}
