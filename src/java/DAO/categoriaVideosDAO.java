
package DAO;

import BD.Conexao;
import BEAN.categoriaVideosBEAN;
import POJO.categoriaVideosPOJO;
import excecoes.Excecao;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class categoriaVideosDAO {
    private Conexao MinhaConexao=null;
    public categoriaVideosDAO(Conexao MinhaConexao){
        this.MinhaConexao=MinhaConexao;
    } 
    
    private void inserir(categoriaVideosBEAN categoriaVideos)throws Exception{
        try{
            this.MinhaConexao.Executa("insert into categoriavideos(titulo) values("+Conexao.sqlProtection(categoriaVideos.getTitulo())+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao cadastrar categoriavideos.");}    
    }
    
    private void alterar(categoriaVideosBEAN categoriaVideos)throws Exception{
        try{
            this.MinhaConexao.Executa("update categoriavideos set titulo=" +Conexao.sqlProtection(categoriaVideos.getTitulo()) +" where(id="+categoriaVideos.getID()+")");
            this.MinhaConexao.Executa("commit");
        }
        catch(Exception erro){throw new Exception("Erro ao alterar Titulo da categoriaVideos.");}          
    }
    public categoriaVideosBEAN excluir(categoriaVideosBEAN categoriaVideos)throws Exception{
        try{
            categoriaVideos=buscarID(categoriaVideos);
            this.MinhaConexao.Executa("delete from categoriavideos where(id="+categoriaVideos.getID()+")");
            this.MinhaConexao.Executa("commit");
            return categoriaVideos;
        }
        catch(Exception erro){throw new Exception("Erro ao excluir categoriavideos.");}
    }
    public categoriaVideosBEAN salvar(categoriaVideosBEAN categoriaVideos)throws Exception{
        if(this.MinhaConexao.isConectado())
            this.MinhaConexao.Abrir();
        if ((categoriaVideos.getIDStr()!=null)&&(buscarID(categoriaVideos).getIDStr()!=null)){
            alterar(categoriaVideos);
            return buscarID(categoriaVideos);
        }
        else{
           inserir(categoriaVideos);
           return buscarUltimo();
        }
    }    
    
    public categoriaVideosBEAN buscarUltimo()throws Excecao{
       categoriaVideosBEAN ultimo = new categoriaVideosBEAN();
       try{ultimo= ((categoriaVideosBEAN)buscar("select id,titulo from categoriavideos order by id desc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    public categoriaVideosBEAN buscarPrimeiro()throws Excecao{
       categoriaVideosBEAN ultimo = new categoriaVideosBEAN();
       try{ultimo= ((categoriaVideosBEAN)buscar("select id,titulo from categoriavideos order by titulo asc").get(0));}catch(Exception erro){}
       return ultimo;
    }
    
    public categoriaVideosBEAN buscarID(categoriaVideosBEAN categoriaVideos)throws Excecao{
       categoriaVideosBEAN registro = new categoriaVideosBEAN();
       try{registro= ((categoriaVideosBEAN)buscar("select id,titulo from categoriavideos where(id="+categoriaVideos.getIDStr()+") ;").get(0));}catch(Exception erro){}
       return registro;
    }
    
    public Vector buscarTodos()throws Excecao{       
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from categoriavideos order by titulo asc ");}catch(Exception erro){throw new Excecao("Erro ao buscar todos! ERRO:");}
       return registros;

     }
    
    public Vector buscarTitulo(String Titulo)throws Excecao{
       Vector registros=new Vector();
       try{registros = buscar("select id,titulo from categoriavideos where(titulo like'%"+Titulo.replaceAll("'","''") +"%');");}catch(Exception erro){throw new Excecao("Erro ao buscar Titulo ERRO:");}
       return registros;
    }
    public Vector buscar(categoriaVideosBEAN categoria)throws Excecao{
        Vector registros=new Vector();
        try{
            String SQL="select id,titulo from categoriavideos ";
            String condicao="";
            if (categoria.getTitulo()!=null){
                if (condicao.length()>0)
                    condicao+="and";
                condicao+="(titulo like'%"+categoria.getTitulo().replaceAll("'","''") +"%')";
            }       
            SQL=(condicao.length()>0)?SQL+" where"+condicao:SQL;        
            registros = buscar(SQL+" order by titulo asc ");
        }
        catch(Exception erro){
            throw new Excecao("Erro ao buscar ERRO:");
        }
        return registros;
    } 
   public Vector buscar(String SQL)throws Excecao{
        Vector categoriaVideoss=new Vector();
        try{
            if(SQL==null)
                SQL = "SELECT id,titulo from categoriavideos order by titulo asc ";                   
            if(MinhaConexao.Busca(SQL)){
                  while(MinhaConexao.MoverProximo())
                  {
                       categoriaVideosPOJO POJO = new  categoriaVideosPOJO();
                       POJO.setID(MinhaConexao.MostrarCampoInteger("id"));                             
                       POJO.setTitulo(MinhaConexao.MostrarCampoStr("titulo"));                              
                       categoriaVideosBEAN categoriaVideos_BEAN= new categoriaVideosBEAN(POJO);
                       categoriaVideoss.add(categoriaVideos_BEAN);
                  }                                                  
            }
            return categoriaVideoss;
        }    
        catch (Exception erro)
        {
            throw new Excecao("Erro ao buscar categoriaVideos! ERRO: ");
        }
    }
    
}
