
package DAO;
import BD.Conexao;
import BEAN.banner2BEAN;
import POJO.banner2POJO;
import Until.functions;
import excecoes.Excecao;
import java.util.Vector;

public class banner2DAO{
    private Conexao MinhaConexao=null;
    public banner2DAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    }
    private banner2BEAN inserir(banner2BEAN banner2)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into banner2(src)"
                    + "values("
                    + ""+Conexao.sqlProtection(banner2.getsrc())+ ""
                    + ""                    
                                       
                    + ")");
            this.MinhaConexao.Executa("commit");
            return buscarUltimo();
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar banner2.");}    
    }
    private banner2BEAN alterar(banner2BEAN banner2,String path_upload)throws Exception{
        try{
            banner2BEAN acc_banner2=buscarid(banner2);
            String SQL="update banner2 set ";
                 
                   if(acc_banner2.getidStr()!=null){
                        if (banner2.getsrc()!=null){
                            functions.deletaImagensRedimencionadas(path_upload,acc_banner2.getsrc());
                            
                            SQL+= "src="+Conexao.sqlProtection(banner2.getsrc()) +"";    
                        }
                   }
                   
                   SQL+= " where(id="+banner2.getidStr()+")";
                             
            this.MinhaConexao.Executa(SQL);
            this.MinhaConexao.Executa("commit");
            banner2=buscarid(banner2);
            return banner2;
        }
        catch(Exception erro){throw new Exception("Erro ao alterar Titulo da banner2.");}          
    }
    public banner2BEAN excluir(banner2BEAN banner2,String path_upload)throws Exception{
        try{
            banner2=buscarid(banner2);
            if(banner2.getidStr()!=null){
                if (banner2.getsrc()!=null){
                    functions.deletaImagensRedimencionadas(path_upload,banner2.getsrc());
                }
            }
            this.MinhaConexao.Executa("delete from banner2 where(id="+banner2.getidStr()+")");
            this.MinhaConexao.Executa("commit");
            return banner2;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir banner2.");}
    }
    public banner2BEAN salvar(banner2BEAN banner2,String path_upload)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((banner2.getidStr()!=null)&&(buscarid(banner2).getidStr()!=null)){
            return alterar(banner2,path_upload);
        }
        else{
           return inserir(banner2);
        }
    }
    public banner2BEAN buscarUltimo()throws Excecao{
       banner2BEAN ultimo = new banner2BEAN();
       try{ultimo= ((banner2BEAN)buscar("select id,src from banner2 order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public banner2BEAN buscarid(banner2BEAN banner2)throws Excecao{
       banner2BEAN registro = new banner2BEAN();
       try{registro= ((banner2BEAN)buscar("select id,src from banner2 where(id="+banner2.getidStr()+");").get(0));}catch(Exception erro){}
       return registro;
    }
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,src from banner2 ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

     }
    public Vector buscarUltimos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,src from banner2 order by id desc");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

    }
   
   public Vector buscar(String SQL)throws Excecao{
        Vector banner2s=new Vector();
        try{
            if(SQL==null)
                SQL = "select id,src from banner2";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       banner2POJO POJO = new  banner2POJO();
                       POJO.setid(MinhaConexao.MostrarCampoInteger("id"));                             
                      
                       POJO.setsrc(MinhaConexao.MostrarCampoStr("src"));
                      
                       banner2BEAN banner2_BEAN= new banner2BEAN(POJO);
                       banner2s.add(banner2_BEAN);
                  }                                                  
            }
            return banner2s;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banner2! ERRO: ");
        }
    }
    public Vector buscarTodosPares()throws Excecao{
        Vector banner2s=new Vector();
        try{
            String SQL = "select id,src from banner2 order by id desc";                   
            if(MinhaConexao.Busca(SQL)){
                  int i=1;
                  while(MinhaConexao.MoverProximo())
                  {
                       if (i%2==0){
                           banner2POJO POJO = new  banner2POJO();
                           POJO.setid(MinhaConexao.MostrarCampoInteger("id"));                             
                          
                           POJO.setsrc(MinhaConexao.MostrarCampoStr("src"));
                           
                           banner2BEAN banner2_BEAN= new banner2BEAN(POJO);
                           banner2s.add(banner2_BEAN);
                       }
                       i++;
                  }                                                  
            }
            return banner2s;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banner2! ERRO: ");
        }
    }
    public Vector buscarTodosImpares()throws Excecao{
        Vector banner2s=new Vector();
        try{
            String SQL = "select id,src from banner2 order by id desc";                   
            if(MinhaConexao.Busca(SQL)){
                  int i=1;
                  while(MinhaConexao.MoverProximo())
                  {
                       if (i%2!=0){
                           banner2POJO POJO = new  banner2POJO();
                           POJO.setid(MinhaConexao.MostrarCampoInteger("id"));                             
                           
                           POJO.setsrc(MinhaConexao.MostrarCampoStr("src"));
                          
                           banner2BEAN banner2_BEAN= new banner2BEAN(POJO);
                           banner2s.add(banner2_BEAN);
                       }
                       i++;
                  }                                                  
            }
            return banner2s;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar banner2! ERRO: ");
        }
    }
    }
    
   
    

