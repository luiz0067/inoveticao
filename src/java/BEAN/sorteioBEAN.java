/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import POJO.sorteioPOJO;
import Until.functions;

/**
 *
 * @author PC
 */
public class sorteioBEAN {
    private Integer ID;
    private String Nome;
    private String Email;
    private String Telefone;

    /**
     * @return the Nome
     */
    public sorteioBEAN(){
        this.ID=null;
        this.Nome=null;
        this.Email=null;
        this.Telefone=null;
        
    }
     public sorteioBEAN(sorteioPOJO POJO){
        this.ID=POJO.getID();
        this.Nome=POJO.getNome();
        this.Email=POJO.getEmail();
        this.Telefone=POJO.getTelefone();
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
            throw new Exception("Código de sorteio inválido.");
        }
    }
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
                throw new Exception("O nome do cadastro deve ter entre 3 e 50 letras.");
    }

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) throws Exception {
        if(
                (Email!=null)
                &&
                (Email.length()>=5)
                &&
                (Email.length()<=50)
                &&
                (Email.indexOf("@") >0)
                &&
                (Email.indexOf(".") >0)
          )
            this.Email = Email;
        else
            throw new Exception("Email inválido.");
    }

   
    public String getTelefone() {
        return Telefone;
    }

    /**
     * @param Telefone the Telefone to set
     */
    public void setTelefone(String Telefone)throws Exception {
        if(
                (Telefone!=null)
                &&
                (Telefone.length()>=3)
                &&
                (Telefone.length()<=50)
          )
        this.Telefone = Telefone;
            else
                throw new Exception("Telefone inválido.");
    }
    public void setSorteio(boolean valor){
        try{
            String acc="";
            if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                acc="\\";
            else
                acc="/";
            String path=functions.path_upload+acc+"anuncio"+acc;
            java.io.File f=new java.io.File(path+"sorteio.txt");
            if((!f.exists())&&(valor)){
                f.createNewFile();                
            }
            else if ((f.exists())&&(!valor)){
                f.delete();
            }
        }
        catch(Exception erro){
        }
    }
    public boolean getSorteio(){
        boolean valor=false;
        try{
            String acc="";
            if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                acc="\\";
            else
                acc="/";
            String path=functions.path_upload+acc+"anuncio"+acc;
            java.io.File f=new java.io.File(path+"sorteio.txt");
            valor=f.exists();
        }
        catch(Exception erro){
        }
        return valor;
    }
    public void setPopup(boolean valor){
        try{
            String acc="";
            if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                acc="\\";
            else
                acc="/";
            String path=functions.path_upload+acc+"anuncio"+acc;
            java.io.File f=new java.io.File(path+"popup.txt");
            if((!f.exists())&&(valor)){
                f.createNewFile();                
            }
            else if ((f.exists())&&(!valor)){
                f.delete();
            }
        }
        catch(Exception erro){
        }
    }
    public boolean getPopup(){
        boolean valor=false;
        try{
            String acc="";
            if ((functions.path_upload!=null)&&(functions.path_upload.indexOf("\\")!=-1))
                acc="\\";
            else
                acc="/";
            String path=functions.path_upload+acc+"anuncio"+acc;
            java.io.File f=new java.io.File(path+"popup.txt");
            valor=f.exists();
        }
        catch(Exception erro){
        }
        return valor;
    }
}