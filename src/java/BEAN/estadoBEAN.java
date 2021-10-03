/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import POJO.estadoPOJO;

/**
 *
 * @author PC
 */
public class estadoBEAN {
    private Integer ID;
    private String Sigla;
    private String Nome;

    /**
     * @return the Sigla
     */
    public estadoBEAN(estadoPOJO POJO){
        this.ID=POJO.getID();
        this.Nome=POJO.getNome();
        this.Sigla=POJO.getSigla();
    }
    public estadoBEAN(){
        this.ID=null;
        this.Sigla=null;
        this.Nome=null;
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
            this.ID=Integer.parseInt(ID);
        }
        catch (Exception erro){
            throw new Exception("Código do estado inválido");
        }
    }
    public String getSigla() {
        return Sigla;
    }

    /**
     * @param Sigla the Sigla to set
     */
    public void setSigla(String Sigla)throws Exception {
        if(
                (Sigla!=null)
                &&
                (Sigla.length()==2)
          )
        this.Sigla = Sigla;
            else
                throw new Exception("Sigla inválida.");
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
