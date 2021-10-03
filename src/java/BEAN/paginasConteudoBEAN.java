package BEAN;
import POJO.paginasConteudoPOJO;
public class paginasConteudoBEAN {
    private Integer id_conteudo;
    private Integer id_paginas;
    public paginasConteudoBEAN(){
        
    }
    public paginasConteudoBEAN(paginasConteudoPOJO paginasConteudo){
        this.id_conteudo=paginasConteudo.getId_conteudo();
        this.id_paginas=paginasConteudo.getId_paginas();
    }
    public int getId_conteudo(){
       
            return this.id_conteudo.intValue();
    }
    public String getId_conteudoStr(){
        if(this.id_conteudo==null)
            return null;
        else
            return this.id_conteudo.toString();
    }
    public void setId_conteudo(String id_conteudo) throws Exception{
        try{
            this.id_conteudo=Integer.parseInt(id_conteudo);
        }
        catch (Exception erro){
            throw new Exception("Código da paginasConteudo inválid_conteudoa.");
        }
    }
    public String getId_paginasStr() {
          if(this.id_paginas==null)
            return null;
        else
            return id_paginas.toString();
    }
    public int getId_paginas() {
        return id_paginas.intValue();
    }    
    public void  setId_paginas(String id_paginas)throws Exception{
        try{
            this.id_paginas=Integer.parseInt(id_paginas);
        }
        catch (Exception erro){
            throw new Exception("Código da paginasConteudo inválido.");
        }
    }
}
