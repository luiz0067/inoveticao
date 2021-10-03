
package DAO;
import BD.Conexao;
import BEAN.bannerBEAN;
import POJO.bannerPOJO;
import Until.functions;
import excecoes.Excecao;
import java.util.Vector;

public class bannerDAO{
    private Conexao MinhaConexao=null;
    public bannerDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private bannerBEAN inserir(bannerBEAN banner)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into banner(src)"
                    + "values("
                    + ""+Conexao.sqlProtection(banner.getsrc())+ ""
                    + ""                    
                                       
                    + ")");
            this.MinhaConexao.Executa("commit");
            return buscarUltimo();
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar banner.");}    
    }
    private bannerBEAN alterar(bannerBEAN banner,String path_upload)throws Exception{
        try{
            bannerBEAN acc_banner=buscarid(banner);
            String SQL="update banner set ";
                 
                   if(acc_banner.getidStr()!=null){
                        if (banner.getsrc()!=null){
                            functions.deletaImagensRedimencionadas(path_upload,acc_banner.getsrc());
                            
                            SQL+= "src="+Conexao.sqlProtection(banner.getsrc()) +"";    
                        }
                   }
                   
                   SQL+= " where(id="+banner.getidStr()+")";
                             
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            banner=buscarid(banner);
            return banner;
        }
        catch(Exception erro){throw new Exception("Erro ao alterar Titulo da banner.");}          
    }
    public bannerBEAN excluir(bannerBEAN banner,String path_upload)throws Exception{
        try{
            banner=buscarid(banner);
            if(banner.getidStr()!=null){
                if (banner.getsrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,banner.getsrc());
                }
            }
            this.MinhaConexao.Executa("delete from banner where(id="+banner.getidStr()+")");
            this.MinhaConexao.Executa("commit");
            return banner;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir banner.");}
    }
    public bannerBEAN salvar(bannerBEAN banner,String path_upload)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((banner.getidStr()!=null)&&(buscarid(banner).getidStr()!=null)){
            return alterar(banner,path_upload);
        }
        else{
           return inserir(banner);
        }
    }
    public bannerBEAN buscarUltimo()throws Excecao{
       bannerBEAN ultimo = new bannerBEAN();
       try{ultimo= ((bannerBEAN)buscar("select id,src from banner order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public bannerBEAN buscarid(bannerBEAN banner)throws Excecao{
       bannerBEAN registro = new bannerBEAN();
       try{registro= ((bannerBEAN)buscar("select id,src from banner where(id="+banner.getidStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,src from banner ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

     }
    public Vector buscarUltimos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,src from banner order by id desc");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

    }
   
   public Vector buscar(String SQL)throws Excecao{
        Vector banners=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,src from banner";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       bannerPOJO POJO = new  bannerPOJO();
                       POJO.setid(MinhaConexao.MostrarCampoInteger("id"));                             
                      
                       POJO.setsrc(MinhaConexao.MostrarCampoStr("src"));
                      
                       bannerBEAN banner_BEAN= new bannerBEAN(POJO);
                       banners.add(banner_BEAN);
                  }                                                  
            }
            return banners;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banner! ERRO: ");
        }
    }
    public Vector buscarTodosPares()throws Excecao{
        Vector banners=new Vector();
        try{
            String SQL = "select id,src from banner order by id desc";                   
            if(MinhaConexao.Busca(SQL)){
                  int i=1;
                  while(MinhaConexao.MoverProximo())
                  {
                       if (i%2==0){
                           bannerPOJO POJO = new  bannerPOJO();
                           POJO.setid(MinhaConexao.MostrarCampoInteger("id"));                             
                          
                           POJO.setsrc(MinhaConexao.MostrarCampoStr("src"));
                           
                           bannerBEAN banner_BEAN= new bannerBEAN(POJO);
                           banners.add(banner_BEAN);
                       }
                       i++;
                  }                                                  
            }
            return banners;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banner! ERRO: ");
        }
    }
    public Vector buscarTodosImpares()throws Excecao{
        Vector banners=new Vector();
        try{
            String SQL = "select id,src from banner order by id desc";                   
            if(MinhaConexao.Busca(SQL)){
                  int i=1;
                  while(MinhaConexao.MoverProximo())
                  {
                       if (i%2!=0){
                           bannerPOJO POJO = new  bannerPOJO();
                           POJO.setid(MinhaConexao.MostrarCampoInteger("id"));                             
                           
                           POJO.setsrc(MinhaConexao.MostrarCampoStr("src"));
                          
                           bannerBEAN banner_BEAN= new bannerBEAN(POJO);
                           banners.add(banner_BEAN);
                       }
                       i++;
                  }                                                  
            }
            return banners;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banner! ERRO: ");
        }
    }
    }
    
   
    

