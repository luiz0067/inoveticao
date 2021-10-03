package BEAN;

import POJO.banner2POJO;


public class banner2BEAN {
        private Integer id;
        private String src;

        
 public banner2BEAN(){
  this.id=null;
  this.src=null;
 }
 public banner2BEAN(banner2POJO POJO){
        this.id=POJO.getid();
        this.src=POJO.getsrc();
 }
    public int getid() {
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
            throw new Exception("Código de agenda inválido.");
        }
    }
    
     public String getsrc() {
        return src;      
    }
    public void setsrc(String src){
        this.src = src;
    }  
    
    public void Clear(){
        this.id=0;
        this.src="";
        
    }
        }
    
    
    
    
    
